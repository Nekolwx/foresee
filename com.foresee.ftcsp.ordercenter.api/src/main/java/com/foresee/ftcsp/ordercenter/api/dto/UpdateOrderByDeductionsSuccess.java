package com.foresee.ftcsp.ordercenter.api.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * <pre>
 * 更新订单入参实体类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *             修改后版本:     修改人：  修改日期:     修改内容:
 *                   </pre>
 * @date 2018年01月31日
 */
public class UpdateOrderByDeductionsSuccess {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 业务订单号
     */
    @Pattern(regexp = "^.{1,64}$", message = "参数[businessOrderNo]超长,请控制在64位以内")
    private String businessOrderNo;

    /**
     * 来源
     */
    //@NotNull(message="参数[channel]不能为空")
    private Integer channel;

    /**
     * 价钱
     */
    @NotNull(message = "参数[currentPayAmount]不能为空")
    //@Pattern(regexp = "^\\d+(\\.\\d{1,2})?",message = "参数[currentPayAmount]异常，请输入合法金额")
    private BigDecimal currentPayAmount;

    /**
     * 更新人
     */
    @NotBlank(message = "参数[updateUser]不能为空")
    private String updateUser;

    /**
     * 签名
     */
    @NotBlank(message = "参数[sign]不能为空")
    private String sign;
    /**
     * 支付流水ID
     */
    private Long payId;

    /**
     * 支付时间
     */
    @NotBlank(message = "参数[payTime]不能为空")
    private String payTime;

    /**
     * 支付方式
     */
    private Integer payWay;

    /**
     * 付款方
     */
    private String payer;

    /**
     * 付款账号
     */
    private String payerAccount;

    /**
     * 收款方
     */
    private String receiptSide;

    /**
     * 收款方账号
     */
    private String receiptAccount;

    /**
     * 第三方交易流水号
     */
    private String threePartyNo;

    /**
     * 支付AppId
     */
    private String payAppId;

    /**
     * 订单支付价钱（接口入参不传）
     */
    private BigDecimal payAmount;

    /**
     * 支付状态
     */
    @NotNull(message = "参数[payStatus]不能为空")
    private Integer payStatus;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public BigDecimal getCurrentPayAmount() {
        return currentPayAmount;
    }

    public void setCurrentPayAmount(BigDecimal currentPayAmount) {
        this.currentPayAmount = currentPayAmount;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getReceiptSide() {
        return receiptSide;
    }

    public void setReceiptSide(String receiptSide) {
        this.receiptSide = receiptSide;
    }

    public String getReceiptAccount() {
        return receiptAccount;
    }

    public void setReceiptAccount(String receiptAccount) {
        this.receiptAccount = receiptAccount;
    }

    public String getThreePartyNo() {
        return threePartyNo;
    }

    public void setThreePartyNo(String threePartyNo) {
        this.threePartyNo = threePartyNo;
    }

    public String getPayAppId() {
        return payAppId;
    }

    public void setPayAppId(String payAppId) {
        this.payAppId = payAppId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
