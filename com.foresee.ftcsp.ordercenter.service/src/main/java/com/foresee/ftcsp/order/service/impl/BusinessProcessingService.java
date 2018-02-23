package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.manual.restdto.QueryGoodInfoByIdDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.GoodsCodeListDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryGoodsByGoodsIdToOrderDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryValidateByTryGoodsId;
import com.foresee.ftcsp.order.auto.dao.*;
import com.foresee.ftcsp.order.auto.model.*;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dao.OrdOrderContractExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdOrderContractDTO;
import com.foresee.ftcsp.order.manual.restdto.BusinessHandle;
import com.foresee.ftcsp.order.manual.restdto.BusinessHandleDataDTO;
import com.foresee.ftcsp.order.manual.restdto.PreRechargeDTO;
import com.foresee.ftcsp.order.manual.restdto.TryBusinessHandle;
import com.foresee.ftcsp.order.service.IBusinessProcessingService;
import com.foresee.ftcsp.order.service.IMessageProducerService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * 业务办理实现类。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2017年12月14日
 */
@Service
@Transactional
public class BusinessProcessingService implements IBusinessProcessingService {
    private Logger LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private IDUtil idUtil;

    @Resource
    private OrdBillingInfoMapper ordBillingInfoMapper;

    @Resource
    private OrdOrderContractExtMapper ordOrderContractExtMapper;

    @Resource
    private OrdOrderMapper ordOrderMapper;

    @Resource
    private OrdPayFlowingMapper ordPayFlowingMapper;

    @Resource
    private OrdPayFlowingRefMapper ordPayFlowingRefMapper;

    @Resource
    private OrdOrderContractMapper ordOrderContractMapper;

    @Resource
    private OrdOrderExtMapper ordOrderExtMapper;

    @Resource
    private IMessageProducerService messageProducerService;

    @Resource
    private GoodsApi goodsApi;


    /**
     * 查询是否互斥商品规则。
     * @return
     */
    public Object queryGoodsIsMutuallyExclusive(TryBusinessHandle tryBusinessHandle){
        boolean result = queryGoodsIdIsMutuallyExclusiveByOrder(tryBusinessHandle.getGoodsId(),tryBusinessHandle.getCusId());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("result",result);
        if (!result){
            String contractId = IDUtil.getUUID();
            resultMap.put("contractId",contractId);
        }
        return resultMap;
    }

    /**
     * 试用商品业务办理添加订单接口
     * @param tryBusinessHandle
     * @return
     */
    public  Object addTryOutBusinessHandle(TryBusinessHandle tryBusinessHandle,String userId){
        //判断此客户下单的商品是否已存在互斥
        if (queryGoodsIdIsMutuallyExclusiveByOrder(tryBusinessHandle.getGoodsId(),tryBusinessHandle.getCusId())){
            throw new FtcspRuntimeException("09030034", (Object) "选择的商品已办理过或者办理过同类商品");
        }
        //创建订单
        OrdOrder ordOrder = addBusinessHandleOrderInfo(null,userId,tryBusinessHandle);
        //组装财悦接口对象类
        BusinessHandleDataDTO businessHandleDataDTO = assemblyBusinessHandleDataDTO(tryBusinessHandle.getCusId(),ordOrder);
        //调用财悦接口
        messageProducerService.sendBusinessHandleData(businessHandleDataDTO,ordOrder.getOrderId()+"");
        //返回创建成功
        return ordOrder;
    }

    /**
     * 详情业务办理添加订单接口
     * @param businessHandle
     * @param userId
     * @return
     */
    public Object addDetailedBusinessHandle(BusinessHandle businessHandle, String userId) {
        //判断此客户下单的商品是否已存在互斥
        if(queryGoodsIdIsMutuallyExclusiveByOrder(businessHandle.getGoodsId(),businessHandle.getCusId())){
            throw new FtcspRuntimeException("09030034", (Object) "选择的商品已办理过或者办理过同类商品");
        }
        //合同编号组装
//        assemblyContractCode(businessHandle);
        //判断合同编号是否存
        if (queryContractCodeIsHave(businessHandle)){
            throw new FtcspRuntimeException("09030034", (Object) "合同编号已存在");
        }
        //创建开票信息
        addBusinessHandleOrdBillingInfo(businessHandle);
        //创建订单
        OrdOrder ordOrder = addBusinessHandleOrderInfo(businessHandle,userId,null);
        //创建合同
        addBusinessHandleOrdOrderContract(businessHandle,ordOrder);
        //组装充值对象
        PreRechargeDTO rechargeDTO = assemblyPreRechargeDTO(businessHandle,userId);
        //调用充值
        messageProducerService.sendRechargeData(rechargeDTO);
        //组装财悦接口对象类
        BusinessHandleDataDTO businessHandleDataDTO = assemblyBusinessHandleDataDTO(businessHandle.getCusId(),ordOrder);
        //调用财悦接口
        messageProducerService.sendBusinessHandleData(businessHandleDataDTO,ordOrder.getOrderId()+"");
        return ordOrder;
    }

