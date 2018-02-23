package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.api.inner.CommunalInnerApi;
import com.foresee.ftcsp.common.core.config.AppUtils;
import com.foresee.ftcsp.common.core.dict.FtcspDictHolder;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.multipart.client.FtcspMultipartClient;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.*;
import com.foresee.ftcsp.common.manual.request.UpdateTaskRequestDTO;
import com.foresee.ftcsp.customer.api.inner.CustomerInnerApi;
import com.foresee.ftcsp.customer.manual.restdto.QueryUserDepCusParamDTO;
import com.foresee.ftcsp.customer.manual.restdto.QueryUserDepCusRequestDTO;
import com.foresee.ftcsp.customer.manual.restdto.response.QueryUserDepCusDTO;
import com.foresee.ftcsp.customer.manual.restdto.response.QueryUserDepCusListDTO;
import com.foresee.ftcsp.customer.manual.restdto.response.QueryUserDepCusResponseDTO;
import com.foresee.ftcsp.ftcspmvc.validation.ValidationException;
import com.foresee.ftcsp.ftcspmvc.validation.Validators;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.manual.dto.PdtAttributeDTO;
import com.foresee.ftcsp.goods.manual.dto.PdtGoodsSkuExtDTO;
import com.foresee.ftcsp.goods.manual.dto.PdtTypeAttributeDTO;
import com.foresee.ftcsp.goods.manual.restdto.QuerySkuInfoAttrBySkuIdDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.AttributeBySkuIdDTO;
import com.foresee.ftcsp.mq.message.impl.JobHandleMessage;
import com.foresee.ftcsp.mq.producer.IMessageProducer;
import com.foresee.ftcsp.order.auto.dao.*;
import com.foresee.ftcsp.order.auto.model.*;
import com.foresee.ftcsp.order.constant.*;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdPayFlowingExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.manual.exceltemplate.FailureOrderTemplate;
import com.foresee.ftcsp.order.manual.exceltemplate.OrderTemplate;
import com.foresee.ftcsp.order.service.IBatchExcelService;
import com.foresee.ftcsp.order.service.config.FileUpLoadConfig;
import com.foresee.ftcsp.order.util.ExcelUtil;
import com.foresee.ftcsp.ordertask.api.inner.OrderTaskInnerApi;
import com.foresee.ftcsp.ordertask.dto.AddTaskDTO;
import com.foresee.ftcsp.ordertask.dto.RelationDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//import com.foresee.ftcsp.common.manual.request.UpdateTaskRequestDTO;

/**
 * <pre>
 * excel订单解析和生成数据服务类
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/22
 */
@Service
public class BatchExcelServiceImpl implements IBatchExcelService {

    private Logger logger = Loggers.make();

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private FtcspDictHolder dictHolder;

    @Autowired
    private OrdExcelTempMapper ordExcelTempMapper;

    @Autowired
    private OrdExcelDataTempMapper ordExcelDataTempMapper;

    @Autowired
    private OrdOrderExtMapper ordOrderExtMapper;

    @Autowired
    private OrdPayFlowingExtMapper ordPayFlowingExtMapper;

    @Autowired
    private OrdPayFlowingRefMapper ordPayFlowingRefMapper;

    @Autowired
    private OrdOrderUserDepMapper ordOrderUserDepMapper;

    @Autowired
    private OrdBillMapper ordBillMapper;

    @Autowired
    private CustomerInnerApi customerInnerApi;

    @Autowired
    private GoodsApi goodsApi;

    @Autowired
    private OrderTaskInnerApi orderTaskInnerApi;

    @Autowired
    private CommunalInnerApi communalInnerApi;

    @Autowired
    private IMessageProducer producer;

    @Autowired
    private FileUpLoadConfig fileUpLoadConfig;

    @Autowired
    IDUtil idUtil;

