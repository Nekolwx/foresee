package com.foresee.ftcsp.order.manual.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.foresee.ftcsp.order.auto.model.OrdOrderUserDep;
import com.foresee.ftcsp.order.manual.restdto.GetOrederGoodSkuDTO;

public class OrdOrderDTO implements Serializable {

    /**
     * 期数
     * 表 : t_ord_order
     * 对应字段 : periods
     */
    private Integer periods;

    /**
     * 数量单位(1、次；2、台；3、件；4、套)
     * 表 : t_ord_order
     * 对应字段 : goods_quantity_unit
     */
    private Integer goodsQuantityUnit;

    /**
     * 服务方式 0 按时间服务 1按次数服务
     * 表 : t_ord_order
     * 对应字段 : service_mode
     */
    private Integer serviceMode;

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
     * 下单时间
     * 表 : t_ord_order
     * 对应字段 : order_time
     */
    private Date orderTime;

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
     * 服务开始日期
     * 表 : t_ord_order
     * 对应字段 : service_start_date
     */
    private Date serviceStartDate;

    /**
     * 服务结束日期
     * 表 : t_ord_order
     * 对应字段 : service_end_date
     */
    private Date serviceEndDate;

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

    /**
     * 服务期限值
     * 表 : t_ord_order
     * 对应字段 : service_term_value
     */
    private Integer serviceTermValue;

    /**
     * 1、即时开通;2、人工开通;3、付款开通
     * 表 : t_ord_order
     * 对应字段 : opening_mode
     */
    private Integer openingMode;

    
    /**
     * payWay:支付方式。
     */
    private String payWay;
    
    /**
     * payNo:支付流水号。
     */
    private String payNo;
    
    /**
     * payState:支付状态。
     */
    private String payState;
    
    /**
     * payFlowingAmount:支付流水中的支付金额。
     */
    private String payFlowingAmount;
    
    /**
     * paySuccessRetUrl:支付成功跳转url。
     */
    private String paySuccessRetUrl;
    
    /**
     * payNotifyUrl:回调接口地址。
     */
    private String payNotifyUrl;
    
    /**
     * goodsName:商品名称。
     */
    private String goodsName;

    /**
     * 商品简情存放
     */
    private List<GetOrederGoodSkuDTO> goodsGroup;
       
    /**
     * cusName:客户名称。
     */
    private String cusName;
    /**
     * nsrNumber:纳税人识别号。
     */
    private String nsrNumber;
    /**
     * mobilePhone:联系电话。
     */
    private String mobilePhone;
    /**
     * deliveryMode:配送方式。
     */
    private String deliveryMode;
    /**
     * taxRate:税率。
     */
    private BigDecimal taxRate;
    /**
     * areaName:区域名称。
     */
    private String areaName;
    /**
     * payTime:支付时间。
     */
    private Date payTime;
    
    /**
     * 支付流水ID。
     */
    private Long payId;
    /**
     * 支父订单号。
     */
    private String orderParenCode;
    
    /**
     * 下单时间。
     */
    private String orderTimeStr;
    
    /**
     * 财币支付userId。
     */
    private String payUserId;
    
    /**
     * 联系人
     * 表 : t_ord_order
     * 对应字段 : contact_person
     */
    private String contactPerson;
    
    /**
     * 联系电话
     * 表 : t_ord_order
     * 对应字段 : contact_phone
     */
    private String contactPhone;
    
    /**
     * 渠道方式
     * 表 : t_ord_order
     * 对应字段 : channel_option
     */
    private Integer channelOption;

    /**
     * 服务期限单位0 天 1 月 2 年
     * 表 : t_ord_order
     * 对应字段 : service_term_unit
     */
    private Integer serviceTermUnit;

    /**
     * 订单对应的人员组织集合
     */
    private List<OrdOrderUserDep> relationList;

    /**
     * 一达通导入订单的任务期数
     */
    private Integer taskNumber;

    /**
     * 订单对应的客户的社会信用代码
     */
    private String creditCode;

    /**
     * 订单h5微信支付时对应的提交用户端ip
     */
    private String payClientIp;

    /**
     * 计费开始时间
     */
    private Date contractChargingStartTime;

    /**
     * 计费结束时间
     */
    private Date contractChargingEndTime;

    public String getPayClientIp() {
        return payClientIp;
    }

    public void setPayClientIp(String payClientIp) {
        this.payClientIp = payClientIp;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(Integer taskNumber) {
        this.taskNumber = taskNumber;
    }

    public List<OrdOrderUserDep> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<OrdOrderUserDep> relationList) {
        this.relationList = relationList;
    }

