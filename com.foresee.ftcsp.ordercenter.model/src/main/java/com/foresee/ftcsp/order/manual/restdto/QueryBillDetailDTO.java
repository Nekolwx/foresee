/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author mowentao@foresee.com.cn
 * @date 2017年8月22日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class QueryBillDetailDTO {
	/**
	 * 客户名称 表 : t_crm_customer 对应字段 : cusName
	 */
	private String cusName;

	/**
	 * 纳税人识别号 表 : t_crm_customer 对应字段 : tax_identification_number
	 */
	private String taxIdentificationNumber;

	/**
	 * 社会信用代码 表 : t_crm_customer 对应字段 : credit_code
	 */
	private String creditCode;

	/**
	 * 订单编号 表 : t_ord_order 对应字段 : order_user
	 */
	private String orderUser;
	
	/**
	 * 订单ID 表 : t_ord_bill 对应字段 : order_id
	 */
	private String orderId;

	/**
	 * 订单编号 表 : t_ord_order 对应字段 : order_code
	 */
	private String orderCode;

	/**
	 * 账单编号 表 : t_ord_bill 对应字段 : bill_code
	 */
	private String billCode;

	/**
	 * 账单开始时间 表 : t_ord_bill 对应字段 : bill_start_time
	 */
	private String billStartTime;

	/**
	 * 账单结束时间 表 : t_ord_bill 对应字段 : bill_end_time
	 */
	private String billEndTime;

	/**
	 * 商品Sku 表 : t_pdt_goods_sku 对应字段 : goods_sku
	 */
	private String goodsSku;
	
	/**
	 * 商品 表 : t_pdt_goods 对应字段 : goods_name
	 */
	private String goodsName;

	/**
	 * 商品类别 表 : t_pdt_goods_sku 对应字段 : goods_type
	 */
	private int goodsType;

	/**
	 * 付款状态 表 : t_ord_bill 对应字段 : pay_status
	 */
	private int payStatus;

	/**
	 * 付款时间 表 : t_ord_bill 对应字段 : pay_time
	 */
	private String payTime;

	/**
	 * 支付方式 表 : t_ord_pay_flowing 对应字段 : pay_way
	 */
	private int payWay;

	/**
	 * 付款渠道 表 : 对应字段 :
	 */
	private int payChannel;

	/**
	 * 商品原价 表 : t_ord_order 对应字段 : goods_original_price
	 */
	private float goodsOriginalPrice;

	/**
	 * 折扣价 表 : 对应字段 :
	 */
	private float discountAmount;

	/**
	 * 初装费 表 : t_pdt_goods_sku 对应字段 : initial_installation_fee
	 */
	private float initialInstallationFee;

	/**
	 * 手续费 表 : 对应字段 :
	 */
	private float servicePrice;

	/**
	 * 商品总数量 表 : t_ord_order 对应字段 : goods_quantity
	 */
	private int goodsQuantity;

	/**
	 * 商品总价 表 : 对应字段 :
	 */
	private float goodsAmount;

	/**
	 * 应付金额 表 : t_ord_bill 对应字段 : bill_amount
	 */
	private float billAmount;

	/**
	 * 实付金额 表 : t_ord_pay_flowing 对应字段 : pay_amount
	 */
	private float payAmount;

	/**
	 * 到期时间起 表 : t_ord_bill 对应字段 : bill_start_time
	 */
	private String payStartTime;

	/**
	 * 到期时间止 表 : t_ord_bill 对应字段 : limit_time
	 */
	private String payEndTime;

	/**
	 * 付款人名称 表 : t_ord_billing_info 对应字段 : buyer_name
	 */
	private String payerName;

	/**
	 * 付款人账号 表 : t_ord_billing_info 对应字段 : buyer_bank_card
	 */
	private String payerAccount;

	/**
	 * 发票状态 表 : t_ord_bill 对应字段 : invoice_status
	 */
	private int invoiceStatus;

	/**
	 * 支付流水号 表 : t_ord_pay_flowing 对应字段 : pay_no
	 */
	private String payNo;

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

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillStartTime() {
		 
		return billStartTime;
	}

	public void setBillStartTime(String billStartTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		try {
			data = formatter.parse(billStartTime);
			billStartTime = formatter.format(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.billStartTime = billStartTime;
	}

	public String getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(String billEndTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		try {
			data = formatter.parse(billEndTime);
			billEndTime = formatter.format(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.billEndTime = billEndTime;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date data;
		try {
			data = formatter.parse(payTime);
			payTime = formatter.format(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.payTime = payTime;
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}

	public int getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(int payChannel) {
		this.payChannel = payChannel;
	}

	public float getGoodsOriginalPrice() {
		return goodsOriginalPrice;
	}

	public void setGoodsOriginalPrice(float goodsOriginalPrice) {
		this.goodsOriginalPrice = goodsOriginalPrice;
	}

	public float getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}

	public float getInitialInstallationFee() {
		return initialInstallationFee;
	}

	public void setInitialInstallationFee(float initialInstallationFee) {
		this.initialInstallationFee = initialInstallationFee;
	}

	public float getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(float servicePrice) {
		this.servicePrice = servicePrice;
	}

	public int getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public float getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(float goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public float getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	public float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(float payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(String payStartTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		try {
			data = formatter.parse(payStartTime);
			payStartTime = formatter.format(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.payStartTime = payStartTime;
	}

	public String getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(String payEndTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		try {
			data = formatter.parse(payEndTime);
			payEndTime = formatter.format(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.payEndTime = payEndTime;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	@Override
	public String toString() {
		return "QueryBillDetailDTO [cusName=" + cusName + ", taxIdentificationNumber=" + taxIdentificationNumber
				+ ", creditCode=" + creditCode + ", orderUser=" + orderUser + ", orderId=" + orderId + ", orderCode="
				+ orderCode + ", billCode=" + billCode + ", billStartTime=" + billStartTime + ", billEndTime="
				+ billEndTime + ", goodsSku=" + goodsSku + ", goodsName=" + goodsName + ", goodsType=" + goodsType
				+ ", payStatus=" + payStatus + ", payTime=" + payTime + ", payWay=" + payWay + ", payChannel="
				+ payChannel + ", goodsOriginalPrice=" + goodsOriginalPrice + ", discountAmount=" + discountAmount
				+ ", initialInstallationFee=" + initialInstallationFee + ", servicePrice=" + servicePrice
				+ ", goodsQuantity=" + goodsQuantity + ", goodsAmount=" + goodsAmount + ", billAmount=" + billAmount
				+ ", payAmount=" + payAmount + ", payStartTime=" + payStartTime + ", payEndTime=" + payEndTime
				+ ", payerName=" + payerName + ", payerAccount=" + payerAccount + ", invoiceStatus=" + invoiceStatus
				+ ", payNo=" + payNo + "]";
	}


}
