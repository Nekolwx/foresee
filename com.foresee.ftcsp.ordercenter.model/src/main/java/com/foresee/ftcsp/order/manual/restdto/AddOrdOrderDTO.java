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
 * @date 2017年8月23日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class AddOrdOrderDTO {

    /**
     * 客户id（关联客户信息）
     * 表 : t_ord_order
     * 对应字段 : customer_id
     */
    private String customerId;

    /**
     * 下单人、订购人（关联用户信息）
     * 表 : t_ord_order
     * 对应字段 : order_user
     */
    private String orderUser; 
    
    //联系电话
    private String phone;
    
    /**
     * 产品id
     * 表 : t_ord_order
     * 对应字段 : product_id
     */
    private Long productId;
    
    /**
     * 工厂id
     * 表 : t_ord_order
     * 对应字段 : factory_id
     */
    private String factoryId;

    /**
     * 下单时间（订购时间）
     * 表 : t_ord_order
     * 对应字段 : order_time
     */
    private Date orderTime;
    
    /**
     * 0-全额，1-分期
     * 表 : t_ord_order
     * 对应字段 : divide_pay
     */
    private Integer dividePay;
    
    /**
     * 1、即时开通;2、人工开通;3、付款开通
     * 表 : t_ord_order
     * 对应字段 : opening_mode
     */
    private Integer openingMode;

    
    public String getCustomerId() {
        return customerId;
    }

    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    
    public String getOrderUser() {
        return orderUser;
    }

    
    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    
    public String getPhone() {
        return phone;
    }

    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public Long getProductId() {
        return productId;
    }

    
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    
    public String getFactoryId() {
        return factoryId;
    }

    
    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    
    public Date getOrderTime() {
        return orderTime;
    }

    
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    
    public Integer getDividePay() {
        return dividePay;
    }

    
    public void setDividePay(Integer dividePay) {
        this.dividePay = dividePay;
    }

    
    public Integer getOpeningMode() {
        return openingMode;
    }

    
    public void setOpeningMode(Integer openingMode) {
        this.openingMode = openingMode;
    }
    
    
    
}