    public String getPayUserId() {
        return payUserId;
    }

    
    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ord_order
     *
     * @MBG Generated Thu Aug 17 10:48:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

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
     * @return t_ord_order.order_time：下单时间
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * set method 
     *
     * @param orderTime  下单时间
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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
     * @return t_ord_order.service_start_date：服务开始日期
     */
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    /**
     * set method 
     *
     * @param serviceStartDate  服务开始日期
     */
    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /**
     * get method 
     *
     * @return t_ord_order.service_end_date：服务结束日期
     */
    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    /**
     * set method 
     *
     * @param serviceEndDate  服务结束日期
     */
    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
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
    
    
    public String getContactPerson() {
        return contactPerson;
    }


    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }


    
    public String getContactPhone() {
        return contactPhone;
    }


    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }


    
    public Integer getChannelOption() {
        return channelOption;
    }


    
    public void setChannelOption(Integer channelOption) {
        this.channelOption = channelOption;
    }


    public String getPayWay() {
        return payWay;
    }

    
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    
    public String getPayNo() {
        return payNo;
    }

    
    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    
    public String getPayState() {
        return payState;
    }

    
    public void setPayState(String payState) {
        this.payState = payState;
    }

    
    public String getPayFlowingAmount() {
        return payFlowingAmount;
    }

    
    public void setPayFlowingAmount(String payFlowingAmount) {
        this.payFlowingAmount = payFlowingAmount;
    }
    
    

    
    public String getPaySuccessRetUrl() {
        return paySuccessRetUrl;
    }

    
    public void setPaySuccessRetUrl(String paySuccessRetUrl) {
        this.paySuccessRetUrl = paySuccessRetUrl;
    }

    
    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    
    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }
    
    
    public String getGoodsName() {
        return goodsName;
    }

    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
    
    public List<GetOrederGoodSkuDTO> getGoodsGroup() {
        return goodsGroup;
    }

    
    public void setGoodsGroup(List<GetOrederGoodSkuDTO> goodsGroup) {
        this.goodsGroup = goodsGroup;
    }

    public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getNsrNumber() {
		return nsrNumber;
	}

	public void setNsrNumber(String nsrNumber) {
		this.nsrNumber = nsrNumber;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	
	
    public Long getPayId() {
        return payId;
    }

    
    public void setPayId(Long payId) {
        this.payId = payId;
    }
        
    public String getOrderParenCode() {
		return orderParenCode;
	}

	public void setOrderParenCode(String orderParenCode) {
		this.orderParenCode = orderParenCode;
	}
	
	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

    public Integer getServiceTermValue() {
        return serviceTermValue;
    }

    public void setServiceTermValue(Integer serviceTermValue) {
        this.serviceTermValue = serviceTermValue;
    }

    public Integer getServiceTermUnit() {
        return serviceTermUnit;
    }

    public void setServiceTermUnit(Integer serviceTermUnit) {
        this.serviceTermUnit = serviceTermUnit;
    }

    public Integer getOpeningMode() {
        return openingMode;
    }

    public void setOpeningMode(Integer openingMode) {
        this.openingMode = openingMode;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Integer getGoodsQuantityUnit() {
        return goodsQuantityUnit;
    }

    public void setGoodsQuantityUnit(Integer goodsQuantityUnit) {
        this.goodsQuantityUnit = goodsQuantityUnit;
    }

    public Integer getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(Integer serviceMode) {
        this.serviceMode = serviceMode;
    }

    public Date getContractChargingStartTime() {
        return contractChargingStartTime;
    }

    public void setContractChargingStartTime(Date contractChargingStartTime) {
        this.contractChargingStartTime = contractChargingStartTime;
    }

    public Date getContractChargingEndTime() {
        return contractChargingEndTime;
    }

    public void setContractChargingEndTime(Date contractChargingEndTime) {
        this.contractChargingEndTime = contractChargingEndTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", orderType=").append(orderType);
        sb.append(", rootOrderId=").append(rootOrderId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", orderUser=").append(orderUser);
        sb.append(", dividePay=").append(dividePay);
        sb.append(", skuId=").append(skuId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", productId=").append(productId);
        sb.append(", customerId=").append(customerId);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", salesPerson=").append(salesPerson);
        sb.append(", billingStatus=").append(billingStatus);
        sb.append(", goodsOriginalPrice=").append(goodsOriginalPrice);
        sb.append(", goodsQuantity=").append(goodsQuantity);
        sb.append(", goodsAmount=").append(goodsAmount);
        sb.append(", freight=").append(freight);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", channel=").append(channel);
        sb.append(", serviceStartDate=").append(serviceStartDate);
        sb.append(", serviceEndDate=").append(serviceEndDate);
        sb.append(", businessOrderNo=").append(businessOrderNo);
        sb.append(", operationCenterId=").append(operationCenterId);
        sb.append(", storeId=").append(storeId);
        sb.append(", factoryId=").append(factoryId);
        sb.append(", feedback=").append(feedback);
        sb.append(", billingInfoId=").append(billingInfoId);
        sb.append(", isDelivery=").append(isDelivery);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", refundStatus=").append(refundStatus);
        sb.append(", serviceStatus=").append(serviceStatus);
        sb.append(", logisticsStatus=").append(logisticsStatus);
        sb.append(", deleted=").append(deleted);
        sb.append(", isParent=").append(isParent);
        sb.append(", parentId=").append(parentId);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", contactPerson=").append(contactPerson);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", channelOption=").append(channelOption);
        sb.append("]");
        return sb.toString();
    }
}