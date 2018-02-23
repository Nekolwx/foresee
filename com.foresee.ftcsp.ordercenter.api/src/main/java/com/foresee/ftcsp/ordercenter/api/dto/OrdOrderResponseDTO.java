package com.foresee.ftcsp.ordercenter.api.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrdOrderResponseDTO {
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

    /**
     * 根订单id（用于订单续期）
     * 表 : t_ord_order
     * 对应字段 : root_order_id
     */
    private Long rootOrderId;

    /**
     * 订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）
     * 表 : t_ord_order
     * 对应字段 : order_status
     */
    private Integer orderStatus;


    /**
     * 下单人（关联用户信息）
     * 表 : t_ord_order
     * 对应字段 : order_user
     */
    private String orderUser;

    /**
     * 0-全额，1-分期
     * 表 : t_ord_order
     * 对应字段 : divide_pay
     */
    private Integer dividePay;

    /**
     * sku_id（关联商品信息）
     * 表 : t_ord_order
     * 对应字段 : sku_id
     */
    private Long skuId;

    /**
     * 商品id
     * 表 : t_ord_order
     * 对应字段 : goods_id
     */
    private Long goodsId;

    /**
     * 产品id
     * 表 : t_ord_order
     * 对应字段 : product_id
     */
    private Long productId;

    /**
     * 客户id（关联客户信息）
     * 表 : t_ord_order
     * 对应字段 : customer_id
     */
    private String customerId;

    /**
     * 区域编码
     * 表 : t_ord_order
     * 对应字段 : area_code
     */
    private String areaCode;

    /**
     * 销售人（关联用户信息）
     * 表 : t_ord_order
     * 对应字段 : sales_person
     */
    private String salesPerson;

    /**
     * 开票状态0 未开票 1 已开票 2 不需要开票
     * 表 : t_ord_order
     * 对应字段 : billing_status
     */
    private Integer billingStatus;

    /**
     * 商品原价
     * 表 : t_ord_order
     * 对应字段 : goods_original_price
     */
    private BigDecimal goodsOriginalPrice;

    /**
     * 商品总数量
     * 表 : t_ord_order
     * 对应字段 : goods_quantity
     */
    private Integer goodsQuantity;

    /**
     * 商品金额
     * 表 : t_ord_order
     * 对应字段 : goods_amount
     */
    private BigDecimal goodsAmount;

    /**
     * 运费
     * 表 : t_ord_order
     * 对应字段 : freight
     */
    private BigDecimal freight;

    /**
     * 优惠金额
     * 表 : t_ord_order
     * 对应字段 : discount_amount
     */
    private BigDecimal discountAmount;

    /**
     * 支付金额
     * 表 : t_ord_order
     * 对应字段 : pay_amount
     */
    private BigDecimal payAmount;

    /**
     * 售卖渠道（0 平台 1 网站 ）
     * 表 : t_ord_order
     * 对应字段 : channel
     */
    private Integer channel;

   

    /**
     * 业务订单号
     * 表 : t_ord_order
     * 对应字段 : business_order_no
     */
    private String businessOrderNo;

    /**
     * 运营中心id
     * 表 : t_ord_order
     * 对应字段 : operation_center_id
     */
    private String operationCenterId;

    /**
     * 门店id
     * 表 : t_ord_order
     * 对应字段 : store_id
     */
    private String storeId;

    /**
     * 工厂id
     * 表 : t_ord_order
     * 对应字段 : factory_id
     */
    private String factoryId;

    /**
     * 客户留言
     * 表 : t_ord_order
     * 对应字段 : feedback
     */
    private String feedback;

    /**
     * 开票信息id
     * 表 : t_ord_order
     * 对应字段 : billing_info_id
     */
    private Long billingInfoId;

    /**
     * 是否需要配送0 不需要 1 需要
     * 表 : t_ord_order
     * 对应字段 : is_delivery
     */
    private Integer isDelivery;

    /**
     * 付款状态（0不需要付款 1待付款 2付款中 3部分付款 4 已付款 ）
     * 表 : t_ord_order
     * 对应字段 : pay_status
     */
    private Integer payStatus;

    /**
     * 退款状态 （0未启动 1待退款2部分退款 3已退款）
     * 表 : t_ord_order
     * 对应字段 : refund_status
     */
    private Integer refundStatus;

    /**
     * 服务状态（0未启动 1待服务 2服务中 3已完成 4 已终止 5 已取消）
     * 表 : t_ord_order
     * 对应字段 : service_status
     */
    private Integer serviceStatus;

    /**
     * 物流状态（0不需要物流 1未启动 2待发货 3已发货 4已完成 5拒收 6 已取消）
     * 表 : t_ord_order
     * 对应字段 : logistics_status
     */
    private Integer logisticsStatus;

    /**
     * 是否删除（0默认值未删除，1已删除）
     * 表 : t_ord_order
     * 对应字段 : deleted
     */
    private Integer deleted;

    /**
     * 是否是父订单（0是 1否）
     * 表 : t_ord_order
     * 对应字段 : is_parent
     */
    private Integer isParent;

    /**
     * 父订单id
     * 表 : t_ord_order
     * 对应字段 : parent_id
     */
    private Long parentId;

    /**
     * 创建人
     * 表 : t_ord_order
     * 对应字段 : create_user
     */
    private String createUser;

    /**
     * 创建时间
     * 表 : t_ord_order
     * 对应字段 : create_time
     */
    private Date createTime;

    /**
     * 修改人
     * 表 : t_ord_order
     * 对应字段 : update_user
     */
    private String updateUser;

    /**
     * 修改日期
     * 表 : t_ord_order
     * 对应字段 : update_time
     */
    private Date updateTime;

    //父订单id
    private String parentOrderId;
    
    //父订单编号
    private String parentOrderCode;
    
    //客户名称
    private String customerName;
    
    //纳税人识别码
    private String taxIdentificationNumber;
    
    //订单商品
    private String goodsName;
    
    //付款方式
    private Integer payWay;
    
   
    
    //配送方式
    private Integer deliveryMode;
    
    //联系人
    private String buyerName;
    
    //联系方式
    private String buyerAddressPhone;
    
    //区域名称
    private String areaName;

    // 合同编码
    private String contractCode;

    // 计费开始时间
    private Date contractChargingStartTime;

    // 计费结束时间
    private Date contractChargingEndTime;

    // 开通方式
    private Integer openingMode;
    
    /**
     * 下单时间
     * 表 : t_ord_order
     * 对应字段 : order_time
     */
    private String orderTime;
    
    //支付时间
    private String payDate;
    /**
     * 服务开始日期
     * 表 : t_ord_order
     * 对应字段 : service_start_date
     */
    private String serviceStartDate;

    /**
     * 服务结束日期
     * 表 : t_ord_order
     * 对应字段 : service_end_date
     */
    private String serviceEndDate;
    
    /**
     * get method 
     *
     * @return t_ord_order.order_id：主键,订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * set method 
     *
     * @param orderId  主键,订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.order_code：订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * set method 
     *
     * @param orderCode  订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.order_type：订单类型（0-新增 1-续期）
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * set method 
     *
     * @param orderType  订单类型（0-新增 1-续期）
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * get method 
     *
     * @return t_ord_order.root_order_id：根订单id（用于订单续期）
     */
    public Long getRootOrderId() {
        return rootOrderId;
    }

    /**
     * set method 
     *
     * @param rootOrderId  根订单id（用于订单续期）
     */
    public void setRootOrderId(Long rootOrderId) {
        this.rootOrderId = rootOrderId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.order_status：订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * set method 
     *
     * @param orderStatus  订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * get method 
     *
     * @return t_ord_order.order_user：下单人（关联用户信息）
     */
    public String getOrderUser() {
        return orderUser;
    }

    /**
     * set method 
     *
     * @param orderUser  下单人（关联用户信息）
     */
    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser == null ? null : orderUser.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.divide_pay：0-全额，1-分期
     */
    public Integer getDividePay() {
        return dividePay;
    }

    /**
     * set method 
     *
     * @param dividePay  0-全额，1-分期
     */
    public void setDividePay(Integer dividePay) {
        this.dividePay = dividePay;
    }

    /**
     * get method 
     *
     * @return t_ord_order.sku_id：sku_id（关联商品信息）
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * set method 
     *
     * @param skuId  sku_id（关联商品信息）
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.goods_id：商品id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * set method 
     *
     * @param goodsId  商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.product_id：产品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * set method 
     *
     * @param productId  产品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.customer_id：客户id（关联客户信息）
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * set method 
     *
     * @param customerId  客户id（关联客户信息）
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.area_code：区域编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * set method 
     *
     * @param areaCode  区域编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.sales_person：销售人（关联用户信息）
     */
    public String getSalesPerson() {
        return salesPerson;
    }

    /**
     * set method 
     *
     * @param salesPerson  销售人（关联用户信息）
     */
    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson == null ? null : salesPerson.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.billing_status：开票状态0 未开票 1 已开票 2 不需要开票
     */
    public Integer getBillingStatus() {
        return billingStatus;
    }

    /**
     * set method 
     *
     * @param billingStatus  开票状态0 未开票 1 已开票 2 不需要开票
     */
    public void setBillingStatus(Integer billingStatus) {
        this.billingStatus = billingStatus;
    }

    /**
     * get method 
     *
     * @return t_ord_order.goods_original_price：商品原价
     */
    public BigDecimal getGoodsOriginalPrice() {
        return goodsOriginalPrice;
    }

    /**
     * set method 
     *
     * @param goodsOriginalPrice  商品原价
     */
    public void setGoodsOriginalPrice(BigDecimal goodsOriginalPrice) {
        this.goodsOriginalPrice = goodsOriginalPrice;
    }

    /**
     * get method 
     *
     * @return t_ord_order.goods_quantity：商品总数量
     */
    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    /**
     * set method 
     *
     * @param goodsQuantity  商品总数量
     */
    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    /**
     * get method 
     *
     * @return t_ord_order.goods_amount：商品金额
     */
    public BigDecimal getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * set method 
     *
     * @param goodsAmount  商品金额
     */
    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     * get method 
     *
     * @return t_ord_order.freight：运费
     */
    public BigDecimal getFreight() {
        return freight;
    }

    /**
     * set method 
     *
     * @param freight  运费
     */
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    /**
     * get method 
     *
     * @return t_ord_order.discount_amount：优惠金额
     */
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    /**
     * set method 
     *
     * @param discountAmount  优惠金额
     */
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * get method 
     *
     * @return t_ord_order.pay_amount：支付金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * set method 
     *
     * @param payAmount  支付金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * get method 
     *
     * @return t_ord_order.channel：售卖渠道（0 平台 1 网站 ）
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * set method 
     *
     * @param channel  售卖渠道（0 平台 1 网站 ）
     */
    public void setChannel(Integer channel) {
        this.channel = channel;
    }


    /**
     * get method 
     *
     * @return t_ord_order.business_order_no：业务订单号
     */
    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    /**
     * set method 
     *
     * @param businessOrderNo  业务订单号
     */
    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo == null ? null : businessOrderNo.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.operation_center_id：运营中心id
     */
    public String getOperationCenterId() {
        return operationCenterId;
    }

    /**
     * set method 
     *
     * @param operationCenterId  运营中心id
     */
    public void setOperationCenterId(String operationCenterId) {
        this.operationCenterId = operationCenterId == null ? null : operationCenterId.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.store_id：门店id
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * set method 
     *
     * @param storeId  门店id
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.factory_id：工厂id
     */
    public String getFactoryId() {
        return factoryId;
    }

    /**
     * set method 
     *
     * @param factoryId  工厂id
     */
    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId == null ? null : factoryId.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.feedback：客户留言
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * set method 
     *
     * @param feedback  客户留言
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.billing_info_id：开票信息id
     */
    public Long getBillingInfoId() {
        return billingInfoId;
    }

    /**
     * set method 
     *
     * @param billingInfoId  开票信息id
     */
    public void setBillingInfoId(Long billingInfoId) {
        this.billingInfoId = billingInfoId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.is_delivery：是否需要配送0 不需要 1 需要
     */
    public Integer getIsDelivery() {
        return isDelivery;
    }

    /**
     * set method 
     *
     * @param isDelivery  是否需要配送0 不需要 1 需要
     */
    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    /**
     * get method 
     *
     * @return t_ord_order.pay_status：付款状态（0不需要付款 1待付款 2付款中 3部分付款 4 已付款 ）
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * set method 
     *
     * @param payStatus  付款状态（0不需要付款 1待付款 2付款中 3部分付款 4 已付款 ）
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * get method 
     *
     * @return t_ord_order.refund_status：退款状态 （0未启动 1待退款2部分退款 3已退款）
     */
    public Integer getRefundStatus() {
        return refundStatus;
    }

    /**
     * set method 
     *
     * @param refundStatus  退款状态 （0未启动 1待退款2部分退款 3已退款）
     */
    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    /**
     * get method 
     *
     * @return t_ord_order.service_status：服务状态（0未启动 1待服务 2服务中 3已完成 4 已终止 5 已取消）
     */
    public Integer getServiceStatus() {
        return serviceStatus;
    }

    /**
     * set method 
     *
     * @param serviceStatus  服务状态（0未启动 1待服务 2服务中 3已完成 4 已终止 5 已取消）
     */
    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    /**
     * get method 
     *
     * @return t_ord_order.logistics_status：物流状态（0不需要物流 1未启动 2待发货 3已发货 4已完成 5拒收 6 已取消）
     */
    public Integer getLogisticsStatus() {
        return logisticsStatus;
    }

    /**
     * set method 
     *
     * @param logisticsStatus  物流状态（0不需要物流 1未启动 2待发货 3已发货 4已完成 5拒收 6 已取消）
     */
    public void setLogisticsStatus(Integer logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    /**
     * get method 
     *
     * @return t_ord_order.deleted：是否删除（0默认值未删除，1已删除）
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * set method 
     *
     * @param deleted  是否删除（0默认值未删除，1已删除）
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * get method 
     *
     * @return t_ord_order.is_parent：是否是父订单（0是 1否）
     */
    public Integer getIsParent() {
        return isParent;
    }

    /**
     * set method 
     *
     * @param isParent  是否是父订单（0是 1否）
     */
    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    /**
     * get method 
     *
     * @return t_ord_order.parent_id：父订单id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * set method 
     *
     * @param parentId  父订单id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * get method 
     *
     * @return t_ord_order.create_user：创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * set method 
     *
     * @param createUser  创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.create_time：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * set method 
     *
     * @param createTime  创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * get method 
     *
     * @return t_ord_order.update_user：修改人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * set method 
     *
     * @param updateUser  修改人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order.update_time：修改日期
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * set method 
     *
     * @param updateTime  修改日期
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
    public String getParentOrderId() {
        return parentOrderId;
    }

    
    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    
    public String getParentOrderCode() {
        return parentOrderCode;
    }

    
    public void setParentOrderCode(String parentOrderCode) {
        this.parentOrderCode = parentOrderCode;
    }

    
    public String getCustomerName() {
        return customerName;
    }

    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    
    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    
    public String getGoodsName() {
        return goodsName;
    }

    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getPayWay() {
        return payWay;
    }

    
    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }


    
    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    
    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    
    public String getBuyerName() {
        return buyerName;
    }

    
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    
    public String getBuyerAddressPhone() {
        return buyerAddressPhone;
    }

    
    public void setBuyerAddressPhone(String buyerAddressPhone) {
        this.buyerAddressPhone = buyerAddressPhone;
    }

    
    public String getAreaName() {
        return areaName;
    }

    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    
    public String getOrderTime() {
        return orderTime;
    }

    
    public String getPayDate() {
        return payDate;
    }

    
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    
    public String getServiceStartDate() {
        return serviceStartDate;
    }

    
    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    
    public String getServiceEndDate() {
        return serviceEndDate;
    }

    
    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }


    public String getContractCode() {
        return contractCode;
    }

    public Date getContractChargingStartTime() {
        return contractChargingStartTime;
    }

    public Date getContractChargingEndTime() {
        return contractChargingEndTime;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public void setContractChargingStartTime(Date contractChargingStartTime) {
        this.contractChargingStartTime = contractChargingStartTime;
    }

    public void setContractChargingEndTime(Date contractChargingEndTime) {
        this.contractChargingEndTime = contractChargingEndTime;
    }

    public Integer getOpeningMode() {
        return openingMode;
    }

    public void setOpeningMode(Integer openingMode) {
        this.openingMode = openingMode;
    }
}