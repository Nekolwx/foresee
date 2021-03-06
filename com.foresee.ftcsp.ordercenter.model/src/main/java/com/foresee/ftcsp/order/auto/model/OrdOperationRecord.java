package com.foresee.ftcsp.order.auto.model;

import java.io.Serializable;
import java.util.Date;

public class OrdOperationRecord implements Serializable {
    /**
     * 主键
     * 表 : t_ord_operation_record
     * 对应字段 : operation_id
     */
    private Long operationId;

    /**
     * 操作类型(0 创建订单)
     * 表 : t_ord_operation_record
     * 对应字段 : operation_type
     */
    private Integer operationType;

    /**
     * 说明
     * 表 : t_ord_operation_record
     * 对应字段 : remark
     */
    private String remark;

    /**
     * 操作人
     * 表 : t_ord_operation_record
     * 对应字段 : operation_user
     */
    private String operationUser;

    /**
     * 操作时间
     * 表 : t_ord_operation_record
     * 对应字段 : operation_time
     */
    private Date operationTime;

    /**
     * 创建人
     * 表 : t_ord_operation_record
     * 对应字段 : create_user
     */
    private String createUser;

    /**
     * 创建时间
     * 表 : t_ord_operation_record
     * 对应字段 : create_time
     */
    private Date createTime;

    /**
     * 修改人
     * 表 : t_ord_operation_record
     * 对应字段 : update_user
     */
    private String updateUser;

    /**
     * 修改日期
     * 表 : t_ord_operation_record
     * 对应字段 : update_time
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ord_operation_record
     *
     * @MBG Generated Thu Aug 17 10:48:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * get method 
     *
     * @return t_ord_operation_record.operation_id：主键
     */
    public Long getOperationId() {
        return operationId;
    }

    /**
     * set method 
     *
     * @param operationId  主键
     */
    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    /**
     * get method 
     *
     * @return t_ord_operation_record.operation_type：操作类型(0 创建订单)
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * set method 
     *
     * @param operationType  操作类型(0 创建订单)
     */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    /**
     * get method 
     *
     * @return t_ord_operation_record.remark：说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * set method 
     *
     * @param remark  说明
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_operation_record.operation_user：操作人
     */
    public String getOperationUser() {
        return operationUser;
    }

    /**
     * set method 
     *
     * @param operationUser  操作人
     */
    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser == null ? null : operationUser.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_operation_record.operation_time：操作时间
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * set method 
     *
     * @param operationTime  操作时间
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    /**
     * get method 
     *
     * @return t_ord_operation_record.create_user：创建人
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
     * @return t_ord_operation_record.create_time：创建时间
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
     * @return t_ord_operation_record.update_user：修改人
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
     * @return t_ord_operation_record.update_time：修改日期
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
        sb.append(", operationId=").append(operationId);
        sb.append(", operationType=").append(operationType);
        sb.append(", remark=").append(remark);
        sb.append(", operationUser=").append(operationUser);
        sb.append(", operationTime=").append(operationTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}