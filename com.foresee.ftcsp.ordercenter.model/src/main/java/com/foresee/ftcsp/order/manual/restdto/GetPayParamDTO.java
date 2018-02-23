/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * 获取支付参数接口请求参数DTO。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月18日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class GetPayParamDTO {
    @JsonProperty("cus_name")
    @NotBlank(message = "参数[cus_name]不能为空")
    private String cusName;
    
    @JsonProperty("cus_type")
    @NotBlank(message = "参数[cus_type]不能为空")
    private String cusType;
    
    @JsonProperty("tax_identification")
    @NotBlank(message = "参数[tax_identification]不能为空")
    private String taxIdentification;
    
    @JsonProperty("mobile_phone")
    @NotBlank(message = "参数[mobile_phone]不能为空")
    private String mobilePhone;
    
    @JsonProperty("cus_address")
    private String cusAddress;
    
    @NotBlank(message = "参数[source]不能为空")
    private String source;
    
    @JsonProperty("pay_failure_url")
    private String payFailureUrl;
    
    @JsonProperty("pay_project")
    @NotBlank(message = "参数[pay_project]不能为空")
    private String payProject;
    
    @JsonProperty("is_Enabled_order")
    @NotBlank(message = "参数[is_Enabled_order]不能为空")
    private String isEnabledOrder;
    
    @JsonProperty("pay_sign")
    @NotBlank(message = "参数[pay_sign]不能为空")
    private String paySign;
    
    @JsonProperty("pay_timestamp")
    @NotBlank(message = "参数[pay_timestamp]不能为空")
    private String payTimestamp;
    
    @JsonProperty("pay_app_id")
    @NotBlank(message = "参数[pay_app_id]不能为空")
    private String payAppId;
    
    @JsonProperty("pay_channel_id")
    @NotBlank(message = "参数[pay_channel_id]不能为空")
    private String payChannelId;
    
    @JsonProperty("pay_account_id")
    @NotBlank(message = "参数[pay_account_id]不能为空")
    private String payAccountId;
    
    @JsonProperty("pay_ret_url")
    @NotBlank(message = "参数[pay_ret_url]不能为空")
    private String payRetUrl;
    
    @JsonProperty("pay_notify_url")
    @NotBlank(message = "参数[pay_notify_url]不能为空")
    private String payNotifyUrl;
    
    @JsonProperty("pay_biz_order_id")
    @NotBlank(message = "参数[pay_biz_order_id]不能为空")
    private String payBizOrderId;
    
    @JsonProperty("pay_amount")
    @NotBlank(message = "参数[pay_amount]不能为空")
    private String payAmount;
    
    @JsonProperty("pay_order_time")
    @NotBlank(message = "参数[pay_order_time]不能为空")
    private String payOrderTime;
    
    @JsonProperty("pay_invalid_time")
    @NotBlank(message = "参数[pay_invalid_time]不能为空")
    private String payInvalidTime;
    
    @JsonProperty("pay_subject")
    @NotBlank(message = "参数[pay_subject]不能为空")
    private String paySubject;

    @JsonProperty("pay_body")
    @NotBlank(message = "参数[pay_body]不能为空")
    private String payBody;
    
    @JsonProperty("pay_show_url")
    private String payShowUrl;
    
    @JsonProperty("pay_api_version")
    @NotBlank(message = "参数[pay_api_version]不能为空")
    private String payApiVersion;
    
    @JsonProperty("pay_open_id")
    //@NotBlank(message = "参数[pay_open_id]不能为空")
    private String payOpenId;
    
    @JsonProperty("pay_client_ip")
    //@NotBlank(message = "参数[pay_client_ip]不能为空")
    private String payClientIp;
    
    @JsonProperty("goods_sku_id")
    @Range(min=10000000000000000L,
    max=Long.MAX_VALUE,
    message="goods_sku_id不合法")
    @NotNull(message = "参数[goods_sku_id]不能为空")
    private Long skuId;
    
    /**
     * goodsId:根据所传的skuId所对应的goodsId。
     */
    private Long goodsId;
    
    @JsonProperty("order_user")
    private String orderUser;
    
    

    
    
    public Long getGoodsId() {
        return goodsId;
    }


    
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }


    public String getCusName() {
        return cusName;
    }

    
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    
    public String getCusType() {
        return cusType;
    }

    
    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    
    public String getTaxIdentification() {
        return taxIdentification;
    }

    
    public void setTaxIdentification(String taxIdentification) {
        this.taxIdentification = taxIdentification;
    }

    
    public String getMobilePhone() {
        return mobilePhone;
    }

    
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    
    public String getCusAddress() {
        return cusAddress;
    }

    
    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    
    public String getSource() {
        return source;
    }

    
    public void setSource(String source) {
        this.source = source;
    }

    
    public String getPayFailureUrl() {
        return payFailureUrl;
    }

    
    public void setPayFailureUrl(String payFailureUrl) {
        this.payFailureUrl = payFailureUrl;
    }

    
    public String getPayProject() {
        return payProject;
    }

    
    public void setPayProject(String payProject) {
        this.payProject = payProject;
    }

    
    public String getIsEnabledOrder() {
        return isEnabledOrder;
    }

    
    public void setIsEnabledOrder(String isEnabledOrder) {
        this.isEnabledOrder = isEnabledOrder;
    }

    
    public String getPaySign() {
        return paySign;
    }

    
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    
    public String getPayTimestamp() {
        return payTimestamp;
    }

    
    public void setPayTimestamp(String payTimestamp) {
        this.payTimestamp = payTimestamp;
    }

    
    public String getPayAppId() {
        return payAppId;
    }

    
    public void setPayAppId(String payAppId) {
        this.payAppId = payAppId;
    }

    
    public String getPayChannelId() {
        return payChannelId;
    }

    
    public void setPayChannelId(String payChannelId) {
        this.payChannelId = payChannelId;
    }

    
    public String getPayAccountId() {
        return payAccountId;
    }

    
    public void setPayAccountId(String payAccountId) {
        this.payAccountId = payAccountId;
    }

    
    public String getPayRetUrl() {
        return payRetUrl;
    }

    
    public void setPayRetUrl(String payRetUrl) {
        this.payRetUrl = payRetUrl;
    }

    
    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    
    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    
    public String getPayBizOrderId() {
        return payBizOrderId;
    }

    
    public void setPayBizOrderId(String payBizOrderId) {
        this.payBizOrderId = payBizOrderId;
    }

    
    public String getPayAmount() {
        return payAmount;
    }

    
    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    
    public String getPayOrderTime() {
        return payOrderTime;
    }

    
    public void setPayOrderTime(String payOrderTime) {
        this.payOrderTime = payOrderTime;
    }

    
    public String getPayInvalidTime() {
        return payInvalidTime;
    }

    
    public void setPayInvalidTime(String payInvalidTime) {
        this.payInvalidTime = payInvalidTime;
    }

    
    public String getPaySubject() {
        return paySubject;
    }

    
    public void setPaySubject(String paySubject) {
        this.paySubject = paySubject;
    }

    
    public String getPayBody() {
        return payBody;
    }

    
    public void setPayBody(String payBody) {
        this.payBody = payBody;
    }

    
    public String getPayShowUrl() {
        return payShowUrl;
    }

    
    public void setPayShowUrl(String payShowUrl) {
        this.payShowUrl = payShowUrl;
    }

    
    public String getPayApiVersion() {
        return payApiVersion;
    }

    
    public void setPayApiVersion(String payApiVersion) {
        this.payApiVersion = payApiVersion;
    }

    
    public String getPayOpenId() {
        return payOpenId;
    }

    
    public void setPayOpenId(String payOpenId) {
        this.payOpenId = payOpenId;
    }

    
    public String getPayClientIp() {
        return payClientIp;
    }

    
    public void setPayClientIp(String payClientIp) {
        this.payClientIp = payClientIp;
    }

    
    public Long getSkuId() {
        return skuId;
    }

    
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }



    
    public String getOrderUser() {
        return orderUser;
    }



    
    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }
    
    
    

}
