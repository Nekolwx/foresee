/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.account.api.dto.AccountQueryBalanceDTO;
import com.foresee.ftcsp.account.api.dto.AccountQueryBalanceResultDTO;
import com.foresee.ftcsp.account.api.inner.AccountInnerApi;
import com.foresee.ftcsp.common.core.dict.FtcspDictHolder;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.redis.IRedisClient;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.client.FtcspInnerRestClient;
import com.foresee.ftcsp.common.core.util.Jackson;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.auto.model.PdtGoods;
import com.foresee.ftcsp.goods.auto.model.PdtGoodsSku;
import com.foresee.ftcsp.goods.common.CommonDict;
import com.foresee.ftcsp.goods.manual.dto.PdtBillingInformationDTO;
import com.foresee.ftcsp.goods.manual.restdto.QueryGoodInfoByIdDTO;
import com.foresee.ftcsp.goods.manual.restdto.QueryInvoiceInfoDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.GoodsInformationDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryBriefGoodsForOrder;
import com.foresee.ftcsp.mq.producer.IMessageProducer;
import com.foresee.ftcsp.order.auto.dao.OrdBillItemMapper;
import com.foresee.ftcsp.order.auto.dao.OrdBillMapper;
import com.foresee.ftcsp.order.auto.dao.OrdOrderMapper;
import com.foresee.ftcsp.order.auto.dao.OrdPayFlowingMapper;
import com.foresee.ftcsp.order.auto.model.OrdBillItem;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.auto.model.OrdPayFlowing;
import com.foresee.ftcsp.order.constant.*;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.manual.dto.request.MobilePayEntranceDTO;
import com.foresee.ftcsp.order.manual.dto.request.PayRequestDTO;
import com.foresee.ftcsp.order.manual.dto.response.PayResPonseDTO;
import com.foresee.ftcsp.order.manual.restdto.*;
import com.foresee.ftcsp.order.service.*;
import com.foresee.ftcsp.order.service.config.PayByPublicNumberConfig;
import com.foresee.ftcsp.order.service.config.PayByScanCodeConfig;
import com.foresee.ftcsp.order.service.config.PayChannelConfig;
import com.foresee.ftcsp.order.service.config.PayRewardConfig;
import com.foresee.ftcsp.order.util.CommonUtil;
import com.foresee.ftcsp.order.util.PaySign;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.security.api.inner.SecurityInnerApi;
import com.foresee.ftcsp.security.manual.dto.SecUserDTO;
import com.foresee.ftcsp.security.manual.restdto.QueryUserInfoByUserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import com.amazonaws.Response;
//import com.foresee.ftcsp.account.manual.dto.RechargeCoinDTO;
//import com.foresee.ftcsp.account.manual.dto.RechargeCoinResultDTO;

