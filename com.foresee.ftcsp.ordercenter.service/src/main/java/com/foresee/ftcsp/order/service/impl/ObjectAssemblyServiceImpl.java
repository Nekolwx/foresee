package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.dict.FtcspDictHolder;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.order.auto.model.*;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.service.ICustomerInnerService;
import com.foresee.ftcsp.order.service.IObjectAssemblyService;
import com.foresee.ftcsp.order.service.IOrderDataProcessingService;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <pre>
 * 对象组装接口实现类
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2018年01月16日
 */
@Service
public class ObjectAssemblyServiceImpl implements IObjectAssemblyService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private IDUtil idUtil;

    @Autowired
    private IOrderDataProcessingService orderDataProcessingService;

    @Autowired
    private FtcspDictHolder ftcspDictHolder;

    @Autowired
    private ICustomerInnerService customerInnerService;

    /**
     * 生成支付流水 TODO。
     *
     * @return String
     */
    @Override
    public String generatePayNo() {
        String prefixStr = OrderConstant.ORDER_PAY_NO_PREFIX + LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = idUtil.createSequenceId(OrderConstant.ORDER_PAY_NO_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    /**
     * 生成订单编号 TODO。
     *
     * @return String
     */
    @Override
    public String generateOrderCode() {
        String prefixStr = LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = this.idUtil.createSequenceId(OrderConstant.ORDER_CODE_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    /**
     * 基础创单--初始化流水对象与流水订单关联对象
     * @param ordPayFlowing
     * @param ordPayFlowingRef
     * @param ordOrder
     */
    @Override
    public void initializationOrdPayFlowing(OrdPayFlowing ordPayFlowing, OrdPayFlowingRef ordPayFlowingRef, OrdOrder ordOrder){
        //流水设置
        ordPayFlowing.setPayId(idGenerator.getLong());
        ordPayFlowing.setPayState(OrderConstant.ZERO);// 默认待支付
        ordPayFlowing.setPayWay(OrderConstant.ZERO);// 默认支付宝
        //设置payNo和appId
        if (null!=ordOrder.getBusinessOrderNo()&&!"".equals(ordOrder.getBusinessOrderNo())&&ordOrder.getBusinessOrderNo()!=ordOrder.getOrderId().toString()){
            String appId = ftcspDictHolder.getDictItemValue("pay_appId_channel", ordOrder.getChannel()+"");
            ordPayFlowing.setPayNo(ordOrder.getBusinessOrderNo());
            ordPayFlowing.setPayAppId(appId);
        }else{
            String appId = ftcspDictHolder.getDictItemValue("pay_appId_channel", OrderConstant.ZERO+"");
            ordPayFlowing.setPayNo(generatePayNo());
            ordPayFlowing.setPayAppId(appId);
        }
        ordPayFlowing.setPayAmount(ordOrder.getPayAmount());
        //流水订单关联表
        ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
        ordPayFlowingRef.setPayId(ordPayFlowing.getPayId());
        ordPayFlowingRef.setOrderId(ordOrder.getOrderId());
    }

    /**
     * 基础创单--生成配送信息对象
     * @param orderDTO
     * @return
     */
    @Override
    public OrdDelivery createDelivery(OrderDTO orderDTO, OrdOrder ordOrder){
        OrdDelivery deliver = new OrdDelivery();
        deliver.setDeliveryId(this.idGenerator.getLong());
        deliver.setOrderId(ordOrder.getOrderId());
        deliver.setDeliveryMode(Integer.valueOf(orderDTO.getDeliveryMode()));
        deliver.setReceiver(orderDTO.getReceiver());
        deliver.setReceiverPhone(orderDTO.getReceiverPhone());
        deliver.setReceiverAddress(orderDTO.getReceiverAddress());
        deliver.setInviteAddress(orderDTO.getInviteAddress());
        deliver.setCreateUser(orderDTO.getUserId());
        deliver.setUpdateUser(orderDTO.getUserId());
        return deliver;
    }

    /**
     * 基础创单--生成开票信息
     * @param orderDTO
     * @return
     */
    @Override
    public OrdBillingInfo createAnInvoice(OrderDTO orderDTO){
        OrdBillingInfo ordBillingInfo = new OrdBillingInfo();
        ordBillingInfo.setBillingInfoId(idGenerator.getLong());
        if("0".equals(orderDTO.getNeedInvoice())){
            ordBillingInfo.setBuyerType(Integer.valueOf(orderDTO.getBuyerType()));
            ordBillingInfo.setBuyerName(orderDTO.getBuyerName());
            ordBillingInfo.setBuyerTaxpayerNo(orderDTO.getBuyerTaxpayerNo());
            ordBillingInfo.setBuyerAddressPhone(orderDTO.getBuyerAddressPhone());
        }
        return ordBillingInfo;
    }

    /**
     * 基础创单--根据商品补充订单信息
     * @param orderDTO
     * @param ordOrder
     * @param goodsDTO
     */
    @Override
    public void supplementOrderByGoods(OrderDTO orderDTO,OrdOrder ordOrder,GoodsDTO goodsDTO){
        //0-全额，1-分期 dividePay(现在默认是全额)
        ordOrder.setDividePay(OrderConstant.DIVIDE_PAY_NO);
        //sku_id（关联商品信息） skuId
        ordOrder.setSkuId(goodsDTO.getGoodsSkuId());
        //商品id goodsId
        ordOrder.setGoodsId(goodsDTO.getGoodsId());
        //产品id productId
        ordOrder.setProductId(goodsDTO.getProductId());
        //区域编码 areaCode
        ordOrder.setAreaCode(changeOrderAreaBySkuId(orderDTO,goodsDTO));
        //商品数量
        Integer qty = goodsDTO.getGoodsQuantity();
        //商品总数量 goodsQuantity
        ordOrder.setGoodsQuantity(qty);
        //数量单位(1、次；2、台；3、件；4、套) goodsQuantityUnit
        ordOrder.setGoodsQuantityUnit(goodsDTO.getGoodsQuantityUnit());
        if (null!=goodsDTO.getGoodsDiscountPrice()&&goodsDTO.getGoodsDiscountPrice().compareTo(BigDecimal.ZERO)!=-1){
            //商品原价 goodsOriginalPrice
            ordOrder.setGoodsOriginalPrice(goodsDTO.getGoodsOriginalPrice().multiply(new BigDecimal(qty)));
            //商品金额 goodsAmount
            ordOrder.setGoodsAmount(goodsDTO.getGoodsDiscountPrice().multiply(new BigDecimal(qty)));
        }else if (null!=goodsDTO.getCustomPrice()){
            //商品原价 goodsOriginalPrice
            ordOrder.setGoodsOriginalPrice(goodsDTO.getCustomPrice());
            //商品金额 goodsAmount
            ordOrder.setGoodsAmount(goodsDTO.getCustomPrice());
        }else {
            throw new FtcspRuntimeException("09030034", (Object) "查询到商品价格为空或商品的自定义面议价格为空");
        }
        //运费 freight
        ordOrder.setFreight(goodsDTO.getFreight());
        //优惠金额 discountAmount
        ordOrder.setDiscountAmount(goodsDTO.getDiscountAmount());
        //支付金额 payAmount 商品金额+运费-优惠金额
        ordOrder.setPayAmount((ordOrder.getGoodsAmount().add(ordOrder.getFreight()))
                .subtract(ordOrder.getDiscountAmount()));
        //开通方式 1、即时开通;2、人工开通;3、付款开通 openingMode
        ordOrder.setOpeningMode(goodsDTO.getOpeningMode());
        //服务方式 0 按时间服务 1按次数服务 serviceMode
        ordOrder.setServiceMode(goodsDTO.getServiceMode());
        //服务期限值 serviceTermValue
        ordOrder.setServiceTermValue(goodsDTO.getServiceTermValue());
        //服务期限单位0 天 1 月 2 年 serviceTermUnit
        ordOrder.setServiceTermUnit(goodsDTO.getServiceTermUnit());
        //付款状态与服务状态由此方法处理。payStatus，serviceStatus
        orderDataProcessingService.orderServiceChangeByGoods(ordOrder);
        //及时开通,才可计算服务日期:服务开始日期 serviceStartDate,服务结束日期 serviceEndDate
        if (goodsDTO.getOpeningMode() == OrderConstant.ONE) {
            orderDataProcessingService.ServiceTimeByUnit(ordOrder);
        }
    }

    /**
     * 基础创单--父订单标识与子订单标识组装
     * @param orderDTO
     * @param parentOrdOrder
     * @param ordOrder
     */
    @Override
    public void assemblyParentAndSubToOrder(OrderDTO orderDTO,OrdOrder parentOrdOrder,OrdOrder ordOrder){
        //是否是父订单（0是 1否）isParent
        if (null==parentOrdOrder){
            ordOrder.setIsParent(OrderConstant.ZERO);
            if (null != orderDTO.getGoodsGroup()&&orderDTO.getGoodsGroup().size()>OrderConstant.ONE){
                //是否有子订单（0否，1是） isHaveSubOrder
                ordOrder.setIsHaveSubOrder(true);
            }else{
                //是否有子订单（0否，1是） isHaveSubOrder
                ordOrder.setIsHaveSubOrder(false);
            }
        }else{
            ordOrder.setIsParent(OrderConstant.ONE);
            //父订单id parentId
            ordOrder.setParentId(parentOrdOrder.getParentId());
            //是否有子订单（0否，1是） isHaveSubOrder
            ordOrder.setIsHaveSubOrder(false);
        }
    }

    /**
     * 组装订单基础信息
     * @param orderDTO
     * @param ordOrder
     */
    @Override
    public void creadMainOrder(OrderDTO orderDTO,OrdOrder ordOrder){
        //订单号ID orderId
        ordOrder.setOrderId(idGenerator.getLong());
        //订单编号 orderCode
        ordOrder.setOrderCode(generateOrderCode());
        //订单类型（0-新增 1-续期）orderType
        ordOrder.setOrderType(OrderConstant.ORDER_TYPE_ADD);
        //订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）orderStatus
        ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_PENDING_PAY);
        //下单时间 orderTime
        ordOrder.setOrderTime(new Date());
        //下单人（关联用户信息）orderUser
        ordOrder.setOrderUser(orderDTO.getUserId());
        //客户id（关联客户信息）customerId
        ordOrder.setCustomerId(orderDTO.getCusId());
        //开票状态0 未开票 1 已开票 2 不需要开票  3 开票中 4 开票失败 5 红冲中 6 已红冲 7 红冲失败 billingStatus
        // 设置开票状态默认未开票
        if ("1".equals(orderDTO.getNeedInvoice())) {
            ordOrder.setBillingStatus(OrderConstant.BILLING_STATUS_NO_NEED);
        } else {
            ordOrder.setBillingStatus(OrderConstant.BILLING_STATUS_NO);
        }
        //售卖渠道（0 平台 1 网站 2金财代账 3 微信商城） channel
        ordOrder.setChannel(Integer.valueOf(orderDTO.getChannel()));
        //业务订单号 businessOrderNo
        if (null==orderDTO.getBusinessOrderNo()||"".equals(orderDTO.getBusinessOrderNo())){
            ordOrder.setBusinessOrderNo(ordOrder.getOrderId()+"");
        }else {
            ordOrder.setBusinessOrderNo(orderDTO.getBusinessOrderNo());
        }
        //客户留言 feedback
        ordOrder.setFeedback(orderDTO.getFeedback());
        //是否需要配送0 不需要 1 需要-快递 2 需要-上门自提 isDelivery
        // 设置配送方式默认不需要配送
        if ("0".equals(orderDTO.getDeliveryMode()) || "1".equals(orderDTO.getDeliveryMode())) {
            ordOrder.setIsDelivery(OrderConstant.NEED_DELIVERY_YES);
        } else {
            ordOrder.setIsDelivery(OrderConstant.NEED_DELIVERY_NO);
        }
        //退款状态 （0未启动 1待退款2部分退款 3已退款）refundStatus
        ordOrder.setRefundStatus(OrderConstant.ZERO);
        //物流状态（0不需要物流 1未启动 2待发货 3已发货 4已完成 5拒收 6 已取消）logisticsStatus
        ordOrder.setLogisticsStatus(OrderConstant.ZERO);
        //设置创建人
        if (null!=orderDTO.getUserId()&&!"".equals(orderDTO.getUserId())){
            ordOrder.setCreateUser(orderDTO.getUserId());
            ordOrder.setUpdateUser(orderDTO.getUserId());
        }
    }

    /**
     * 根据orderDTO,goodsDTO获取到商品是否配置了订单需要获取下单区域。
     * 没有配置的，将返回null的areaCode。
     * 需要配置的，如果参数里面areaCode有将直接返回，参数没有，将调用客户模块去获取客户的areaCode
     * @param orderDTO
     * @param goodsDTO
     * @return
     */
    public String changeOrderAreaBySkuId(OrderDTO orderDTO,GoodsDTO goodsDTO){
        //根据goodsDTO获取下单区域是否配置：0免配置，1需配置
        //免配的，将返回null的areaCode
        if (OrderConstant.ZERO==goodsDTO.getIsOrderAreaDeploy()){
            return null;
        }
        //需配置的，将根据areaCode是否为空，空需执行客户区域查询
        if (null!=goodsDTO.getAreaCode() && !goodsDTO.getAreaCode().isEmpty()){
            return goodsDTO.getAreaCode();
        }
        //根据客户id获取客户区域
        String cusAreaCode = customerInnerService.queryCusAreaCodeByCusId(orderDTO.getCusId());
        return cusAreaCode;
    }
}