    /**
     * 合同编码组装
     * @param businessHandle
     */
    public void assemblyContractCode(BusinessHandle businessHandle){
        String initialContractCode = businessHandle.getOrdOrderContractRest().getContractCode();
        //获取年份的后两位数
        Calendar cal = Calendar.getInstance();
        String year = cal.get(Calendar.YEAR)+"";
        //前缀生成
        String prefix = "JCSJ-XY"+year.substring(2);
        //真正的组合合同编码
        String contractCode = prefix+initialContractCode;
        businessHandle.getOrdOrderContractRest().setContractCode(contractCode);
    }

    /**
     * 业务办理的开票信息增加
     *
     * @param businessHandle
     */
    public void addBusinessHandleOrdBillingInfo(BusinessHandle businessHandle) {
        //判断开票信息是否存在，存在就添加。
        if (null != businessHandle.getOrdBillingInfoRest()) {
            OrdBillingInfo ordBillingInfo = new OrdBillingInfo();
            BeanUtils.copyProperties(businessHandle.getOrdBillingInfoRest(), ordBillingInfo);
            Long ordBillingInfoId = idGenerator.getLong();
            ordBillingInfo.setBuyerType(OrderConstant.ONE);//企业
            ordBillingInfo.setBillingInfoId(ordBillingInfoId);
            businessHandle.getOrdBillingInfoRest().setBillingInfoId(ordBillingInfoId);
            ordBillingInfoMapper.insertSelective(ordBillingInfo);
        }
    }

    /**
     * 业务办理的合同信息增加
     *
     * @param businessHandle
     */
    public void addBusinessHandleOrdOrderContract(BusinessHandle businessHandle,OrdOrder ordOrder) {
        OrdOrderContract ordOrderContract = new OrdOrderContract();
        BeanUtils.copyProperties(businessHandle.getOrdOrderContractRest(),ordOrderContract);
        ordOrderContract.setOwnerId(ordOrder.getOrderId());
        //拥有者类型 1-订单
        ordOrderContract.setOwnerType(OrderConstant.ONE);
        ordOrderContractMapper.insertSelective(ordOrderContract);
    }