    @Override
    public boolean handleOrderFromExcel(String fileKey, String fileName,Long excelId,String taskId) {
        FtcspMultipartClient multipartClient = new FtcspMultipartClient("000106");
        try {
            File excelFile = File.createTempFile("orderExcel", ".xlsx");
            String encodeKey = fileKey.replace(" ","%20");
            multipartClient.getFile(encodeKey, excelFile);
            Integer count = ExcelUtil.getExcelRowNum(excelFile);  //获得总条数，分批解析导入；

            //这里分批解析保存，一次长度为3000
            int starIndex = 1;
            while (true){
                int endIndex = starIndex + 3000;
                if(endIndex<count){
                    //解析start - end
                    parseAndSave(excelFile, excelId, starIndex, endIndex);
                    starIndex = endIndex;
                }else{
                    //解析start - 总长度
                    parseAndSave(excelFile, excelId, starIndex, count);
                    break;
                }
            }

            // 解析excel完成并插入到了临时表；异步通知进行临时表数据的判断并插入到业务表；
            JobHandleMessage jobHandleMessage = new JobHandleMessage();
            Map param = new HashMap();
            param.put("excelId", excelId);
            param.put("taskId", taskId);
            jobHandleMessage.setObject(param);
            producer.send(QueueConstants.ORDER_CREATE_ORDER_EXCHANGE,QueueConstants.HANDLE_ORDER_DATA_TEMP,jobHandleMessage);

        } catch (Exception e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertExcelTemp(OrdExcelTemp ordExcelTemp) {
        int count = ordExcelTempMapper.insert(ordExcelTemp);
        return count>0?true:false;
    }

    @Override
    @Transactional
    public Map handleDataByExcelId(Long excelId,String taskId) throws Exception {
        String userId = ordExcelTempMapper.selectByPrimaryKey(excelId).getOperator();
        //不要一次加载所有,一次加载3000
        Long startIndex = 0l;
        int allCount = 0;  //统计总记录数
        List<OrdExcelDataTemp> orderTempList = new ArrayList<>();
        List<OrdExcelDataTemp> troubleList = new ArrayList<>(); //存储处理后返回的失败数据
        do {
             orderTempList = ordExcelDataTempMapper.selectByExcelId(excelId, startIndex, 3000l);
             if(orderTempList.isEmpty()){
                 logger.info("查询到该Excel文件的内容不存在未处理数据！");
                 break;
             }
             troubleList.addAll(handleOrderTempData(orderTempList, userId));
             allCount += orderTempList.size();
             startIndex += 3000;
             orderTempList = ordExcelDataTempMapper.selectByExcelId(excelId, startIndex, 3000l);
        }while (!orderTempList.isEmpty());
        int failCount = troubleList.size();

        //创建失败数据生成模板
        List<FailureOrderTemplate> troubleTemplates = troubleList.stream().map(excelTemp->{
            FailureOrderTemplate failureOrderTemplate = new FailureOrderTemplate();
            try{
                BeanUtils.copyProperties(failureOrderTemplate,excelTemp);
            }catch (Exception e){
                e.printStackTrace();
            }
            String date = LocalDateUtil.getDateStr("yyyy/MM/dd HH:mm", excelTemp.getPayTime());
            failureOrderTemplate.setPayTime(date);
            return failureOrderTemplate;
        }).collect(Collectors.toList());

        String path = "";
        if(!troubleTemplates.isEmpty()){
            File file = File.createTempFile("failure_order_excel", ".xls");
            ExcelUtil.createExcel(troubleTemplates,FailureOrderTemplate.class,file);
            FtcspMultipartClient ftcspMultipartClient = new FtcspMultipartClient("000106","yu1qoj8kyu1qoj8k","6ECD141F8B991FB2616214018D9BA32F");
            String url = fileUpLoadConfig.getUploadIpPort()+"fileGateway/ordercenter/outter/storageService/upload";
            if(ftcspMultipartClient.post(url,file)){
                RestResponse<List> response = Jackson.fromJson(ftcspMultipartClient.getResponseContent(),RestResponse.class);
                Map map =  (Map) response.getBody().get(0);
                path = fileUpLoadConfig.getUploadIpPort()+"fileGateway/ordercenter/outter/storageService/download/"+(String) map.get("path");
            }
        }

        //回调记录
        UpdateTaskRequestDTO updateTaskRequestDTO = new UpdateTaskRequestDTO();
        updateTaskRequestDTO.setTaskId(taskId);
        updateTaskRequestDTO.setSuccessRecord(String.valueOf(allCount-failCount));
        updateTaskRequestDTO.setFailRecord(String.valueOf(failCount));
        updateTaskRequestDTO.setTotalRecord(String.valueOf(allCount));
        updateTaskRequestDTO.setLogPath(path);
        communalInnerApi.updateTask(updateTaskRequestDTO);
        return null;
    }

    public List<OrdExcelDataTemp> handleOrderTempData(List<OrdExcelDataTemp> orderTempList,String userId){
        List<OrdExcelDataTemp> troubleList = new ArrayList<>();
        List<OrdExcelDataTemp> normalList = new ArrayList<>();
        orderTempList.forEach(orderTemp->{
            try{   //调用框架jsr303校验数据
                Validators.validate(orderTemp);
                orderTemp.setIsNormal(OrderExcelTempConstant.normalData); //若没有抛出异常则是正常数据
                if (Objects.isNull(orderTemp.getBusinessOrderNo())) {
                    orderTemp.setBusinessOrderNo("");
                }
                normalList.add(orderTemp);
            }catch (ValidationException e){
                List<ValidationException.ValidationDetail> details = e.getDetails();
                StringBuffer sb = new StringBuffer();
                details.forEach(detail-> sb.append(detail.getMessage()).append(","));
                orderTemp.setErrorMessage(sb.substring(0,sb.length()-1));
                orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                troubleList.add(orderTemp); //错误数据加入到错误集合
            }
        });
        //判断正常数据中有没有业务参考号重复的情况，若有重复直接返回失败;

        HashMap<String,List<OrdExcelDataTemp>> map = normalList.stream().collect(Collectors.groupingBy(OrdExcelDataTemp::getBusinessOrderNo,HashMap::new,Collectors.toList()));
        normalList.clear();  //清除正常数据集合
        map.forEach((key,list)->{
            int size = list.size();
            if (size > 1) { //根据业务参考号分组，集合长度大于1则存在重复
                if ("".equals(key)) {  //说明用户没有输入业务参考号
                    normalList.addAll(list);
                    return;
                }
                list.forEach(ordExcelDataTemp -> {
                    ordExcelDataTemp.setErrorMessage("表格中业务参考号重复"+key);
                });
                troubleList.addAll(list);
            }else{
                normalList.addAll(list);
            }
        });

        if(normalList.isEmpty()){
            return troubleList; //至此若正常数据为空则返回；
        }

        //执行正常数据业务逻辑校验,调用客户查询接口，更新相关信息
        List<QueryUserDepCusParamDTO> cusParams = normalList.stream().map(OrdExcelDataTemp::getCompany).distinct().
                map(company->{
                    QueryUserDepCusParamDTO paramDTO = new QueryUserDepCusParamDTO();
                    paramDTO.setCusName(company);
                    return paramDTO;
                }).collect(Collectors.toList());
        QueryUserDepCusRequestDTO queryParam = new QueryUserDepCusRequestDTO();
        queryParam.setQueryCustomerParamDTOList(cusParams);
        logger.info("调用客户查询的参数为\n"+Jackson.toJson(cusParams));
        queryParam.setServiceCode("7");  //一达通业务code
        RestResponse<QueryUserDepCusResponseDTO> resultDTOs =  customerInnerApi.queryUserDepCusList(queryParam);
        if (Objects.isNull(resultDTOs)) {  //客户服务调用失败返回对象为空
            normalList.forEach(data->{
                data.setIsNormal(OrderExcelTempConstant.troubleData);
                data.setErrorMessage("客户查询时出现了错误");
            });
            troubleList.addAll(normalList);
            return troubleList;
        }
        List<QueryUserDepCusListDTO>  customers = resultDTOs.getBody().getCusList();
        Map<String,QueryUserDepCusListDTO> cusMap = customers.stream().collect(Collectors.toMap(QueryUserDepCusListDTO::getCusName, Function.identity(),(t1,t2)->t1));

        //获取到客户信息后进行批量的处理
        String payAppId = dictHolder.getDictItem("pay_appId_channel", "0").get("itemValue");
        List<OrdOrderDTO> ordOrders = new ArrayList<>();
        List<OrdPayFlowingDTO> ordPayFlowings = new ArrayList<>();
        List<OrdPayFlowingRef> ordPayFlowingRefs = new ArrayList<>();
        List<OrdOrderUserDep> ordOrderUserDepsAll = new ArrayList<>();
        List<OrdBill> ordBills = new ArrayList<>();
        normalList.forEach(orderTemp->{
            QueryUserDepCusListDTO userDTO = cusMap.get(orderTemp.getCompany());
            if(Objects.isNull(userDTO)){
                orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                orderTemp.setErrorMessage("不存在该客户");
                troubleList.add(orderTemp);
                return;
            }
            if (Objects.isNull(userDTO.getRelationList()) || userDTO.getRelationList().isEmpty()) {
                orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                orderTemp.setErrorMessage("该客户没有分配到服务人员");
                troubleList.add(orderTemp);
                return;
            }else {
                List<QueryUserDepCusDTO> relationList = userDTO.getRelationList();
                boolean ifAllocate = relationList.stream().anyMatch(relation->"1".equals(relation.getDistriType()));
                if (!ifAllocate) {
                    orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                    orderTemp.setErrorMessage("该客户没有分配到服务人员");
                    troubleList.add(orderTemp);
                    return;
                }
            }

            QuerySkuInfoAttrBySkuIdDTO querySkuInfoAttrBySkuIdDTO = new QuerySkuInfoAttrBySkuIdDTO();
            querySkuInfoAttrBySkuIdDTO.setGoodsSkuId(orderTemp.getSkuId());
            querySkuInfoAttrBySkuIdDTO.setPropertySelection(3); //定价与非定价属性
            RestResponse<AttributeBySkuIdDTO> querySkuInfoAttrBySkuId = goodsApi.querySkuInfoAttrBySkuId(querySkuInfoAttrBySkuIdDTO);
            //插入业务订单表,调用商品inner接口查询对应商品是否面议，基础or增值
            if(!"0".equals(querySkuInfoAttrBySkuId.getHead().getErrorCode())||
                    Objects.isNull(querySkuInfoAttrBySkuId.getBody())){
                orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                orderTemp.setErrorMessage("无法查询到对应商品");
                troubleList.add(orderTemp);
                return;
            }

            boolean ifBase = false;
            PdtGoodsSkuExtDTO goodsSkuExtDTO = querySkuInfoAttrBySkuId.getBody().getPdtGoodsSkuExtDTO();
            Long goodsId = goodsSkuExtDTO.getGoodsId();
            BigDecimal price = goodsSkuExtDTO.getPrice();
            if (price == null || price.doubleValue()<0) {
                price = orderTemp.getOrderAmount();  //若是面议价<0 或者空 取导入价
                if (Objects.isNull(price)) { //若导入价还是为空则错误
                    orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                    orderTemp.setErrorMessage("面议价的商品需要填入价格");
                    troubleList.add(orderTemp);
                    return;
                }
            }
            PdtTypeAttributeDTO typeAttributeDTO = querySkuInfoAttrBySkuId.getBody().getPdtTypeAttributeDTOList().get(0);
            Set<String> attrCode =  typeAttributeDTO.getPdtAttributeDTOList().stream().map(PdtAttributeDTO::getAttributeCode).collect(Collectors.toSet());
            if(attrCode.contains(AttributeCodeConstant.ydtBase)){
                ifBase = true;
            }

            OrdOrderDTO ordOrder = new OrdOrderDTO();
            Long orderId = idGenerator.getLong();
            if (!StringUtils.isBlank(orderTemp.getBusinessOrderNo())) { //若有业务参考号先校验
                OrdOrderDTO ordOrderDTO = ordOrderExtMapper.queryOrderByBusinessNoAndChannel(orderTemp.getBusinessOrderNo(),OrderConstant.CHANNEL_ALIYDT.toString());
                if(!Objects.isNull(ordOrderDTO)){
                    orderTemp.setIsNormal(OrderExcelTempConstant.troubleData);
                    orderTemp.setErrorMessage("系统中已存在该业务参考号");
                    troubleList.add(orderTemp);
                    return;
                }
                ordOrder.setBusinessOrderNo(orderTemp.getBusinessOrderNo());
            }else {
                ordOrder.setBusinessOrderNo(orderId.toString());
            }
            String orderCode = generateOrderCode();
            ordOrder.setOrderId(orderId);
            ordOrder.setOrderCode(orderCode);
            ordOrder.setOrderType(OrderConstant.ORDER_TYPE_ADD);
            Date payTime = orderTemp.getPayTime()==null?new Date():orderTemp.getPayTime(); //若excel中没有下单时间取当前
            ordOrder.setOrderTime(payTime);
            ordOrder.setServiceStartDate(payTime);
            ordOrder.setOrderUser(orderTemp.getOrderUser());
            ordOrder.setServiceStatus(2);  //服务状态 - 服务中
            ordOrder.setContactPerson(orderTemp.getOrderUser());
            ordOrder.setContactPhone(orderTemp.getContactPhone());
            ordOrder.setSkuId(orderTemp.getSkuId());
            ordOrder.setOpeningMode(1);  //即时开通
            ordOrder.setCreateUser(userId);
            ordOrder.setGoodsId(goodsId);
            ordOrder.setGoodsAmount(price);
            ordOrder.setPayAmount(price);
            ordOrder.setChannel(OrderConstant.CHANNEL_ALIYDT);
            ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING); //服务中状态
            if(orderTemp.getServiceTermValue()!=null){  //服务期限值-月份
                ordOrder.setServiceTermValue(orderTemp.getServiceTermValue());
                ordOrder.setServiceTermUnit(OrderConstant.SERVICE_TERM_UNIT_MONTH);
            }
            if (ifBase) {  //若为基础服务 代付款
                ordOrder.setPayStatus(OrderConstant.PAY_STATUS_REPLACE);
            }else {
                ordOrder.setPayStatus(OrderConstant.PAY_STATUS_PREPARE_TO_PAY);
            }
            Date serviceEndDate = null;
            if(!ifBase){
                Integer serviceValue = orderTemp.getServiceTermValue();
                if(serviceValue!=null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(payTime);
                    calendar.add(Calendar.MONTH,serviceValue);
                    serviceEndDate = calendar.getTime();
                    if(serviceEndDate.before(new Date())){
                        ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_COMPLETED); //若结束时间早于现在则为已完成状态
                    }
                    ordOrder.setServiceTermValue(serviceValue);
                    ordOrder.setServiceTermUnit(OrderConstant.SERVICE_TERM_UNIT_MONTH);
                }
            }
            ordOrder.setServiceEndDate(serviceEndDate);  //设置服务结束时间
            ordOrder.setTaskNumber(orderTemp.getTaskNumber());
            ordOrder.setCustomerId(userDTO.getCusId());
            ordOrder.setBillingStatus(OrderConstant.BILLING_STATUS_NO);  //默认为0
            ordOrder.setLogisticsStatus(OrderConstant.ZERO);  //物流不需要配送


            //插入人员组织-订单表
            List<OrdOrderUserDep> ordOrderUserDeps = new ArrayList<>();
            userDTO.getRelationList().forEach(user->{
                OrdOrderUserDep ordOrderUserDep = new OrdOrderUserDep();
                ordOrderUserDep.setBusinessType(user.getBusinessType());
                ordOrderUserDep.setInsId(user.getInsId());
                ordOrderUserDep.setDistriType(Short.valueOf(user.getDistriType()));
                ordOrderUserDep.setOwnerId(user.getOwnerId());
                ordOrderUserDep.setSrcId(user.getSrcId());
                ordOrderUserDep.setOrderId(orderId);
                ordOrderUserDep.setId(idGenerator.getLong());
                ordOrderUserDep.setCreateUser(userId);
                ordOrderUserDeps.add(ordOrderUserDep);
            });
            ordOrder.setRelationList(ordOrderUserDeps);
            ordOrders.add(ordOrder);  //添加进入集合
            ordOrderUserDepsAll.addAll(ordOrderUserDeps);

            //插入流水表
            OrdPayFlowingDTO ordPayFlowing = new OrdPayFlowingDTO();
            Long payId = idGenerator.getLong();
            ordPayFlowing.setPayId(payId); //默认拿0为运营平台本身的支付appId
            ordPayFlowing.setPayWay(PayConstant.PAYWAY_ALIYDT);
            ordPayFlowing.setPayTime(orderTemp.getPayTime());
            ordPayFlowing.setPayAppId(payAppId);
            ordPayFlowing.setPayNo(ordOrder.getBusinessOrderNo()); //支付流水号-业务订单号
            ordPayFlowing.setCreateUser(userId);
            ordPayFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_REPLACE_PAY);
            ordPayFlowings.add(ordPayFlowing); //添加进入集合

            //如果是基础商品需要插入账单表
            if(ifBase){
                OrdBill ordBill = new OrdBill();
                ordBill.setBillId(idGenerator.getLong());
                ordBill.setOrderId(orderId);
                ordBill.setPayId(payId);
                ordBill.setBillType(BillConstant.BILL_TYPE_FULL);
                ordBill.setBillStartTime(orderTemp.getPayTime());
                ordBill.setCreateUser(userId);
                ordBill.setPayStatus(3);  //代付款
                String billDateStr = LocalDateUtil.getDateStr(LocalDateUtil.YMD, new Date());
                String billCodeKey = String.format(idUtil.createSequenceId("bill_code_key"), "1");
                ordBill.setBillCode(billDateStr + billCodeKey);
                ordBills.add(ordBill);
            }

            //订单-流水表关联表
            OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
            ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
            ordPayFlowingRef.setOrderId(orderId);
            ordPayFlowingRef.setPayId(payId);
            ordPayFlowingRef.setCreateUser(userId);
            ordPayFlowingRefs.add(ordPayFlowingRef); //添加进入集合

        });

