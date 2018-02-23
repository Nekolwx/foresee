/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import com.foresee.ftcsp.order.common.CommonDict;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @date 2017年8月24日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class AddOrdOrdeReceivablesrDTO {
    
    /**
     * 主键,订单id
     * 表 : t_ord_order
     * 对应字段 : order_id
     */
    @NotNull(message="订单id不能为空")
    @Range(min=CommonDict.ID_MIN_VALUE,
            max=CommonDict.ID_MAX_VALUE,
            message="ID不合法")
    private Long orderId;
    
    /**
     * 支付方式（0-支付宝，1-微信支付，2-银联支付，3-现金）
     * 表 : t_ord_pay_flowing
     * 对应字段 : pay_way
     */
    @NotNull(message="支付方式不能为空")
    private Integer payWay;

    /**
     * 第三方支付流水号
     * 表 : t_ord_pay_flowing
     * 对应字段 : three_party_no
     */
    private String threePartyNo;

    /**
     * 支付时间
     * 表 : t_ord_pay_flowing
     * 对应字段 : pay_time
     */
    @NotNull(message="支付时间不能为空")
    private Date payTime;

    /**
     * 支付金额
     * 表 : t_ord_pay_flowing
     * 对应字段 : pay_amount
     */
    @NotNull(message="支付金额不能为空")
    private BigDecimal payAmount;

    /**
     * 付款方
     * 表 : t_ord_pay_flowing
     * 对应字段 : payer
     */
    @NotEmpty(message="付款人不能为空")
    private String payer;

    /**
     * 付款方账号
     * 表 : t_ord_pay_flowing
     * 对应字段 : payer_account
     */
    private String payerAccount;

    /**
     * 付款方联系方式
     * 表 : t_ord_pay_flowing
     * 对应字段 : payer_phone
     */
    @NotEmpty(message="付款人联系方式不能为空")
    private String payerPhone;

    /**
     * 收款方
     * 表 : t_ord_pay_flowing
     * 对应字段 : receipt_side
     */
    @NotEmpty(message="收款人不能为空")
    private String receiptSide;

    /**
     * 收款账号
     * 表 : t_ord_pay_flowing
     * 对应字段 : receipt_account
     */
    private String receiptAccount;
    
    public Long getOrderId() {
        return orderId;
    }

    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    
    public Integer getPayWay() {
        return payWay;
    }

    
    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    
    public Date getPayTime() {
        return payTime;
    }

    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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

    
    public String getPayerPhone() {
        return payerPhone;
    }

    
    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
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
    
}