    /**
     * 创建订单与插入订单
     * @param businessHandle
     * @param userId
     * @return
     */
    public OrdOrder addBusinessHandleOrderInfo(BusinessHandle businessHandle, String userId,TryBusinessHandle tryBusinessHandle) {
        //组装订单初始数据
        OrdOrder ordOrder = new OrdOrder();
        initializationOrderInfo(ordOrder,businessHandle,userId,tryBusinessHandle);
        //组装流水数据
        OrdPayFlowing ordPayFlowing = new OrdPayFlowing();
        OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
        initializationOrdPayFlowing(ordPayFlowing,ordPayFlowingRef,ordOrder,businessHandle);
        //插入数据
        ordOrderMapper.insertSelective(ordOrder);
        ordPayFlowingMapper.insertSelective(ordPayFlowing);
        ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);
        return ordOrder;
    }

    /**
     * 初始化订单类
     * @param ordOrder
     * @param businessHandle
     * @param userId
     */
    private void initializationOrderInfo(OrdOrder ordOrder, BusinessHandle businessHandle, String userId,TryBusinessHandle tryBusinessHandle) {
        QueryGoodInfoByIdDTO queryGoodInfoByIdDTO = new QueryGoodInfoByIdDTO();
        Long goodsId =null;
        if (null!=businessHandle){
            //设置商品Id
            ordOrder.setGoodsId(businessHandle.getGoodsId());
            //设置产品Id
            ordOrder.setProductId(businessHandle.getProductId());
            //设置客户Id
            ordOrder.setCustomerId(businessHandle.getCusId());
            //付款状态
            ordOrder.setPayStatus(OrderConstant.PAY_STATUS_PREPARE_TO_PAY);
            //服务开始日期
            ordOrder.setServiceStartDate(businessHandle.getOrdOrderContractRest().getContractServiceStartTime());
            //服务结束日期(因需求确定，设置为计费结束时间)
            ordOrder.setServiceEndDate(businessHandle.getOrdOrderContractRest().getContractChargingEndTime());
            businessHandle.getOrdOrderContractRest().setContractServiceEndTime(businessHandle.getOrdOrderContractRest().getContractChargingEndTime());
            //开票信息id
            ordOrder.setBillingInfoId(businessHandle.getOrdBillingInfoRest().getBillingInfoId());
            //合同编码
            ordOrder.setContractCode(businessHandle.getOrdOrderContractRest().getContractCode());
            //计费开始日期
            ordOrder.setContractChargingStartTime(businessHandle.getOrdOrderContractRest().getContractChargingStartTime());
            //计费结束日期
            ordOrder.setContractChargingEndTime(businessHandle.getOrdOrderContractRest().getContractChargingEndTime());
            goodsId = businessHandle.getGoodsId();
        }else if (null != tryBusinessHandle){
            //设置商品Id
            ordOrder.setGoodsId(tryBusinessHandle.getGoodsId());
            //设置产品Id
            ordOrder.setProductId(tryBusinessHandle.getProductId());
            //设置客户Id
            ordOrder.setCustomerId(tryBusinessHandle.getCusId());
            //服务开始日期
            ordOrder.setServiceStartDate(new Date());
            //付款状态
            ordOrder.setPayStatus(OrderConstant.PAY_STATUS_NOT_PAY);
            goodsId = tryBusinessHandle.getGoodsId();
            //服务结束日期(根据试用商品获取服务期限)
            queryGoodInfoByIdDTO.setGoodsId(goodsId);
            RestResponse<QueryValidateByTryGoodsId> restPdtGoods = goodsApi.queryValidateByTryGoodsId(queryGoodInfoByIdDTO);
            if (!restPdtGoods.isSuccess()){
                throw new FtcspRuntimeException("09030034", (Object) restPdtGoods.getHead().getErrorMsg());
            }
            String validate = restPdtGoods.getBody().getValidate();
            String termValue = validate.substring(0, validate.length() - 1);
            Integer timeNum = Integer.parseInt(termValue);
            String termUnit = validate.substring(validate.length() - 1, validate.length());
            Calendar rightNow = Calendar.getInstance();
            ordOrder.setServiceTermValue(timeNum);
            if ("D".equals(termUnit)) {
                rightNow.add(Calendar.DAY_OF_YEAR,timeNum);//日期加多少天
                ordOrder.setServiceTermUnit(OrderConstant.ZERO);
                rightNow.add(Calendar.DAY_OF_YEAR,-1);
            } else if ("M".equals(termUnit)) {
                rightNow.add(Calendar.MONTH,timeNum);//日期加多少个月
                ordOrder.setServiceTermUnit(OrderConstant.ONE);
                rightNow.add(Calendar.DAY_OF_YEAR,-1);
            } else if ("Y".equals(termUnit)) {
                rightNow.add(Calendar.YEAR,timeNum);//日期减多少年
                ordOrder.setServiceTermUnit(OrderConstant.TWO);
                rightNow.add(Calendar.DAY_OF_YEAR,-1);
            }
            ordOrder.setServiceEndDate(rightNow.getTime());
            goodsId = tryBusinessHandle.getGoodsId();
        }else {
            throw new FtcspRuntimeException("09030034", (Object) "需要的信息不存在");
        }
        queryGoodInfoByIdDTO.setGoodsId(goodsId);
        RestResponse<QueryGoodsByGoodsIdToOrderDTO> restPdtGoods = goodsApi.queryGoodsByGoodsIdToOrder(queryGoodInfoByIdDTO);
        QueryGoodsByGoodsIdToOrderDTO pdtGoods = new QueryGoodsByGoodsIdToOrderDTO();
        if (restPdtGoods.isSuccess()){
            pdtGoods = restPdtGoods.getBody();
        }
        //生成订单ID
        Long orderId = idGenerator.getLong();
        ordOrder.setOrderId(orderId);
        //放取订单编号
        ordOrder.setOrderCode(generateOrderCode());
        //设置订单类型（为新增）
        ordOrder.setOrderType(OrderConstant.ORDER_TYPE_ADD);
        //设置订单状态
        ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING);
        //设置下单时间
        ordOrder.setOrderTime(new Date());
        //设置下单人
        ordOrder.setOrderUser(userId);
        //业务订单号
        ordOrder.setBusinessOrderNo(ordOrder.getOrderId()+"");
        //付费方式
        ordOrder.setDividePay(OrderConstant.THREE);
        //设置渠道，金财管家代账（渠道为12）
        ordOrder.setChannel(OrderConstant.FINANCE_AND_TAX_STEWARD);
        //设置商品原价为0
        ordOrder.setGoodsOriginalPrice(BigDecimal.ZERO);
        //设置商品总数量
        ordOrder.setGoodsQuantity(OrderConstant.ONE);
        //设置数量单位(1、次；2、台；3、件；4、套)
        ordOrder.setGoodsQuantityUnit(pdtGoods.getProductUnit());
        //设置商品金额
        ordOrder.setGoodsAmount(BigDecimal.ZERO);
        //设置优惠金额
        ordOrder.setDiscountAmount(BigDecimal.ZERO);
        //设置价格,为0
        ordOrder.setPayAmount(BigDecimal.ZERO);
        //开通模式
        ordOrder.setOpeningMode(pdtGoods.getOpeningWay());
        //服务状态
        ordOrder.setServiceStatus(OrderConstant.SERVICE_STATUS_SERVICING);
        //商品编码
        ordOrder.setGoodsCode(pdtGoods.getGoodsCode());
        //服务时间在创订单时间之后，服务状态，订单状态分别为未启动，待付款，待付款
        if (ordOrder.getServiceStartDate().getTime()>ordOrder.getOrderTime().getTime()){
            //服务状态
            ordOrder.setServiceStatus(OrderConstant.SERVICE_STATUS_PREPARE_TO_START);
            //设置订单状态
            ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_PENDING_PAY);
        }
    }

    /**
     * 生成订单编号 TODO。
     *
     * @return String
     */
    private String generateOrderCode() {
        String prefixStr = LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = idUtil.createSequenceId(OrderConstant.ORDER_CODE_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    /**
     * 初始化流水对象
     * @param ordPayFlowing
     * @param ordPayFlowingRef
     * @param ordOrder
     * @param businessHandle
     */
    public void initializationOrdPayFlowing(OrdPayFlowing ordPayFlowing, OrdPayFlowingRef ordPayFlowingRef, OrdOrder ordOrder,BusinessHandle businessHandle){
        //初始化流水
        ordPayFlowing.setPayId(idGenerator.getLong());
        ordPayFlowing.setPayState(OrderConstant.ZERO);// 默认待支付
        ordPayFlowing.setPayNo(generatePayNo());
        if (null!=businessHandle){
            switch (businessHandle.getRechargeInformation().getRechargeWay()){
                case "1":
                    ordPayFlowing.setPayWay(OrderConstant.TWO);// 银联
                    break;
                case "2":
                    ordPayFlowing.setPayWay(OrderConstant.ZERO);// 支付宝
                    break;
                case "3":
                    ordPayFlowing.setPayWay(OrderConstant.ONE);// 微信
                    break;
            }
            ordPayFlowing.setThreePartyNo(businessHandle.getRechargeInformation().getOutPayNo());//第三方流水号
            businessHandle.getRechargeInformation().setOutTradeNo(ordPayFlowing.getPayNo());
        }else {
            ordPayFlowing.setPayWay(OrderConstant.ZERO);// 默认支付宝
        }
        ordPayFlowing.setPayAmount(BigDecimal.ZERO);//价格为0
        ordPayFlowing.setPayTime(new Date());
        //初始化流水订单关联
        ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
        ordPayFlowingRef.setOrderId(ordOrder.getOrderId());
        ordPayFlowingRef.setPayId(ordPayFlowing.getPayId());
    }

    /**
     * 生成支付流水 TODO。
     *
     * @return String
     */
    private String generatePayNo() {
        String prefixStr = OrderConstant.ORDER_PAY_NO_PREFIX + LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = this.idUtil.createSequenceId(OrderConstant.ORDER_PAY_NO_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    /**
     * 判断商品是否有互斥
     *
     * @param goodsId
     * @return
     */
    public boolean queryGoodsIdIsMutuallyExclusiveByOrder(Long goodsId,String cusId) {
        boolean result = false;
        //获取此商品互斥相关联的goodsCode
        QueryGoodInfoByIdDTO queryGoodInfoByIdDTO = new QueryGoodInfoByIdDTO();
        queryGoodInfoByIdDTO.setGoodsId(goodsId);
        RestResponse<GoodsCodeListDTO> goodsCodeListDTO =  goodsApi.queryGoodsCodeBySameType(queryGoodInfoByIdDTO);
        List<String> goodsCodeList = null;
        if (goodsCodeListDTO.isSuccess()){
            goodsCodeList = goodsCodeListDTO.getBody().getGoodsCodes();
        }
        if (null!=goodsCodeList&&goodsCodeList.size()>0){
            int ordNum = ordOrderExtMapper.queryOrderByGoodsCode(goodsCodeList,cusId);
            if (ordNum>0){
                result = true;
                return result;
            }
        }
        return result;
    }

    /**
     * 获取此合同编码是否存在,存在true，不存在false
     * @param businessHandle
     * @return
     */
    public boolean queryContractCodeIsHave(BusinessHandle businessHandle) {
        boolean result = false;
        //获取此合同编码是否存在
        OrdOrderContractDTO ordOrderContractDTO = ordOrderContractExtMapper.queryContractByContractCode(businessHandle.getOrdOrderContractRest().getContractCode());
        //如果查询存在，将结果返回true;
        if (null!=ordOrderContractDTO && !"".equals(ordOrderContractDTO.getContractId())){
            result = true;
        }
        return result;
    }

    /**
     * 组装充值对象
     * @param businessHandle
     * @param userId
     * @return
     */
    public PreRechargeDTO assemblyPreRechargeDTO(BusinessHandle businessHandle,String userId){
        PreRechargeDTO preRechargeDTO = new PreRechargeDTO();
        BeanUtils.copyProperties(businessHandle.getRechargeInformation(),preRechargeDTO);
        //ownerId:账号拥有者ID
        preRechargeDTO.setOwnerId(businessHandle.getCusId());
        //拥有者类型： 2 企业。
        preRechargeDTO.setOwnerType(OrderConstant.TWO+"");
        //currency:币种:1财币，2人民币。
        preRechargeDTO.setCurrency(OrderConstant.TWO+"");
        //outTradeNo:外部交易号：平台订单的流水
//        preRechargeDTO.setOutPayNo(businessHandle.getRechargeInformation().getOutPayNo());
        //rechargeDate:充值时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = businessHandle.getRechargeInformation().getRechargeDate();
        String rechargeDate = sdf.format(d);
        preRechargeDTO.setRechargeDate(rechargeDate);
        //主题，填写充值原因。
        preRechargeDTO.setSubject(OrderConstant.SUBJECT);
        //rechargeType:1线上充值，2线下充值
        preRechargeDTO.setRechargeType(OrderConstant.TWO+"");
        return preRechargeDTO;
    }

    /**
     * 组装财悦mq对象。
     * @param cusId
     * @param ordOrder
     * @return
     */
    public BusinessHandleDataDTO assemblyBusinessHandleDataDTO(String cusId,OrdOrder ordOrder){
        BusinessHandleDataDTO businessHandleDataDTO = new BusinessHandleDataDTO();
        businessHandleDataDTO.setDljgId(cusId);
        businessHandleDataDTO.setGoodsCode(ordOrder.getGoodsCode());
        if(OrderConstant.ORDER_STATUS_SERVICING==ordOrder.getServiceStatus()){
            businessHandleDataDTO.setServiceState(OrderConstant.ZERO+"");
        }else{
            businessHandleDataDTO.setServiceState(OrderConstant.ONE+"");
        }
        return businessHandleDataDTO;
    }

}
