/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * 接收支付回调接口入参DTO。
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

public class ReceivePayCallBackDTO {

    @JsonProperty("biz_order_id")
    @NotBlank(message = "参数[biz_order_id]不能为空")
    private String bizOrderId;
    
    @JsonProperty("pay_trade_code")
    @NotBlank(message = "参数[pay_trade_code]不能为空")
    private String payTradeCode;
    
    @JsonProperty("pay_status")
    @NotBlank(message = "参数[pay_status]不能为空")
    private String payStatus;
    
    @JsonProperty("pay_msg")
    @NotBlank(message = "参数[pay_msg]不能为空")
    private String payMsg;
    
    @JsonProperty("pay_time")
    @NotBlank(message = "参数[pay_time]不能为空")
    private String payTime;
    
    @JsonProperty("pay_success_time")
    private String paySuccessTime;
    
    @JsonProperty("pay_amount")
    @NotNull(message = "参数[pay_amount]不能为空")
    private BigDecimal payAmount;
    
    @JsonProperty("pay_subject")
    private String paySubject;
    
    @JsonProperty("pay_body")
    private String payBody;
    
    @JsonProperty("pay_channel")
    @NotBlank(message = "参数[pay_channel]不能为空")
    private String payChannel;
    
    @JsonProperty("pay_sign")
    private String paySign;
    
    @JsonProperty("pay_app_id")
    private String appId;
    
    public String getBizOrderId() {
        return bizOrderId;
    }

    
    public void setBizOrderId(String bizOrderId) {
        this.bizOrderId = bizOrderId;
    }


    
    public String getPayTradeCode() {
        return payTradeCode;
    }


    
    public void setPayTradeCode(String payTradeCode) {
        this.payTradeCode = payTradeCode;
    }


    
    public String getPayStatus() {
        return payStatus;
    }


    
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }


    
    public String getPayMsg() {
        return payMsg;
    }


    
    public void setPayMsg(String payMsg) {
        this.payMsg = payMsg;
    }


    
    public String getPayTime() {
        return payTime;
    }


    
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }


    
    public String getPaySuccessTime() {
        return paySuccessTime;
    }


    
    public void setPaySuccessTime(String paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }
 
    public BigDecimal getPayAmount() {
        return payAmount;
    }


    
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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


    
    public String getPayChannel() {
        return payChannel;
    }


    
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }


    
    public String getPaySign() {
        return paySign;
    }


    
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }


    
    public String getAppId() {
        return appId;
    }


    
    public void setAppId(String appId) {
        this.appId = appId;
    }

    
    
}
