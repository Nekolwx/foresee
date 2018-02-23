package com.foresee.ftcsp.order.manual.dto;

import java.io.Serializable;

public class OrdInvoiceListDTO implements Serializable {
  
    /**
     * serialVersionUID:TODO。
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键,发票id
     * 表 : t_ord_invoice
     * 对应字段 : invoice_id
     */
    private Long invoiceId;
    
    /**
     * 主键,账单id
     * 表 : t_ord_bill
     * 对应字段 : bill_id
     */
    private Long billId;

    /**
     * 账单编号
     * 表 : t_ord_bill
     * 对应字段 : bill_code
     */
    private String billCode;
    
    //商品名称
    private String goodsName;
    
    //客户名称
    private String cusName;
    
    //纳税人识别号
    private String taxIdentificationNumber;
    
    //期数
    private String periods;
    
    //发票代码
    private String invoiceCode;
    
    /**
     * 合计金额；单位：分，（开票金额？？）
     * 表 : t_ord_invoice
     * 对应字段 : total_amount
     */
    private Long totalAmount;
    
    //开票日期
    private String billingTime;
    
    /**
     * 发票形式（0=电子发票、1=纸质发票）(开票形式)
     * 表 : t_ord_invoice
     * 对应字段 : invoice_formality
     */
    private Integer invoiceFormality;

    /**
     * 开票类型（0=蓝字发票，1=红字发票）
     * 表 : t_ord_invoice
     * 对应字段 : billing_type
     */
    private Integer billingType;

    //是否红冲
    private String isRed;
    
    //支付方式
    private String payWay;
    
    //结算时间
    private String settlementTime;

    
    public Long getInvoiceId() {
        return invoiceId;
    }

    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    
    
    public Long getBillId() {
        return billId;
    }


    
    public void setBillId(Long billId) {
        this.billId = billId;
    }


    public String getBillCode() {
        return billCode;
    }

    
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    
    public String getGoodsName() {
        return goodsName;
    }

    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    
    public String getCusName() {
        return cusName;
    }

    
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }


    
    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }


    public String getPeriods() {
        return periods;
    }

    
    public void setPeriods(String periods) {
        this.periods = periods;
    }

    
    public String getInvoiceCode() {
        return invoiceCode;
    }

    
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    
    public Long getTotalAmount() {
        return totalAmount;
    }

    
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    
    
    public String getBillingTime() {
        return billingTime;
    }


    
    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }


    public Integer getInvoiceFormality() {
        return invoiceFormality;
    }

    
    public void setInvoiceFormality(Integer invoiceFormality) {
        this.invoiceFormality = invoiceFormality;
    }

    
    public Integer getBillingType() {
        return billingType;
    }

    
    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    
    public String getIsRed() {
        return isRed;
    }

    
    public void setIsRed(String isRed) {
        this.isRed = isRed;
    }

    
    public String getPayWay() {
        return payWay;
    }

    
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    
    public String getSettlementTime() {
        return settlementTime;
    }

    
    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }
   
}