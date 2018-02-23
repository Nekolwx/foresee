/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author mowentao@foresee.com.cn
 * @date 2017年8月21日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class QueryBillPageDTO {
	/**
     * 账单Id
     * 表 : t_ord_bill
     * 对应字段 : bill_id
     */
    private Long billId;
    /**
     * 订单Id
     * 表 : t_ord_bill
     * 对应字段 : order_id
     */
    private Long orderId;
	/**
     * 账单编号
     * 表 : t_ord_bill
     * 对应字段 : bill_code
     */
    private String billCode;
    /**
     * 订单编号
     * 表 : t_ord_order
     * 对应字段 : order_code
     */
    private String orderCode;
    /**
     * 商品名称
     * 表 : t_pdt_goods
     * 对应字段 : goods_name
     */
    private String goodsName;
    /**
     * 客户名称
     * 表 : t_crm_customer
     * 对应字段 : cusName
     */
    private String cusName;
    /**
     * 纳税人识别号
     * 表 : t_crm_customer
     * 对应字段 : tax_identification_number
     */
    private String taxIdentificationNumber;
    /**
     * 支付方式
     * 表 : t_ord_pay_flowing
     * 对应字段 : pay_way
     */
    private int payWay;
    /**
     * 账单金额
     * 表 : t_ord_bill
     * 对应字段 : bill_amount
     */
    private String billAmount;
    /**
     * 开票状态
     * 表 : t_ord_bill
     * 对应字段 : invoice_status
     */
    private int invoiceStatus;
    /**
     * 账单开始时间起
     * 表 : 
     * 对应字段 : 
     */
    private String billStartTimeStart;
    /**
     * 账单开始时间止
     * 表 : 
     * 对应字段 : 
     */
    private String billStartTimeEnd;
    /**
     * 账单结束时间起
     * 表 : 
     * 对应字段 : 
     */
    private String billEndTimeStart;
    /**
     * 账单结束时间止
     * 表 : 
     * 对应字段 : 
     */
    private String billEndTimeEnd;
    
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public int getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getBillStartTimeStart() {
		return billStartTimeStart;
	}
	public void setBillStartTimeStart(String billStartTimeStart) {
		this.billStartTimeStart = billStartTimeStart;
	}
	public String getBillStartTimeEnd() {
		return billStartTimeEnd;
	}
	public void setBillStartTimeEnd(String billStartTimeEnd) {
		this.billStartTimeEnd = billStartTimeEnd;
	}
	public String getBillEndTimeStart() {
		return billEndTimeStart;
	}
	public void setBillEndTimeStart(String billEndTimeStart) {
		this.billEndTimeStart = billEndTimeStart;
	}
	public String getBillEndTimeEnd() {
		return billEndTimeEnd;
	}
	public void setBillEndTimeEnd(String billEndTimeEnd) {
		this.billEndTimeEnd = billEndTimeEnd;
	}
	@Override
	public String toString() {
		return "QueryBillPageDTO [billId=" + billId + ", orderId=" + orderId + ", billCode=" + billCode + ", orderCode="
				+ orderCode + ", goodsName=" + goodsName + ", cusName=" + cusName + ", taxIdentificationNumber="
				+ taxIdentificationNumber + ", payWay=" + payWay + ", billAmount=" + billAmount + ", invoiceStatus="
				+ invoiceStatus + ", billStartTimeStart=" + billStartTimeStart + ", billStartTimeEnd="
				+ billStartTimeEnd + ", billEndTimeStart=" + billEndTimeStart + ", billEndTimeEnd=" + billEndTimeEnd
				+ "]";
	}
	
    
}