/**
 * <pre>
 * 支付实现类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional
public class PayServiceImpl implements IPayService {

    private Logger              LOGGER       = Logger.getLogger(this.getClass());

    private Gson                gson         = new GsonBuilder().disableHtmlEscaping().create();

    @Autowired
    private PayRewardConfig payRewardConfig;

    @Resource
    private IOrderService       orderService;

    @Resource
    private IPayFlowingService  payFlowingService;

    @Resource
    private IGoodsInnerService goodsInnerService;

    @Resource
    private OrdPayFlowingMapper payFlowingMapper;

    @Resource
    private IOrdBillService     billService;
    
    @Resource
    private IMessageProducerService messageProducerService;

    @Resource
    private OrdBillMapper       ordBillMapper;

    @Resource
    private OrdBillItemMapper   ordBillItemMapper;

    @Resource
    private OrdOrderMapper      ordOrderMapper;
    
    @Resource
    private OrdOrderExtMapper ordOrderExtMapper;

    @Autowired
    private IdGenerator         idGenerator;

    @Autowired
    FtcspInnerRestClient        restClient;

    @Autowired
    PayByScanCodeConfig         payByScanCodeConfig;

    @Autowired
    PayByPublicNumberConfig     payByPublicNumberConfig;
    
    @Autowired
    PayChannelConfig PayChannelConfig;

    @Autowired
    GoodsApi                    goodsApi;
    
    @Autowired
    private AccountInnerApi accountApi;
    
    @Autowired
    private IMessageProducer producer;

    @Resource
    private FtcspDictHolder     ftcspDictHolder;
    
    @Autowired
    IRedisClient redisClient;

    @Autowired
    private IOrderDataProcessingService orderDataProcessingService;

    @Autowired
    private SecurityInnerApi securityInnerApi;

    /**
     * 错误信息参数。
     */
    public static String        ERRORMESSAGE = "errorMessage";

    /**
     * 成功标识参数。
     */
    public static String        SUCCESSFLAG  = "successFlag";


    @Override
    public Map<String, Object> payByScanCode(PayDTO payDTO) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        OrdOrderDTO ordOrderDTO = new OrdOrderDTO();
        ordOrderDTO.setOrderCode(payDTO.getOrderCode());
        ordOrderDTO = orderService.queryOrderPayInfoByCode(ordOrderDTO);
        if (ordOrderDTO != null) {
            ordOrderDTO.setPaySuccessRetUrl(payDTO.getPaySuccessRetUrl());
            ordOrderDTO.setPayNotifyUrl(payDTO.getPayNotifyUrl());
            ordOrderDTO.setPayWay(payDTO.getPayWay());
            String goodsName = getGoodsNameByGoodsId(ordOrderDTO.getGoodsId());
            ordOrderDTO.setGoodsName(goodsName);
            String reuestParma = dealinvokPayParma(ordOrderDTO);
            RestResponse restResponse = invokPay(reuestParma);
            OrdPayFlowing payFlowing = new OrdPayFlowing();
            payFlowing.setPayId(ordOrderDTO.getPayId());
            int payWay = Integer.parseInt(ordOrderDTO.getPayWay());
            payFlowing.setPayWay(payWay);
            payFlowingMapper.updateByPrimaryKeySelective(payFlowing);
            resultMap.put("restResponse", restResponse);
            resultMap.put("successFlag", true);
            return resultMap;
        } else {
            resultMap.put(SUCCESSFLAG, false);
            resultMap.put(ERRORMESSAGE, String.format("查询不到订单号【%s】的订单", payDTO.getOrderCode()));
            return resultMap;
        }

    }

    /**
     * 调用支付接口。
     * @param 请求报文
     * @return 返回回文
     */
    public RestResponse invokPay(String parma) {
        String url = String.format("http://pay/outter/payService/pay", restClient.getDeveloperName());
        RestResponse restResponse = restClient.post(url, parma);
        return restResponse;
    }

    /**
     * 封装扫码支付请求参数。
     * @param ordOrderDTO
     * @return String 请求报文
     */
    String dealinvokPayParma(OrdOrderDTO ordOrderDTO) {
        String payChannel = ordOrderDTO.getPayWay();

        Map<String, String> param = new HashMap<String, String>();

        if (String.valueOf(PayConstant.PAYWAY_ALIPAY).equals(payChannel)) {
            param.put("pay_app_id", payByScanCodeConfig.getAlipayPayAppId());
            param.put("pay_channel_id", payByScanCodeConfig.getAlipayChannelId());
            param.put("pay_account_id", payByScanCodeConfig.getAlipayAccountId());

        }

        if (String.valueOf(PayConstant.PAYWAY_WECHAT).equals(payChannel)) {
            param.put("pay_app_id", payByScanCodeConfig.getWechatPayAppId());
            param.put("pay_channel_id", payByScanCodeConfig.getWechatPayChannelId());
            param.put("pay_account_id", payByScanCodeConfig.getWechatPayAccountId());

        }

        // 更新APPID
        OrdPayFlowing payFlowing = new OrdPayFlowing();
        payFlowing.setPayId(ordOrderDTO.getPayId());
        payFlowing.setPayAppId(param.get("pay_app_id"));
        payFlowingMapper.updateByPrimaryKeySelective(payFlowing);

        param.put("pay_sign", "");
        param.put("pay_subject", ordOrderDTO.getGoodsName());
        param.put("pay_timestamp", String.valueOf(new Date().getTime()));
        param.put("pay_ret_url", ordOrderDTO.getPaySuccessRetUrl());
        param.put("pay_notify_url", ordOrderDTO.getPayNotifyUrl());
        param.put("pay_biz_order_id", ordOrderDTO.getPayNo());
        param.put("pay_amount", String.valueOf(ordOrderDTO.getPayAmount()));
        param.put("pay_order_time", String.valueOf(ordOrderDTO.getCreateTime().getTime()));
        param.put("pay_invalid_time", String.valueOf(new Date().getTime()));
        param.put("pay_body", ordOrderDTO.getGoodsName());
        param.put("pay_api_version", "1.0");
        String sign = PaySign.genSign(param, payByScanCodeConfig.getPaySignKey());
        param.put("pay_sign", sign);
        String json = gson.toJson(param);
        return json;
    }

    /**
     * 封装打赏支付请求参数。
     * @param ordOrderDTO
     * @return String 请求报文
     */
    String dealInvokRewardPayParma(OrdOrderDTO ordOrderDTO) {
        Map<String, String> param = new HashMap<String, String>();

        String channel = String.valueOf(ordOrderDTO.getChannel());
        String appId = ftcspDictHolder.getDictItemValue("pay_appId_channel", channel);
        if (appId == null || !appId.isEmpty()) {
            //订单来源对应的appId配置不存在，暂不支持支付
        }

        String signKey = ftcspDictHolder.getDictItemValue("pay_signKey_appId", appId);
        if (signKey == null || !signKey.isEmpty()) {
            //应用ID对应的签名秘钥配置不存在，暂不支持支付
        }

        String payWay = ordOrderDTO.getPayWay();
        String channelId = ftcspDictHolder.getDictItemValue("pay_channelId_payway", payWay);
        if (channelId == null || !channelId.isEmpty()) {
            //支付方式对应渠道ID配置不存在，暂不支持支付
        }

        String appIdPayWayConcat = appId + "-" + payWay;
        String accountId = ftcspDictHolder.getDictItemValue("pay_accountId_appId_payway", appIdPayWayConcat);
        if (accountId == null || !accountId.isEmpty()) {
            //应用ID和支付方式确定的账户ID配置不存在，暂不支持支付
        }
        //财币支付的多传个payUserId
        if(PayConstant.PAYWAY_CAIBI == Integer.parseInt(payWay)){
            param.put("pay_user_id",ordOrderDTO.getOrderUser());
        }

        //微信h5支付时需要多传参数
        if (PayConstant.PAYWAY_WECHAT_MOBILE == Integer.parseInt(payWay) ) {
            param.put("pay_client_ip", ordOrderDTO.getPayClientIp());
        }

        //禁用信用卡支付
        param.put("pay_disable_credit", "true");
        param.put("pay_sign", "");
        param.put("pay_app_id", appId);
        param.put("pay_channel_id", channelId);
        param.put("pay_account_id", accountId);
        param.put("pay_subject", ordOrderDTO.getGoodsName());
        param.put("pay_timestamp", String.valueOf(new Date().getTime()));
        param.put("pay_ret_url", ordOrderDTO.getPaySuccessRetUrl());
        param.put("pay_notify_url", ordOrderDTO.getPayNotifyUrl());
        param.put("pay_biz_order_id", ordOrderDTO.getPayNo());
        param.put("pay_amount", String.valueOf(ordOrderDTO.getPayAmount()));
        param.put("pay_order_time", String.valueOf(ordOrderDTO.getCreateTime().getTime()));
        param.put("pay_invalid_time", String.valueOf(new Date().getTime()));
        param.put("pay_body", ordOrderDTO.getGoodsName());
        param.put("pay_api_version", "1.0");
        String sign = PaySign.genSign(param, signKey);

        param.put("pay_sign", sign);
        String json = gson.toJson(param);
        return json;
    }

    @Override
    public Map<String, Object> payByPublicNumber(GetPayParamDTO payParamDTO) {
        Map<String, Object> reultMap = new HashMap<String, Object>();
        RestResponse<GoodsInformationDTO> response = goodsApi.queryGoodsInfoBySkuId(payParamDTO.getSkuId());
        if("0".equals(response.getHead().getErrorCode())){
            GoodsInformationDTO goodsInformationDTO = response.getBody();
            if(goodsInformationDTO == null){
                reultMap.put("errorCode", "1");
                reultMap.put("errorMsg", "所传goodsSkuId对应的商品不存在或者已删除");
                return reultMap;
            }else{
                if(goodsInformationDTO.getGoodsStatus() != CommonDict.GOODS_STATUS_UP_SHELVES){
                    reultMap.put("errorCode", "1");
                    reultMap.put("errorMsg", "所传goodsSkuId对应的商品不是上架状态");
                    return reultMap;
                }
                payParamDTO.setGoodsId(goodsInformationDTO.getGoodsId());
            }

        } else{
            reultMap.put("errorCode", "1");
            reultMap.put("errorMsg", response.getHead().getErrorMsg());
            return reultMap;
        }
        // 发送异步队列创建订单消息
        messageProducerService.sendOrderData(payParamDTO);
        // 更新支付来源
        /*OrdPayFlowingDTO payFlowingDTO = new OrdPayFlowingDTO();
        payFlowingDTO.setPayNo(payParamDTO.getPayBizOrderId());
        payFlowingDTO.setPayWay(PayConstant.PAYWAY_WECHAT);
        payFlowingDTO.setAppId(payParamDTO.getPayAppId());
        payFlowingService.updateByPayNoSelective(payFlowingDTO);*/
        // 调用支付接口 

        reultMap.put("errorCode", "0");
        String jsonStr = dealinvokPayParma(payParamDTO);
        RestResponse restResponse = invokPay(jsonStr);
        PayResPonseDTO payResPonse = new PayResPonseDTO();
        if (restResponse.getHead().isSuccess() && restResponse.getBody() != null) {
            @SuppressWarnings("unchecked") Map<String, Object> map = (Map<String, Object>) restResponse.getBody();
            @SuppressWarnings("unchecked") Map<String, Object> wxJsapi = (Map<String, Object>) map.get("wx_jsapi_params");
            try {
                payResPonse.setAppId(URLEncoder.encode(wxJsapi.get("appId").toString(), "utf8"));
                payResPonse.setTimeStamp(wxJsapi.get("timeStamp").toString());
                payResPonse.setNonceStr(URLEncoder.encode(wxJsapi.get("nonceStr").toString(), "utf8"));
                payResPonse.setPackageName(URLEncoder.encode(wxJsapi.get("package").toString(), "utf8"));
                payResPonse.setSignType(URLEncoder.encode(wxJsapi.get("signType").toString(), "utf8"));
                payResPonse.setPaySign(URLEncoder.encode(wxJsapi.get("paySign").toString(), "utf8"));
            } catch (Exception e) {
                LOGGER.error(e);
                return null;
            }

        } else {
            String errorMsg = restResponse.getHead().getErrorMsg();
            reultMap.put("errorCode", "1");
            reultMap.put("errorMsg", errorMsg);
            return reultMap;
        }
        try {
            payResPonse.setPayProject(URLEncoder.encode(payParamDTO.getPayProject(), "utf8"));
            payResPonse.setPayRetUrl(URLEncoder.encode(payParamDTO.getPayRetUrl(), "utf8"));
            payResPonse.setPayFailureUrl(URLEncoder.encode(payParamDTO.getPayFailureUrl(), "utf8"));

        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }

        String payUrl = String.format(
                "http://" + payByPublicNumberConfig.getPayPageDomainName()
                        + "/wechatPay/pay/payWeChat.html?orderPrice=%s&payProject=%s&paySign=%s&packageName=%s&nonceStr=%s&timeStamp=%s&appId=%s&signType=%s&payRetUrl=%s&payFailureUrl=%s",
                payParamDTO.getPayAmount(), payResPonse.getPayProject(), payResPonse.getPaySign(), payResPonse.getPackageName(),
                payResPonse.getNonceStr(), payResPonse.getTimeStamp(), payResPonse.getAppId(), payResPonse.getSignType(),
                payResPonse.getPayRetUrl(), payResPonse.getPayFailureUrl());
        reultMap = new HashMap<String, Object>();
        reultMap.put("payUrl", payUrl);
        return reultMap;
    }

    /**
     * 封装微信公众号支付请求参数。
     * @param payParamDTO
     * @return String
     */
    String dealinvokPayParma(GetPayParamDTO payParamDTO) {
        PayRequestDTO payRequest = new PayRequestDTO();
        payRequest.setPay_app_id(payParamDTO.getPayAppId());
        payRequest.setPay_channel_id(payParamDTO.getPayChannelId());
        payRequest.setPay_account_id(payParamDTO.getPayAccountId());
        payRequest.setPay_open_id(payParamDTO.getPayOpenId());
        payRequest.setPay_timestamp(payParamDTO.getPayTimestamp());
        payRequest.setPay_client_ip(payParamDTO.getPayClientIp());
        payRequest.setPay_ret_url(payParamDTO.getPayRetUrl());
        payRequest.setPay_notify_url(payParamDTO.getPayNotifyUrl());
        payRequest.setPay_biz_order_id(payParamDTO.getPayBizOrderId());
        payRequest.setPay_amount(payParamDTO.getPayAmount());
        payRequest.setPay_order_time(payParamDTO.getPayOrderTime());
        payRequest.setPay_invalid_time(payParamDTO.getPayInvalidTime());
        payRequest.setPay_subject(payParamDTO.getPaySubject());
        payRequest.setPay_body(payParamDTO.getPayBody());
        payRequest.setPay_show_url(payParamDTO.getPayShowUrl());
        payRequest.setPay_api_version(payParamDTO.getPayApiVersion());
        payRequest.setPay_sign(payParamDTO.getPaySign());
        String json = gson.toJson(payRequest);
        return json;
    }

    /**
     * 根据商品ID获取商品名称。
     * @param goodsId
     * @return String
     */
    public String getGoodsNameByGoodsId(Long goodsId) {
        QueryGoodInfoByIdDTO goodsDTO = new QueryGoodInfoByIdDTO();
        goodsDTO.setGoodsId(goodsId);
        RestResponse<PdtGoods> restResponse = goodsApi.queryGoodInfoById(goodsDTO);

        if (restResponse != null && restResponse.getBody() != null) {
            return restResponse.getBody().getGoodsName();
        } else {
            return "商品详情描述";
        }

    }

    @Override
    public Map<String, Object> payCallBack(ReceivePayCallBackDTO receivePayCallBackDTO) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESSFLAG, false);
        OrdPayFlowingDTO payFlowing = new OrdPayFlowingDTO();
        String payNo = receivePayCallBackDTO.getBizOrderId();
        payFlowing.setPayNo(payNo);
        if (!StringUtils.isEmpty(receivePayCallBackDTO.getAppId())) {
            payFlowing.setAppId(receivePayCallBackDTO.getAppId());
        }
        OrdPayFlowingDTO resultPayFlowing = payFlowingService.queryPayFlowingInfoByPayNo(payFlowing);
        if (resultPayFlowing != null) {
            if (PayConstant.PAY_STATUS_SUCCESS.equals(receivePayCallBackDTO.getPayStatus())) {
                if(resultPayFlowing.getPayState() != PayConstant.FLOWING_PAY_STATUS_SUCCESS){
  
                    List<OrdPayFlowingDTO> payFlowinglist = payFlowingService.queryOrderIdByPayNo(payFlowing);
                    /**
                     * 查询结果大于1条时，有父子关系订单 
                     * 查询子订单，账单生成子订单的账单 
                     * 无子订单时，账单生成父订单的账单
                     */
                    if (payFlowinglist != null && !payFlowinglist.isEmpty() && payFlowinglist.size() > 1) {
                        payFlowing.setIsParent("1");
                        payFlowinglist = payFlowingService.queryOrderIdByPayNo(payFlowing);
                    }
                    if (payFlowinglist != null && !payFlowinglist.isEmpty()) {
                        // 批量更新对应订单的订单状态,支付状态，服务状态，服务时间。
                        payFlowinglist.stream().forEach(ordPayFlowingDTO -> orderService.updateOrderSatusByOrdSatus(orderDataProcessingService.afterPaymentToOrdSattus(ordPayFlowingDTO.getOrderId()),ordPayFlowingDTO.getOrderId()));
                        OrdBillDTO billDTO = setPayBillInfo(receivePayCallBackDTO);
                        for (int i = 0; i < payFlowinglist.size(); i++) {
                            // 新增账单
                            billDTO.setBillId(idGenerator.getLong());
                            billDTO.setPayId(resultPayFlowing.getPayId());
                            billDTO.setOrderId(payFlowinglist.get(i).getOrderId());
                            billDTO.setBillAmount(payFlowinglist.get(i).getPayAmount());
                            billService.createBill(billDTO);
                            // 查询出账单所对应的订单
                            OrdOrder orderRef = ordOrderMapper.selectByPrimaryKey(payFlowinglist.get(i).getOrderId());
                            // 新增账单项目表
                            OrdBillItem ordBillItem = new OrdBillItem();
                            ordBillItem.setBillItemId(idGenerator.getLong());
                            ordBillItem.setBillId(billDTO.getBillId());
                            ordBillItem.setUnit(orderRef.getGoodsQuantityUnit()+"");
                            ordBillItem.setQty(orderRef.getGoodsQuantity());
                            ordBillItem.setAmount(payFlowinglist.get(i).getPayAmount());
                            if(orderRef.getGoodsQuantity()!=null){
                                ordBillItem.setPrice(payFlowinglist.get(i).getPayAmount().divide(
                                    new BigDecimal(orderRef.getGoodsQuantity()), 2, BigDecimal.ROUND_UP));
                            }else{
                                ordBillItem.setPrice(payFlowinglist.get(i).getPayAmount());
                            }

                            //获取goodsSkuID查询内部inner接口获取项目名称和税率
                            QueryInvoiceInfoDTO queryInvoiceInfoDTO = new QueryInvoiceInfoDTO();
                            queryInvoiceInfoDTO.setGoodsSkuId(orderRef.getSkuId());
                            RestResponse<PdtBillingInformationDTO> restResponse= goodsApi.queryInvoiceInfoBySkuId(queryInvoiceInfoDTO);
                            if("0".equals(restResponse.getHead().getErrorCode())){
                                PdtBillingInformationDTO pdtBillingInformationDTO = restResponse.getBody();
                                if(pdtBillingInformationDTO != null ){
                                    ordBillItem.setItemCode(pdtBillingInformationDTO.getBillingItemsCode());
                                    ordBillItem.setItemName(pdtBillingInformationDTO.getBillingItemsName());
                                    ordBillItem.setTaxRate(pdtBillingInformationDTO.getTaxRate());
                                }
                            }
                            ordBillItemMapper.insertSelective(ordBillItem);
                        }

                        // 更新支付流水表
                        OrdPayFlowing newPayFlowing = new OrdPayFlowing();
                        newPayFlowing.setPayId(resultPayFlowing.getPayId());
                        Date payTime = new Date();
                        try {
                            payTime = LocalDateUtil.getDateByStr(LocalDateUtil.Y_M_D_HH_MM_SS, receivePayCallBackDTO.getPaySuccessTime());
                        } catch (ParseException e) {
                            LOGGER.info(String.format("支付成功，时间转换格式出错，但不影响业务，支付时间使用了当前时间，对应支付流水号%s,具体错误原因:%s", payNo, e));
                        }
                        newPayFlowing.setPayTime(payTime);
                        newPayFlowing.setThreePartyNo(receivePayCallBackDTO.getPayTradeCode());
                        newPayFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_SUCCESS);
                        payFlowingMapper.updateByPrimaryKeySelective(newPayFlowing);

                        resultMap.put(SUCCESSFLAG, true);
                        
                      
                    }
                }
                
                
                
              /*调用财币内部充值接口改由业务方实现
               * //若商品为充值商品，调用内部充值接口
                OrdPayFlowingDTO ordPayFlowingDTO = new OrdPayFlowingDTO();
                ordPayFlowingDTO.setPayNo(payNo);
                ordPayFlowingDTO.setAppId(receivePayCallBackDTO.getAppId());
                OrdOrderDTO ordOrderDTO = orderService.queryOrderPayInfoByPayNo(ordPayFlowingDTO);
                String goodsSkuId = ftcspDictHolder.getDictItemValue("fiscal_service_goods", "recharge");
                if(ordOrderDTO != null && ordOrderDTO.getSkuId() == Long.parseLong(goodsSkuId)){
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("version", "1.0");
                    String timeStamp = String.valueOf(new Date().getTime());
                    param.put("timeStamp",timeStamp);
                    param.put("appId", AccountConstant.APPID);
                    //用支付流水表主键作外部交易号
                    param.put("outTradeNo", ordOrderDTO.getPayId()+"");
                    param.put("subject", AccountConstant.SUBJECT);
                    param.put("userId", ordOrderDTO.getOrderUser());
                    param.put("amount", ordOrderDTO.getPayAmount()+"");
                    param.put("payTradeNo", receivePayCallBackDTO.getPayTradeCode());        
                    String  signKey = SignEncryptConstant.RechargeCoinPaySalt;
                    String sign = PaySign.genSign(param, signKey);
                    
                    
                    RechargeCoinDTO rechargeCoinDTO = new RechargeCoinDTO();
                    rechargeCoinDTO.setVersion("1.0");
                    rechargeCoinDTO.setTimeStamp(timeStamp);
                    rechargeCoinDTO.setAppId(AccountConstant.APPID);
                    rechargeCoinDTO.setOutTradeNo(ordOrderDTO.getPayId()+"");
                    rechargeCoinDTO.setSubject(AccountConstant.SUBJECT);
                    rechargeCoinDTO.setUserId(ordOrderDTO.getOrderUser());
                    rechargeCoinDTO.setAmount(ordOrderDTO.getPayAmount()+"");
                    rechargeCoinDTO.setPayTradeNo(receivePayCallBackDTO.getPayTradeCode());
                    rechargeCoinDTO.setSign(sign);   
                    RestResponse<RechargeCoinResultDTO> restResponse = accountApi.rechargeCoin(rechargeCoinDTO);
                    if(!"0".equals(restResponse.getHead().getErrorCode())){
                        resultMap.put(SUCCESSFLAG, false);
                        resultMap.put(ERRORMESSAGE, String.format("调用财币充值内部接口失败：%s",restResponse.getHead().getErrorMsg()));  
                    }else{
                        resultMap.put(SUCCESSFLAG, true);
                    }   
                }*/
                
            } else {
                resultMap.put(ERRORMESSAGE, String.format("支付回调返回状态不是SUCCESS，返回状态是：%s", receivePayCallBackDTO.getPayStatus()));
            }

        } else {
            resultMap.put(ERRORMESSAGE, String.format("交易流水号【%s】不存在, 支付应用ID为【%s】", payNo, receivePayCallBackDTO.getAppId()));
        }
        return resultMap;
    }

    /**
     * 设置账单信息。
     * @param receivePayCallBackDTO
     * @return OrdBillDTO
     */
    public OrdBillDTO setPayBillInfo(ReceivePayCallBackDTO receivePayCallBackDTO) {
        OrdBillDTO billDTO = new OrdBillDTO();
        billDTO.setBillType(BillConstant.BILL_TYPE_FULL);
        if (PayConstant.PAY_STATUS_SUCCESS.equals(receivePayCallBackDTO.getPayStatus())) {
            billDTO.setPayStatus(BillConstant.BILL_PAY_STATUS_ALREADY);
        } else {
            billDTO.setPayStatus(BillConstant.BILL_PAY_STATUS_FAIL);
        }
        billDTO.setInvoiceStatus(BillConstant.BILL_INVOICE_STATUS_NOT_BLUE);//设置开票状态未开票

        Date billingDate = new Date();
        try {
            LocalDateUtil.getDateByStr(LocalDateUtil.Y_M_D_HH_MM_SS, receivePayCallBackDTO.getPaySuccessTime());
        } catch (ParseException e) {
            LOGGER.info(String.format("生成账单成功，支付时间转换格式出错，但不影响业务，支付时间使用了当前时间，具体错误原因：%s", e));
        }
        billDTO.setPayTime(billingDate);
        return billDTO;
    }

    @Override
    public Map<String, Object> rewardPay(RewardPayDTO rewardPayDTO) {
        RewardPayInitDTO rewardPayInitDTO = new RewardPayInitDTO();
        rewardPayInitDTO.setBusinessOrderNo(rewardPayDTO.getBusinessOrderNo());
        rewardPayInitDTO.setPayTimestamp(rewardPayDTO.getPayTimestamp());
        rewardPayInitDTO.setSign(rewardPayDTO.getSign());
        Map<String, Object> resultMap = this.checkSign(rewardPayInitDTO);
        if (!(boolean) resultMap.get("successFlag")){
            return resultMap;
        }
        String json = (String)resultMap.get("json");
        
        Map<String, Object> requestParam = Jackson.jsonToMap(json);
        Boolean legalPayWay = false;
        @SuppressWarnings("unchecked") List<String> supportPayWays = (List<String>) requestParam.get("supportPayWays");
        for(int i=0;i<supportPayWays.size();i++){
            if(rewardPayDTO.getPayWay().equals(supportPayWays.get(i))){
                legalPayWay = true;
            }
        }
        if(!legalPayWay){
            resultMap.put(SUCCESSFLAG, false);
            resultMap.put(ERRORMESSAGE, "所选择的支付方式不支持");
            return resultMap;
        }
        OrdPayFlowingDTO ordPayFlowingDTO = new OrdPayFlowingDTO();
        ordPayFlowingDTO.setPayNo((String)requestParam.get("payNo"));
        ordPayFlowingDTO.setAppId((String)requestParam.get("payAppId"));
        OrdOrderDTO ordOrderDTO = orderService.queryOrderPayInfoByPayNo(ordPayFlowingDTO);
        
        if(ordOrderDTO != null){
            if(OrderConstant.PAY_STATUS_PAID == ordOrderDTO.getPayStatus()){
                resultMap.put(SUCCESSFLAG, false);
                resultMap.put(ERRORMESSAGE, "业务订单已付款，无需重复付款");
                return resultMap;
            }     
            ordOrderDTO.setPaySuccessRetUrl((String)requestParam.get("payRetUrl"));
            ordOrderDTO.setPayNotifyUrl((String)requestParam.get("payNotifyUrl"));
            ordOrderDTO.setPayWay(rewardPayDTO.getPayWay());
            
            String goodsName = getGoodsNameByGoodsId(ordOrderDTO.getGoodsId());
            ordOrderDTO.setGoodsName(goodsName);
            String reuestParma = dealInvokRewardPayParma(ordOrderDTO);
            RestResponse restResponse = invokPay(reuestParma);
            OrdPayFlowing payFlowing = new OrdPayFlowing();
            payFlowing.setPayId(ordOrderDTO.getPayId());
            int payWay = Integer.parseInt(ordOrderDTO.getPayWay());
            payFlowing.setPayWay(payWay);
            payFlowingMapper.updateByPrimaryKeySelective(payFlowing);
            resultMap.put("restResponse", restResponse);
            resultMap.put("successFlag", true);
            return resultMap;
        } else {
            resultMap.put(SUCCESSFLAG, false);
            resultMap.put(ERRORMESSAGE, String.format("查询不到支付流水号为【%s】的订单", (String)requestParam.get("payNo")));
            return resultMap;
        }

    }

    @Override
    public Map<String, Object> payByMobileTerminal(GetPayParamDTO payParamDTO) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if("null".equals(payParamDTO.getTaxIdentification().trim())){
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg","参数[tax_identification]不能为null");
            return resultMap;
        }
        if(PayChannelConfig.getWeChatPublicNumberChannel().equals(payParamDTO.getPayChannelId())){
            resultMap=this.payByPublicNumber(payParamDTO);
        }else if(PayChannelConfig.getWeChatHtmlChannel().equals(payParamDTO.getPayChannelId())||PayChannelConfig.getAlipayHtmlChannel().equals(payParamDTO.getPayChannelId())||PayChannelConfig.getUnionPayChannel().equals(payParamDTO.getPayChannelId())){
            resultMap=this.payByHtml(payParamDTO);
        }else{
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg", String.format("渠道ID:%s不合法", payParamDTO.getPayChannelId()));
        }
        
        return resultMap;
    }

    @Override
    public Map<String, Object> payByMobileTerminalExt(GetPayParamExtDTO payParamExtDTO) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        GetPayParamDTO payParamDTO = new GetPayParamDTO();
        try {
            BeanUtils.copyProperties(payParamDTO,payParamExtDTO);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("属性拷贝出错",e);
            resultMap.put("errorCode", "1");

            resultMap.put("errorMsg", "属性拷贝出错");

            return resultMap;
        }

        if(payParamDTO.getTaxIdentification() != null && "null".equals(payParamDTO.getTaxIdentification().trim())){
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg","参数[tax_identification]不能为null");
            return resultMap;
        }
        if(PayChannelConfig.getWeChatPublicNumberChannel().equals(payParamDTO.getPayChannelId())){
            resultMap=this.payByPublicNumber(payParamDTO);
        }else if(PayChannelConfig.getWeChatHtmlChannel().equals(payParamDTO.getPayChannelId())||PayChannelConfig.getAlipayHtmlChannel().equals(payParamDTO.getPayChannelId())||PayChannelConfig.getUnionPayChannel().equals(payParamDTO.getPayChannelId())){
            resultMap=this.payByHtml(payParamDTO);
        }else{
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg", String.format("渠道ID:%s不合法", payParamDTO.getPayChannelId()));
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> payByHtml(GetPayParamDTO payParamDTO) {
        Map<String, Object> reultMap = new HashMap<String, Object>();
        //调用inner接口查询商品是否存在并且已上架
        RestResponse<GoodsInformationDTO> response = goodsApi.queryGoodsInfoBySkuId(payParamDTO.getSkuId());
        if("0".equals(response.getHead().getErrorCode())){
            GoodsInformationDTO goodsInformationDTO = response.getBody();
            if(goodsInformationDTO == null){
                reultMap.put("errorCode", "1");
                reultMap.put("errorMsg", "所传goodsSkuId对应的商品不存在或者已删除");
                return reultMap;
            }else{
                if(goodsInformationDTO.getGoodsStatus() != CommonDict.GOODS_STATUS_UP_SHELVES){
                    reultMap.put("errorCode", "1");
                    reultMap.put("errorMsg", "所传goodsSkuId对应的商品不是上架状态");
                    return reultMap;
                }
                payParamDTO.setGoodsId(goodsInformationDTO.getGoodsId());
            }

        } else{
            reultMap.put("errorCode", "1");
            reultMap.put("errorMsg", response.getHead().getErrorMsg());
            return reultMap;
        }
        // 异步队列创建订单,并返回支付流水号
        messageProducerService.sendOrderData(payParamDTO);
        // 更新支付来源
        /*OrdPayFlowingDTO payFlowingDTO = new OrdPayFlowingDTO();
        payFlowingDTO.setPayNo(payParamDTO.getPayBizOrderId());
        if(PayChannelConfig.getWeChatHtmlChannel().equals(payParamDTO.getPayChannelId())){
            payFlowingDTO.setPayWay(PayConstant.PAYWAY_WECHAT);
        }else if(PayChannelConfig.getUnionPayChannel().equals(payParamDTO.getPayChannelId())){
            payFlowingDTO.setPayWay(PayConstant.PAYWAY_UNION);
        }else if(PayChannelConfig.getAlipayHtmlChannel().equals(payParamDTO.getPayChannelId())){
            payFlowingDTO.setPayWay(PayConstant.PAYWAY_ALIPAY);
        }
        payFlowingDTO.setAppId(payParamDTO.getPayAppId());
        payFlowingService.updateByPayNoSelective(payFlowingDTO);*/
        reultMap.put("errorCode", "0");
        String jsonStr = dealinvokPayParma(payParamDTO);
        @SuppressWarnings("rawtypes") RestResponse restResponse = invokPay(jsonStr);
        @SuppressWarnings("unused") PayResPonseDTO payResPonse = new PayResPonseDTO();
        if (restResponse.getHead().isSuccess() && restResponse.getBody() != null) {
            @SuppressWarnings("unchecked") Map<String, Object> map = (Map<String, Object>) restResponse.getBody();
            
            if(PayChannelConfig.getWeChatHtmlChannel().equals(payParamDTO.getPayChannelId())||PayChannelConfig.getAlipayHtmlChannel().equals(payParamDTO.getPayChannelId())){
                // 支付宝或者微信支付
                String prepaidUrl = map.get("prepaid_url").toString();
                reultMap.put("payUrl", prepaidUrl);
            }else if(PayChannelConfig.getUnionPayChannel().equals(payParamDTO.getPayChannelId())){
                // 银联支付
                String htmlText = map.get("html_text").toString();
                reultMap.put("payUrl", htmlText);
            }
            
        } else {
            String errorMsg = restResponse.getHead().getErrorMsg();
            reultMap.put("errorCode", "1");
            reultMap.put("errorMsg", errorMsg);
            return reultMap;
        }
        return reultMap;
    }

    @Override
    public Map<String, Object> rewardPayInit(RewardPayInitDTO rewardPayInitDTO) {
        Map<String, Object> resultMap = this.checkSign(rewardPayInitDTO);
        if (!(boolean) resultMap.get("successFlag")){
            return resultMap;
        }
        String json = (String)resultMap.get("json");
        
        Map<String, Object> requestParam = Jackson.jsonToMap(json);
        
        //调用inner接口查询財币余额
        AccountQueryBalanceDTO balanceDTO = new AccountQueryBalanceDTO();
        balanceDTO.setUserId((String) requestParam.get("userId"));
        balanceDTO.setAppId(AccountConstant.APPID);
        balanceDTO.setVersion("1.0");
        RestResponse<AccountQueryBalanceResultDTO> balanceDTORestResponse = null;
        try {
            balanceDTORestResponse = accountApi.queryBalance(balanceDTO);
        }catch (Exception e){
            LOGGER.info("查询財币余额调用失败\n"+e.getMessage());
        }
        BigDecimal balance = new BigDecimal(0);
        if(balanceDTORestResponse != null && balanceDTORestResponse.getBody() != null){
             balance = balanceDTORestResponse.getBody().getBalance();
        }
        Long goodsId = Long.parseLong((String)requestParam.get("goodsId"));
        String goodsName = getGoodsNameByGoodsId(goodsId);
        String goodsSkuId = (String)requestParam.get("skuId");
        //如果商品为充值商品，则不允许使用财币支付
        Boolean coinEnabled = true;
        if(goodsSkuId.equals(ftcspDictHolder.getDictItemValue("fiscal_service_goods", "recharge"))){
            coinEnabled = false;
        }
        // 如果商品不需要财币支付且需要把商品名称用接口传入的描述替代
        if(goodsSkuId.equals(ftcspDictHolder.getDictItemValue("pay_need_goods_describe", goodsSkuId))){
            coinEnabled = false;
            if(requestParam.get("goodsDescribe")!=null && !"".equals(requestParam.get("goodsDescribe"))){
                goodsName = requestParam.get("goodsDescribe").toString();
            }
        }

        
        Map<String,Object> showParam = new HashMap<>();
        showParam.put("balance",balance);
        showParam.put("coinEnabled", coinEnabled);
        showParam.put("goodsName", goodsName);
        
        showParam.put("payAmount",requestParam.get("payAmount"));
        showParam.put("payNo",requestParam.get("payNo"));
        showParam.put("date",requestParam.get("date"));
        showParam.put("paySuccessRetUrl", requestParam.get("payRetUrl"));
        showParam.put("supportPayWays", requestParam.get("supportPayWays"));
        resultMap.put(SUCCESSFLAG, true);
        resultMap.put("showParam", showParam);
        return resultMap;
        

    }
    
    /**
     * 校验签名和订单支付有效时间,若成功则返回请求参数
     * @param rewardPayInitDTO
     * @return Map<String,Object>
     */
    public Map<String, Object> checkSign(RewardPayInitDTO rewardPayInitDTO) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String currentTime = String.valueOf(System.currentTimeMillis());
        String payTimesatmp = rewardPayInitDTO.getPayTimestamp();
        if( Long.parseLong(currentTime) - Long.parseLong(payTimesatmp) > 3*60*60*1000 ){
            resultMap.put(SUCCESSFLAG, false);
            resultMap.put(ERRORMESSAGE, "业务订单支付时间已过期");
            return resultMap;
            
        }
        
        Map<String, String> signParam = new HashMap<>();
        signParam.put("businessOrderNo",rewardPayInitDTO.getBusinessOrderNo());
        signParam.put("payTimestamp", rewardPayInitDTO.getPayTimestamp());
        String newSign = PaySign.genSign(signParam, SignEncryptConstant.RewardOrderPaySalt);
        if(!newSign.equals(rewardPayInitDTO.getSign())){
            resultMap.put(SUCCESSFLAG, false);
            resultMap.put(ERRORMESSAGE, "签名非法");
            return resultMap;
        }
        String json = redisClient.get(rewardPayInitDTO.getBusinessOrderNo());
        //若从redis中取得的json为空，则表示redis缓存已过期
        if(StringUtils.isEmpty(json) || json == null ){
            resultMap.put(SUCCESSFLAG, false);
            resultMap.put(ERRORMESSAGE, "业务订单支付时间已过期");
            return resultMap;
        }
        
        resultMap.put(SUCCESSFLAG, true);
        resultMap.put("json", json);
        
        return resultMap;
    }

    @Override
    public String mobilePayEntrance(MobilePayEntranceDTO mobilePayEntranceDTO) {
        //基础创单对象
        OrderDTO orderDTO = new OrderDTO();
        //参数校验并且封装基础创单对象
        checkAndAssemble(mobilePayEntranceDTO, orderDTO);
        //对是否创单进行判断，最后返回待支付订单的下单时间orderTime
        Date orderTime = createPayOrder(mobilePayEntranceDTO, orderDTO);

        PayResPonseDTO payResPonse = new PayResPonseDTO();
        if(mobilePayEntranceDTO.getContainPublicNumber()){
            //封装公众号支付请求参数
            String json = assembleInvokParam(mobilePayEntranceDTO, orderTime);
            //发起公众号支付请求
            RestResponse restResponse = invokPay(json);
            //处理公众号支付请求结果
            dealInvokResult(mobilePayEntranceDTO,restResponse, payResPonse);
        }


        //将信息转成map并存储进redis
        Map<String, Object> redisMap = new HashMap<String, Object>();
        redisMap.put("skuId", mobilePayEntranceDTO.getGoodsSkuId() + "");
        redisMap.put("goodsId", mobilePayEntranceDTO.getGoodsId() + "");
        redisMap.put("userId", mobilePayEntranceDTO.getUserId());
        redisMap.put("payRetUrl", mobilePayEntranceDTO.getPayRetUrl());
        redisMap.put("payNotifyUrl", mobilePayEntranceDTO.getPayNotifyUrl());
        redisMap.put("payAmount", mobilePayEntranceDTO.getPayAmount());
        redisMap.put("goodsQuantity", mobilePayEntranceDTO.getGoodsQuantity());
        redisMap.put("payNo", mobilePayEntranceDTO.getBusinessOrderNo());
        redisMap.put("payAppId", mobilePayEntranceDTO.getPayAppId());
        redisMap.put("goodsDescribe", mobilePayEntranceDTO.getGoodsDescribe());
        redisMap.put("payClientIp", mobilePayEntranceDTO.getPayClientIp());
        redisMap.put("supportPayWays", mobilePayEntranceDTO.getSupportPayWays());
        redisMap.put("payResPonse", payResPonse);
        String key = OrderCenterRedisPrefix.ORDERCENTER_MOBILEPAY_URL_PREFIX + mobilePayEntranceDTO.getBusinessOrderNo() + mobilePayEntranceDTO.getChannel();
        redisClient.set(key,Jackson.toJson(redisMap),3, TimeUnit.HOURS);  //设置有效时间为三个小时，6秒为返回延时预留

        Map<String, String> signParam = new HashMap<>();
        String payTimestamp = String.valueOf(System.currentTimeMillis());
        signParam.put("businessUniqueNumber", mobilePayEntranceDTO.getBusinessOrderNo() + mobilePayEntranceDTO.getChannel());
        signParam.put("payTimestamp", payTimestamp);
        String sign = PaySign.genSign(signParam, SignEncryptConstant.MoblilePaymentValidateSalt);
        String payPageUrl = String
                .format(payRewardConfig.getMobilePayPageUrl() + "?businessUniqueNumber=%s&payTimestamp=%s&sign=%s"
                        ,signParam.get("businessUniqueNumber"), payTimestamp, sign);
        return payPageUrl;
    }

    @Override
    public Map<String, Object> mobilePaymentInit(MobilePaymentInitDTO mobilePaymentInitDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        Long payTimestamp = null;
        try {
            payTimestamp = Long.valueOf(mobilePaymentInitDTO.getPayTimestamp());
        } catch (NumberFormatException e) {
            resultMap.put("flag", false);
            resultMap.put("msg", "时间戳参数非法");
            return resultMap;
        }
        if (!CommonUtil.ifValidTimestamp(payTimestamp, 3 * 60 * 60l)) { //支付有效期为3小时
            resultMap.put("flag", false);
            resultMap.put("msg", "支付已过期,请重新下单!");
            return resultMap;
        }
        if (!CommonUtil.ifValidParam(mobilePaymentInitDTO, SignEncryptConstant.MoblilePaymentValidateSalt)) {
            resultMap.put("flag", false);
            resultMap.put("msg", "签名校验失败!");
            return resultMap;
        }

        String data = redisClient.get(OrderCenterRedisPrefix.ORDERCENTER_MOBILEPAY_URL_PREFIX + mobilePaymentInitDTO.getBusinessUniqueNumber());
        Map<String,Object> payInfo = Jackson.jsonToMap(data);

        //调用inner接口查询財币余额
        AccountQueryBalanceDTO balanceDTO = new AccountQueryBalanceDTO();
        balanceDTO.setUserId((String) payInfo.get("userId"));
        balanceDTO.setAppId(AccountConstant.APPID);
        balanceDTO.setVersion("1.0");
        RestResponse<AccountQueryBalanceResultDTO> balanceDTORestResponse = null;
        try {
            balanceDTORestResponse = accountApi.queryBalance(balanceDTO);
        }catch (Exception e){
            LOGGER.info("查询財币余额调用失败\n"+e.getMessage());
        }
        BigDecimal balance = new BigDecimal(0);
        if(balanceDTORestResponse != null && balanceDTORestResponse.getBody() != null){
            balance = balanceDTORestResponse.getBody().getBalance();
        }

        String goodsSkuId = (String)payInfo.get("skuId");
        String goodsName = getGoodsNameByGoodsId(Long.valueOf((String) payInfo.get("goodsId")));
        //如果商品为充值商品，则不允许使用财币支付
        Boolean coinEnabled = true;
        if(goodsSkuId.equals(ftcspDictHolder.getDictItemValue("fiscal_service_goods", "recharge"))){
            coinEnabled = false;
        }
        // 如果商品不需要财币支付且需要把商品名称用接口传入的描述替代
        if(goodsSkuId.equals(ftcspDictHolder.getDictItemValue("pay_need_goods_describe", goodsSkuId))){
            coinEnabled = false;
        }
        if(!StringUtils.isBlank((String) payInfo.get("goodsDescribe"))){
            goodsName = (String) payInfo.get("goodsDescribe");
        }
        Map<String,Object> resultData = new HashMap();
        resultData.put("balance",balance);
        resultData.put("coinEnabled", coinEnabled);
        resultData.put("goodsName", goodsName);
        resultData.put("payAmount",payInfo.get("payAmount"));
        resultData.put("payNo",payInfo.get("payNo"));
        resultData.put("goodsQuantity", payInfo.get("goodsQuantity"));
        //resultData.put("date",payInfo.get("date"));
        resultData.put("payRetUrl", payInfo.get("payRetUrl"));
        resultData.put("supportPayWays", payInfo.get("supportPayWays"));
        resultData.put("payResPonse", payInfo.get("payResPonse"));

        //返回信息
        resultMap.put("flag", true);
        resultMap.put("data", resultData);
        return resultMap;
    }

    /**
     * 统一移动端支付接入接口入参规则校验并且封装基础创单对象。
     * @param mobilePayEntranceDTO 入参DTO
     * @param OrderDTO 基础创单对象
     */
    public void checkAndAssemble(MobilePayEntranceDTO mobilePayEntranceDTO,OrderDTO orderDTO){
        //校验客户信息
        if(StringUtils.isBlank(mobilePayEntranceDTO.getCusId())){
            if(StringUtils.isBlank(mobilePayEntranceDTO.getCusType())||
               StringUtils.isBlank(mobilePayEntranceDTO.getTaxIdentification())||
               StringUtils.isBlank(mobilePayEntranceDTO.getCusName())){
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：客户信息不完善");
            }else{
                if (!mobilePayEntranceDTO.getCusType().matches("^[01]$")) { //客户类型0,1校验
                    throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：客户类型不存在");
                }
                if (!mobilePayEntranceDTO.getTaxIdentification().matches("\\w{1,50}")) {
                    throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：纳税人识别号不符合标准");
                }
                orderDTO.setCusType(mobilePayEntranceDTO.getCusType());
                orderDTO.setCusIdentificationNumber(mobilePayEntranceDTO.getTaxIdentification());
                orderDTO.setCusName(mobilePayEntranceDTO.getCusName());
            }
        }else{
            orderDTO.setCusId(mobilePayEntranceDTO.getCusId());
        }

        //若参数中存在userId,对userId进行校验
        if (!StringUtils.isBlank(mobilePayEntranceDTO.getUserId())) {
            String userId = mobilePayEntranceDTO.getUserId();
            QueryUserInfoByUserDTO queryUserInfoByUserDTO = new QueryUserInfoByUserDTO();
            queryUserInfoByUserDTO.setUserId(userId);
            RestResponse<SecUserDTO> restResponse = securityInnerApi.queryUserInfoByUserId(queryUserInfoByUserDTO);
            if (restResponse == null || restResponse.getBody() == null) { //说明不存在该userid对应用户
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：userId对应用户不存在");
            }
        }


        //根据goodsSkuId查询商品的价钱
        QueryBriefGoodsForOrder queryBriefGoodsForOrder  = goodsInnerService.queryBriefGoods(null,
                mobilePayEntranceDTO.getGoodsSkuId());
        if(queryBriefGoodsForOrder==null){
            throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：所传的商品SKUID查询不到对应商品");
        }
        if(queryBriefGoodsForOrder.getPrice()==null){
            throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：所传的商品价格为null，请重新确认商品");
        }

        //如果没有传数量，默认数量为1
        if(mobilePayEntranceDTO.getGoodsQuantity()==null){
            mobilePayEntranceDTO.setGoodsQuantity(1);
        }

        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setGoodsQuantiy(mobilePayEntranceDTO.getGoodsQuantity()+"");


        //查询商品的goodsSku
        RestResponse<PdtGoodsSku> response = goodsApi.queryIfExistByGoodsSkuId(mobilePayEntranceDTO.getGoodsSkuId());
        if(response.isSuccess()){
            if(response.getBody()!=null){
                goodsDTO.setGoodsSku(response.getBody().getGoodsSku());
                mobilePayEntranceDTO.setGoodsId(response.getBody().getGoodsId());
            }else{
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：所传的商品对应的sku不存在，请重新确认商品");
            }
        }else{
            throw new FtcspRuntimeException("09030034",(Object)("创建订单失败：请求商品inner接口失败："+response.getHead().getErrorMsg()));
        }



        //校验渠道来源channel和支付应用payAppID的对应关系是否正确
        if(!mobilePayEntranceDTO.getPayAppId().equals( ftcspDictHolder.getDictItemValue("pay_appId_channel", mobilePayEntranceDTO.getChannel()))){
            throw new FtcspRuntimeException("09030034",(Object)("创建订单失败：所传payAppId和channel对应关系错误"));
        }



        if(queryBriefGoodsForOrder.getPrice().compareTo(new BigDecimal(0)) == -1){
            //商品价钱小于0，则为面议商品，面议商品支付金额要求必传
            if(StringUtils.isBlank(mobilePayEntranceDTO.getPayAmount())){
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：所传商品为面议商品，支付金额不能为空");
            }else{
                goodsDTO.setCustomPrice(new BigDecimal(mobilePayEntranceDTO.getPayAmount()));
            }
            BigDecimal payAmount = new BigDecimal(mobilePayEntranceDTO.getPayAmount());
            if (payAmount.compareTo(BigDecimal.ZERO) == 0) {
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付金额不能为零");
            }
        }else if(queryBriefGoodsForOrder.getPrice().compareTo(new BigDecimal(0)) == 0){
            //商品为免费商品，不需要走支付
            throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：所传商品为免费商品，不需要支付");
        }else{
            if(StringUtils.isNotBlank(mobilePayEntranceDTO.getPayAmount())){
                BigDecimal payAmount = new BigDecimal(mobilePayEntranceDTO.getPayAmount());
                if (payAmount.compareTo(BigDecimal.ZERO) == 0) {
                    throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付金额不能为零");
                }
                BigDecimal unitPrice = new BigDecimal(mobilePayEntranceDTO.getPayAmount()).divide(
                        new BigDecimal(mobilePayEntranceDTO.getGoodsQuantity()),2); //不整除时保留两位小数
                if(unitPrice.compareTo(queryBriefGoodsForOrder.getPrice()) != 0){
                    throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：所传支付价钱对应的单价，与商品价钱不符合");
                }
            }else{
                mobilePayEntranceDTO.setPayAmount(queryBriefGoodsForOrder.getPrice().multiply(
                        new BigDecimal(mobilePayEntranceDTO.getGoodsQuantity()))+"");
            }
        }


        //支付方式中是否包含微信支付
        Boolean containWeChat = false;
        Boolean containPublicNumber = false;
        String[] supportPayWays =  mobilePayEntranceDTO.getSupportPayWays();
        //判断支付方式的合法性
        if(supportPayWays!=null &&supportPayWays.length>0){
            for(String payWay:supportPayWays){
                if(!(PayConstant.PAYWAY_ALIPAY_MOBILE+"").equals(payWay)&&
                   !(PayConstant.PAYWAY_WECHAT_MOBILE+"").equals(payWay)&&
                   !(PayConstant.PAYWAY_UNION+"").equals(payWay)&&
                   !(PayConstant.PAYWAY_CAIBI+"").equals(payWay)&&
                   !(PayConstant.PAYWAY_PUBLIC+"").equals(payWay)){
                    throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：请输入支持的支付方式");
                }
                if((PayConstant.PAYWAY_WECHAT_MOBILE+"").equals(payWay)){
                    containWeChat=true;
                }
                if((PayConstant.PAYWAY_PUBLIC+"").equals(payWay)){
                    containPublicNumber=true;
                }
            }
        }
        mobilePayEntranceDTO.setContainPublicNumber(containPublicNumber);
        if(containWeChat&&containPublicNumber){
            throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付方式不可以同时存在微信支付和公众号支付");
        }
        if(containWeChat){
            if(StringUtils.isBlank(mobilePayEntranceDTO.getPayClientIp())){
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付方式存在微信支付时，payClientIp必传");
            }
        }
        if(containPublicNumber){
            if(StringUtils.isBlank(mobilePayEntranceDTO.getPayClientIp())){
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付方式存在公众号支付时，payClientIp必传");
            }
            if(StringUtils.isBlank(mobilePayEntranceDTO.getPayOpenId())){
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付方式存在公众号支付时，payOpenId必传");
            }
            if(StringUtils.isBlank(mobilePayEntranceDTO.getPayFailureUrl())){
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：支付方式存在公众号支付时，payFailureUrl必传");
            }
        }

        List<GoodsDTO> goodsGroup = new ArrayList<>();
        goodsGroup.add(goodsDTO);
        orderDTO.setGoodsGroup(goodsGroup);
        orderDTO.setUserId(mobilePayEntranceDTO.getUserId());
        if (StringUtils.isBlank(orderDTO.getUserId())) {
            orderDTO.setUserId("1"); // 若不存在userId 默认设置为1 平台管理员admin
        }
        orderDTO.setBusinessOrderNo(mobilePayEntranceDTO.getBusinessOrderNo());
        orderDTO.setChannel(mobilePayEntranceDTO.getChannel());
        orderDTO.setDividePay("0");//全额订单
        orderDTO.setNeedInvoice("1");//不需要开票
        //orderDTO.setGoodsQuantiy(goodsDTO.getGoodsQuantiy()); //商品数量
    }

    /**
     * 统一移动端支付接入接口是否创单判断，最后返回下单时间。
     * @param mobilePayEntranceDTO
     * @param orderDTO
     */
    public Date createPayOrder(MobilePayEntranceDTO mobilePayEntranceDTO,OrderDTO orderDTO){
      //订单是否存在
        Date orderTime;
        OrdOrder ordOrder = this.ordOrderExtMapper.selectByBusinessOrderNoAndChannel(mobilePayEntranceDTO.getBusinessOrderNo(),
                Integer.valueOf(mobilePayEntranceDTO.getChannel()));
        if (ordOrder != null) {
            if (OrderConstant.PAY_STATUS_PAID.equals(ordOrder.getPayStatus())) {
                //若该订单为待付款状态则不用创建订单直接跳转支付；
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：订单已支付");
            }else if(OrderConstant.PAY_STATUS_PREPARE_TO_PAY.equals(ordOrder.getPayStatus())){
                mobilePayEntranceDTO.setGoodsQuantity(ordOrder.getGoodsQuantity());
                mobilePayEntranceDTO.setPayAmount(ordOrder.getPayAmount().toString());
                mobilePayEntranceDTO.setGoodsId(ordOrder.getGoodsId());
                mobilePayEntranceDTO.setGoodsSkuId(ordOrder.getSkuId());
                mobilePayEntranceDTO.setBusinessOrderNo(ordOrder.getBusinessOrderNo());
                orderTime = ordOrder.getOrderTime();
                return orderTime;
            }else {
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：业务订单重复");
            }
        } else{
            boolean flag = redisClient.setNx("ordercenter_mobilePayEntrance_wait" + mobilePayEntranceDTO.getBusinessOrderNo() + mobilePayEntranceDTO.getChannel(), "wait", 3l);
            if (!flag) {
                throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：此业务订单号正在创建中，请不要重复提交");
            }
            RepCreateOrderDTO order =  orderService.createBasisOrder(orderDTO);
            orderTime = order.getOrderTime();
            return orderTime;
        }
    }

    /**
     * 统一移动端支付接入接口的公众号支付请求参数。
     * @param mobilePayEntranceDTO
     * @param orderTime
     * @return String
     */
    public String assembleInvokParam(MobilePayEntranceDTO mobilePayEntranceDTO,Date orderTime){
        Map<String, String> param = new HashMap<String, String>();

        String channel = String.valueOf(mobilePayEntranceDTO.getChannel());
        String appId = ftcspDictHolder.getDictItemValue("pay_appId_channel", channel);
        String signKey = ftcspDictHolder.getDictItemValue("pay_signKey_appId", appId);
        String channelId = ftcspDictHolder.getDictItemValue("pay_channelId_payway", PayConstant.PAYWAY_PUBLIC+"");
        String appIdPayWayConcat = appId + "-" + PayConstant.PAYWAY_PUBLIC;
        String accountId = ftcspDictHolder.getDictItemValue("pay_accountId_appId_payway", appIdPayWayConcat);
        String goodsName;
        if(StringUtils.isBlank(mobilePayEntranceDTO.getGoodsDescribe())){
            goodsName = getGoodsNameByGoodsId(mobilePayEntranceDTO.getGoodsId());
        } else{
            goodsName = mobilePayEntranceDTO.getGoodsDescribe();
        }
        param.put("pay_disable_credit","true");
        param.put("pay_sign", "");
        param.put("pay_app_id", appId);
        param.put("pay_channel_id", channelId);
        param.put("pay_account_id", accountId);
        param.put("pay_subject", goodsName);
        param.put("pay_timestamp", String.valueOf(new Date().getTime()));
        param.put("pay_ret_url", mobilePayEntranceDTO.getPayRetUrl());
        param.put("pay_notify_url", mobilePayEntranceDTO.getPayNotifyUrl());
        param.put("pay_biz_order_id", mobilePayEntranceDTO.getBusinessOrderNo());
        param.put("pay_amount", String.valueOf(mobilePayEntranceDTO.getPayAmount()));
        param.put("pay_order_time", String.valueOf(orderTime.getTime()));
        param.put("pay_invalid_time", String.valueOf(new Date().getTime()));
        param.put("pay_body", goodsName);
        param.put("pay_api_version", "1.0");
        param.put("pay_client_ip", mobilePayEntranceDTO.getPayClientIp());
        param.put("pay_open_id", mobilePayEntranceDTO.getPayOpenId());
        String sign = PaySign.genSign(param, signKey);
        param.put("pay_sign", sign);
        String json = gson.toJson(param);
        return json;
    }

    /**
     * 处理统一移动端支付接入接口的公众号支付请求的返回结果。
     * @param mobilePayEntranceDTO
     * @param restResponse
     * @param payResPonse void
     */
    public void dealInvokResult(MobilePayEntranceDTO mobilePayEntranceDTO,RestResponse restResponse,PayResPonseDTO payResPonse){
        if (restResponse.getHead().isSuccess() && restResponse.getBody() != null) {
            @SuppressWarnings("unchecked") Map<String, Object> map = (Map<String, Object>) restResponse.getBody();
            @SuppressWarnings("unchecked") Map<String, Object> wxJsapi = (Map<String, Object>) map.get("wx_jsapi_params");

                payResPonse.setAppId(wxJsapi.get("appId").toString());
                payResPonse.setTimeStamp(wxJsapi.get("timeStamp").toString());
                payResPonse.setNonceStr(wxJsapi.get("nonceStr").toString());
                payResPonse.setPackageName(wxJsapi.get("package").toString());
                payResPonse.setSignType(wxJsapi.get("signType").toString());
                payResPonse.setPaySign(wxJsapi.get("paySign").toString());
                payResPonse.setPayFailureUrl(mobilePayEntranceDTO.getPayFailureUrl());
        } else {
            throw new FtcspRuntimeException("09030034",(Object)"创建订单失败：请求公众号支付接口失败");
        }
    }

    @Override
    public Map<String, Object> mobilePayment(MobilePaymentDTO mobilePaymentDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        Long payTimestamp = null;
        try {
            payTimestamp = Long.valueOf(mobilePaymentDTO.getPayTimestamp());
        } catch (NumberFormatException e) {
            resultMap.put("flag", false);
            resultMap.put("msg", "时间戳格式非法");
            return resultMap;
        }
        if (!CommonUtil.ifValidTimestamp(payTimestamp, 3 * 60 * 60l)) { //支付有效期为3小时
            resultMap.put("flag", false);
            resultMap.put("msg", "支付已过期,请重新下单!");
            return resultMap;
        }
        if (!CommonUtil.ifValidParam(mobilePaymentDTO, SignEncryptConstant.MoblilePaymentValidateSalt,new String[]{"payWay"})) {
            resultMap.put("flag", false);
            resultMap.put("msg", "签名校验失败!");
            return resultMap;
        }
        //从redis中获取支付信息
        Map<String ,Object> payInfo = Jackson.jsonToMap(redisClient.get(OrderCenterRedisPrefix.ORDERCENTER_MOBILEPAY_URL_PREFIX + mobilePaymentDTO.getBusinessUniqueNumber()));

        if (String.valueOf(PayConstant.PAYWAY_CAIBI).equals(mobilePaymentDTO.getPayWay())) { //不支持财币支付的商品校验
            String goodsSkuId = (String) payInfo.get("skuId");
            if(goodsSkuId.equals(ftcspDictHolder.getDictItemValue("fiscal_service_goods", "recharge"))){
                resultMap.put("flag", false);
                resultMap.put("msg", "该商品不支持财币支付!");
                return resultMap;
            }
        }

        OrdPayFlowingDTO ordPayFlowingDTO = new OrdPayFlowingDTO();
        ordPayFlowingDTO.setPayNo((String)payInfo.get("payNo"));
        ordPayFlowingDTO.setAppId((String)payInfo.get("payAppId"));
        OrdOrderDTO ordOrderDTO = orderService.queryOrderPayInfoByPayNo(ordPayFlowingDTO);
        if(ordOrderDTO != null){
            if(OrderConstant.PAY_STATUS_PAID == ordOrderDTO.getPayStatus()){
                resultMap.put("flag", false);
                resultMap.put("msg", "业务订单已付款，无需重复付款");
                return resultMap;
            }
            ordOrderDTO.setPaySuccessRetUrl((String)payInfo.get("payRetUrl"));
            ordOrderDTO.setPayNotifyUrl((String)payInfo.get("payNotifyUrl"));
            ordOrderDTO.setPayClientIp((String) payInfo.get("payClientIp"));
            ordOrderDTO.setPayWay(mobilePaymentDTO.getPayWay());
            String goodsName = getGoodsNameByGoodsId(ordOrderDTO.getGoodsId());
            ordOrderDTO.setGoodsName(goodsName);
            String requestParam = dealInvokRewardPayParma(ordOrderDTO);
            RestResponse restResponse = invokPay(requestParam);
            OrdPayFlowing payFlowing = new OrdPayFlowing();
            payFlowing.setPayId(ordOrderDTO.getPayId());
            int payWay = Integer.parseInt(ordOrderDTO.getPayWay());
            payFlowing.setPayWay(payWay);
            payFlowingMapper.updateByPrimaryKeySelective(payFlowing);

            resultMap.put("flag", true);
            resultMap.put("data", restResponse);
            return resultMap;
        } else {
            resultMap.put("flag", false);
            resultMap.put("msg", String.format("查询不到支付流水号为【%s】的订单", (String)payInfo.get("payNo")));
            return resultMap;
        }
    }
}