        if(!ordOrders.isEmpty()) ordOrderExtMapper.insertOrderInBatch(ordOrders); //批量insert
        if(!ordPayFlowings.isEmpty()) ordPayFlowingExtMapper.insertPayFlowingInBatch(ordPayFlowings);
        if(!ordPayFlowingRefs.isEmpty())ordPayFlowingRefMapper.insertBatch(ordPayFlowingRefs);
        if(!ordOrderUserDepsAll.isEmpty())ordOrderUserDepMapper.insertBatch(ordOrderUserDepsAll);
        if(!ordBills.isEmpty())ordBillMapper.insertBatch(ordBills);


        //异步执行错误数据更新错误信息
        if(!troubleList.isEmpty()){
            BatchExcelServiceImpl that = this;  //入参保存this
            Thread updateTroubleT = new Thread(() -> {
                logger.info("异步执行错误数据更新错误信息!");
                List<OrdExcelDataTemp> list = normalList.stream().filter(orderTemp->!OrderExcelTempConstant.troubleData.equals(orderTemp.getIsNormal())).collect(Collectors.toList());
                list.forEach(ordExcel->ordExcel.setIsNormal(OrderExcelTempConstant.normalData));
                if(!list.isEmpty()) AppUtils.aopWrap(that).batchUpdate(list);
                AppUtils.aopWrap(that).batchUpdate(troubleList); //获得代理类执行方法
            });
            updateTroubleT.start();
        }

