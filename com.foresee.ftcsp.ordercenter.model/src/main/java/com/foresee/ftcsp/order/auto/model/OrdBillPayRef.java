package com.foresee.ftcsp.order.auto.model;

import java.io.Serializable;
import java.util.Date;

public class OrdBillPayRef implements Serializable {
    /**
     * 主键
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : bill_pay_ref_id
     */
    private Long billPayRefId;

    /**
     * 账单id
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : bill_id
     */
    private Long billId;

    /**
     * 支付流水id
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : pay_id
     */
    private Long payId;

    /**
     * 创建人
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : create_user
     */
    private String createUser;

    /**
     * 创建时间
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : create_time
     */
    private Date createTime;

    /**
     * 修改人
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : update_user
     */
    private String updateUser;

    /**
     * 修改日期
     * 表 : t_ord_bill_pay_ref
     * 对应字段 : update_time
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ord_bill_pay_ref
     *
     * @MBG Generated Thu Aug 17 10:48:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * get method 
     *
     * @return t_ord_bill_pay_ref.bill_pay_ref_id：主键
     */
    public Long getBillPayRefId() {
        return billPayRefId;
    }

    /**
     * set method 
     *
     * @param billPayRefId  主键
     */
    public void setBillPayRefId(Long billPayRefId) {
        this.billPayRefId = billPayRefId;
    }

    /**
     * get method 
     *
     * @return t_ord_bill_pay_ref.bill_id：账单id
     */
    public Long getBillId() {
        return billId;
    }

    /**
     * set method 
     *
     * @param billId  账单id
     */
    public void setBillId(Long billId) {
        this.billId = billId;
    }

    /**
     * get method 
     *
     * @return t_ord_bill_pay_ref.pay_id：支付流水id
     */
    public Long getPayId() {
        return payId;
    }

    /**
     * set method 
     *
     * @param payId  支付流水id
     */
    public void setPayId(Long payId) {
        this.payId = payId;
    }

    /**
     * get method 
     *
     * @return t_ord_bill_pay_ref.create_user：创建人
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
     * @return t_ord_bill_pay_ref.create_time：创建时间
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
     * @return t_ord_bill_pay_ref.update_user：修改人
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
     * @return t_ord_bill_pay_ref.update_time：修改日期
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
        sb.append(", billPayRefId=").append(billPayRefId);
        sb.append(", billId=").append(billId);
        sb.append(", payId=").append(payId);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}