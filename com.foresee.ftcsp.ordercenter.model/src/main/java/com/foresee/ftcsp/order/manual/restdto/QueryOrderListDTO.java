/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.util.Date;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @date 2017年8月21日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class QueryOrderListDTO {
    
    /**
     * 主键,订单id
     * 表 : t_ord_order
     * 对应字段 : order_id
     */
    private Long orderId;

    /**
     * 订单编号
     * 表 : t_ord_order
     * 对应字段 : order_code
     */
    private String orderCode;

    /**
     * 订单类型（0-新增 1-续期）
     * 表 : t_ord_order
     * 对应字段 : order_type
     */
    private Integer orderType;
    
    //客户名称
    private String customerName;

    //纳税人识别号
    private String taxIdentificationNumber;
    
    //商品skuId
    private String skuId;
    
    //订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）
    private Integer orderStatus;
    
    //付款状态（0不需要付款 1待付款 2付款中 3部分付款 4 已付款 ）
    private Integer payStatus;
    
    //服务状态（0未启动 1待服务 2服务中 3已完成 4 已终止 5 已取消）
    private Integer serviceStatus;
    
    //付款方式（支付方式（0-支付宝，1-微信支付，2-银联支付，3-现金））
    private Integer payWay;
    
    //服务开始时间起
    private Date serviceStartDateStart;
    
    //服务开始时间止
    private Date serviceStartDateEnd;
    
    //服务结束时间起
    private Date serviceEndDateStart;
    
    //服务结束时间止
    private Date serviceEndDateEnd;
    
    //售卖渠道（0 平台 1 网站 2 金财代账）
    private Integer channel;
    
    //下单时间起
    private Date orderTimeStart;
    
    //下单时间止
    private Date orderTimeEnd;
    
    //分期:0-全额，1-分期
    private Integer dividePay;
    
    //开票状态0 未开票 1 已开票 2 不需要开票
    private Integer billingStatus;
    
    //区域编码
    private String areaCode;
    
    //是否需要配送0 不需要 1 需要-快递 2 需要-上门自提
    private Integer deliveryMode;
    
    //用于判断是否为省，不为空则是
    private String isPre;
    
    //用于判断是否为市，不为空则是
    private String isCity;
    
    //用于判断是否为区，不为空则是
    private String isXian;
    
    
    public String getCustomerName() {
        return customerName;
    }


    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public Long getOrderId() {
        return orderId;
    }

    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    
    public String getOrderCode() {
        return orderCode;
    }

    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    
    public Integer getOrderType() {
        return orderType;
    }

    
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }



    
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }



    
    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }



    
    public String getSkuId() {
        return skuId;
    }



    
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }



    
    public Integer getOrderStatus() {
        return orderStatus;
    }



    
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }



    
    public Integer getPayStatus() {
        return payStatus;
    }



    
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }



    
    public Integer getServiceStatus() {
        return serviceStatus;
    }



    
    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }



    
    public Integer getPayWay() {
        return payWay;
    }



    
    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }



    
    public Date getServiceStartDateStart() {
        return serviceStartDateStart;
    }



    
    public void setServiceStartDateStart(Date serviceStartDateStart) {
        this.serviceStartDateStart = serviceStartDateStart;
    }



    
    public Date getServiceStartDateEnd() {
        return serviceStartDateEnd;
    }



    
    public void setServiceStartDateEnd(Date serviceStartDateEnd) {
        this.serviceStartDateEnd = serviceStartDateEnd;
    }



    
    public Date getServiceEndDateStart() {
        return serviceEndDateStart;
    }



    
    public void setServiceEndDateStart(Date serviceEndDateStart) {
        this.serviceEndDateStart = serviceEndDateStart;
    }



    
    public Date getServiceEndDateEnd() {
        return serviceEndDateEnd;
    }



    
    public void setServiceEndDateEnd(Date serviceEndDateEnd) {
        this.serviceEndDateEnd = serviceEndDateEnd;
    }



    
    public Integer getChannel() {
        return channel;
    }



    
    public void setChannel(Integer channel) {
        this.channel = channel;
    }



    
    public Date getOrderTimeStart() {
        return orderTimeStart;
    }



    
    public void setOrderTimeStart(Date orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }



    
    public Date getOrderTimeEnd() {
        return orderTimeEnd;
    }



    
    public void setOrderTimeEnd(Date orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }



    
    public Integer getDividePay() {
        return dividePay;
    }



    
    public void setDividePay(Integer dividePay) {
        this.dividePay = dividePay;
    }



    
    public Integer getBillingStatus() {
        return billingStatus;
    }



    
    public void setBillingStatus(Integer billingStatus) {
        this.billingStatus = billingStatus;
    }



    
    public String getAreaCode() {
        return areaCode;
    }



    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }



    
    public Integer getDeliveryMode() {
        return deliveryMode;
    }



    
    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }



    
    public String getIsPre() {
        return isPre;
    }



    
    public void setIsPre(String isPre) {
        this.isPre = isPre;
    }



    
    public String getIsCity() {
        return isCity;
    }



    
    public void setIsCity(String isCity) {
        this.isCity = isCity;
    }



    
    public String getIsXian() {
        return isXian;
    }



    
    public void setIsXian(String isXian) {
        this.isXian = isXian;
    }
    
    
}