        //ordOrders的数据调用任务生成接口 异步执行调用生成task任务
        Thread taskThread = new Thread(()->{
            ordOrders.forEach(ordOrderDTO -> {
                AddTaskDTO addTaskDTO = new AddTaskDTO();
                addTaskDTO.setCreateUser(ordOrderDTO.getCreateUser());
                addTaskDTO.setOrderId(ordOrderDTO.getOrderId());
                addTaskDTO.setCusId(ordOrderDTO.getCustomerId());
                addTaskDTO.setGoodsId(ordOrderDTO.getGoodsId());
                addTaskDTO.setGoodsSkuId(ordOrderDTO.getSkuId());
                addTaskDTO.setTaskPeriods(ordOrderDTO.getTaskNumber().toString());
                addTaskDTO.setEffectiveTime(ordOrderDTO.getOrderTime().getTime());
                List<RelationDTO> relationDTOList = ordOrderDTO.getRelationList().stream().
                        map(rel->{
                            RelationDTO relationDTO = new RelationDTO();
                            relationDTO.setSrcId(rel.getSrcId());
                            relationDTO.setBusinessType(rel.getBusinessType());
                            relationDTO.setDistriType(rel.getDistriType());
                            relationDTO.setOwnerId(rel.getOwnerId());
                            relationDTO.setInsId(rel.getInsId());
                            return relationDTO;
                        }).collect(Collectors.toList());
                addTaskDTO.setRelationList(relationDTOList);
                orderTaskInnerApi.addTask(addTaskDTO);
            });
        });
        taskThread.start();

