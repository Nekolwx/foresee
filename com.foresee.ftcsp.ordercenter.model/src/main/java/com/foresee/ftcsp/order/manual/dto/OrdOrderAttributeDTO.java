package com.foresee.ftcsp.order.manual.dto;

import java.io.Serializable;
import java.util.Date;

public class OrdOrderAttributeDTO implements Serializable {
    /**
     * 主键
     * 表 : t_ord_order_attribute
     * 对应字段 : order_attribute_id
     */
    private Long orderAttributeId;

    /**
     * 订单id
     * 表 : t_ord_order_attribute
     * 对应字段 : order_id
     */
    private Long orderId;

    /**
     * 订单属性id
     * 表 : t_ord_order_attribute
     * 对应字段 : attribute_id
     */
    private Long attributeId;

    /**
     * 订单属性值id
     * 表 : t_ord_order_attribute
     * 对应字段 : attribute_value_id
     */
    private Long attributeValueId;

    /**
     * 订单属性值
     * 表 : t_ord_order_attribute
     * 对应字段 : attribute_value
     */
    private String attributeValue;

    /**
     * 创建人
     * 表 : t_ord_order_attribute
     * 对应字段 : create_user
     */
    private String createUser;

    /**
     * 创建时间
     * 表 : t_ord_order_attribute
     * 对应字段 : create_time
     */
    private Date createTime;

    /**
     * 修改人
     * 表 : t_ord_order_attribute
     * 对应字段 : update_user
     */
    private String updateUser;

    /**
     * 修改日期
     * 表 : t_ord_order_attribute
     * 对应字段 : update_time
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ord_order_attribute
     *
     * @MBG Generated Thu Aug 17 10:48:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * get method 
     *
     * @return t_ord_order_attribute.order_attribute_id：主键
     */
    public Long getOrderAttributeId() {
        return orderAttributeId;
    }

    /**
     * set method 
     *
     * @param orderAttributeId  主键
     */
    public void setOrderAttributeId(Long orderAttributeId) {
        this.orderAttributeId = orderAttributeId;
    }

    /**
     * get method 
     *
     * @return t_ord_order_attribute.order_id：订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * set method 
     *
     * @param orderId  订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * get method 
     *
     * @return t_ord_order_attribute.attribute_id：订单属性id
     */
    public Long getAttributeId() {
        return attributeId;
    }

    /**
     * set method 
     *
     * @param attributeId  订单属性id
     */
    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * get method 
     *
     * @return t_ord_order_attribute.attribute_value_id：订单属性值id
     */
    public Long getAttributeValueId() {
        return attributeValueId;
    }

    /**
     * set method 
     *
     * @param attributeValueId  订单属性值id
     */
    public void setAttributeValueId(Long attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    /**
     * get method 
     *
     * @return t_ord_order_attribute.attribute_value：订单属性值
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * set method 
     *
     * @param attributeValue  订单属性值
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue == null ? null : attributeValue.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_order_attribute.create_user：创建人
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
     * @return t_ord_order_attribute.create_time：创建时间
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
     * @return t_ord_order_attribute.update_user：修改人
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
     * @return t_ord_order_attribute.update_time：修改日期
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

    /**
     *
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderAttributeId=").append(orderAttributeId);
        sb.append(", orderId=").append(orderId);
        sb.append(", attributeId=").append(attributeId);
        sb.append(", attributeValueId=").append(attributeValueId);
        sb.append(", attributeValue=").append(attributeValue);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}