        return troubleList;
    }


    public void parseAndSave(File excelFile,Long excelId,int start,int end) throws Exception{
        List<OrderTemplate> objList = ExcelUtil.parseExcel(excelFile, OrderTemplate.class,start,end);
        if(objList.isEmpty()){
            logger.info("对应的文件解析到有效数据为空！");
            return;
        }
        List<OrdExcelDataTemp> ordExcelDataTemps = objList.stream().map(obj->{
            OrdExcelDataTemp ordExcelDataTemp = new OrdExcelDataTemp();
            try {
                BeanUtils.copyProperties(ordExcelDataTemp,obj);
            } catch (IllegalAccessException e) {
                logger.info(e.getMessage());
                return new OrdExcelDataTemp();
            } catch (InvocationTargetException e) {
                logger.info(e.getMessage());
                return new OrdExcelDataTemp();
            }
            ordExcelDataTemp.setExcelId(excelId);
            ordExcelDataTemp.setDataId(idGenerator.getLong());
            ordExcelDataTemp.setPayTime(DateTimeUtils.parseDate(obj.getPayTimeTemp(),"yyyy-MM-dd HH:mm"));
            String orderAmount = obj.getOrderAmountTemp();
            if (orderAmount.matches("\\d{1,10}(\\.\\d{0,2})?")) {
                ordExcelDataTemp.setOrderAmount(BigDecimal.valueOf(Double.parseDouble(orderAmount)));
            }
            return ordExcelDataTemp;
        }).collect(Collectors.toList());
        logger.info("解析文件成功,解析到有效数据条数为:"+ordExcelDataTemps.size());
        ordExcelDataTempMapper.batchInsert(ordExcelDataTemps); //绕过框架dao拦截
    }




    @Override
    public void validTempData(Long excelId) {

    }

    @Transactional
    public int batchUpdate(List<OrdExcelDataTemp> OrdExcelDataTemps){
        return ordExcelDataTempMapper.batchUpdate(OrdExcelDataTemps);
    }

    /**
     * 生成订单编码
     * @return
     */
    private String generateOrderCode() {
        String prefixStr = LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = this.idUtil.createSequenceId(OrderConstant.ORDER_CODE_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    public static void main(String[] args) {
        Map<String, Object> ma = new HashMap();
        ma.put(null, "1");
        ma.put(null, "2");
        ma.put("1", "2");
        ma.get(null);
        System.out.println(ma.get(null));

    }

}