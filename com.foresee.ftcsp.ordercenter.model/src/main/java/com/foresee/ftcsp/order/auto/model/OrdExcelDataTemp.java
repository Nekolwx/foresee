package com.foresee.ftcsp.order.auto.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdExcelDataTemp implements Serializable {
    /**
     * 数据ID,无实际意义
     * 表 : t_ord_excel_data_temp
     * 对应字段 : data_id
     */
    private Long dataId;

    /**
     * 错误信息
     * 表 : t_ord_excel_data_temp
     * 对应字段 : error_message
     */
    private String errorMessage;

    /**
     * excel文件ID,同一个文件的数据该ID相同
     * 表 : t_ord_excel_data_temp
     * 对应字段 : excel_id
     */
    private Long excelId;

    /**
     * 数据是否生成,默认0未生成,1为已生成
     * 表 : t_ord_excel_data_temp
     * 对应字段 : status
     */
    private Byte status;

    /**
     * 校验是否正常,默认0未检验,1为正常,2为问题数据(存在错误信息)
     * 表 : t_ord_excel_data_temp
     * 对应字段 : is_normal
     */
    private Byte isNormal;

    /**
     * 业务订单号
     * 表 : t_ord_excel_data_temp
     * 对应字段 : business_order_no
     */
    private String businessOrderNo;

    /**
     * 订单金额
     * 表 : t_ord_excel_data_temp
     * 对应字段 : order_amount
     */
    @NotNull(message = "订单金额为空或非法金额数字")
    private BigDecimal orderAmount;

    /**
     * 支付时间
     * 表 : t_ord_excel_data_temp
     * 对应字段 : pay_time
     */
    private Date payTime;

    /**
     * 订购人
     * 表 : t_ord_excel_data_temp
     * 对应字段 : order_user
     */
    private String orderUser;

    /**
     * 订购公司
     * 表 : t_ord_excel_data_temp
     * 对应字段 : company
     */
    @NotBlank(message = "订购公司为空")
    private String company;

    /**
     * 纳税人识别号
     * 表 : t_ord_excel_data_temp
     * 对应字段 : tax_identification_number
     */
    private String taxIdentificationNumber;

    /**
     * 联系电话
     * 表 : t_ord_excel_data_temp
     * 对应字段 : contact_phone
     */
    private String contactPhone;

    /**
     * 对应商品的skuID
     * 表 : t_ord_excel_data_temp
     * 对应字段 : sku_id
     */
    @NotNull(message = "商品sku为空")
    @Range(min = 100000000000000l ,message = "请不要更改Excel商品选项信息")
    private Long skuId;

    /**
     * 任务数量
     * 表 : t_ord_excel_data_temp
     * 对应字段 : task_number
     */
    private Integer taskNumber;

    /**
     * 服务期限(单位:月份)
     * 表 : t_ord_excel_data_temp
     * 对应字段 : service_term_value
     */
    private Integer serviceTermValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ord_excel_data_temp
     *
     * @MBG Generated Thu Nov 23 10:45:18 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.data_id：数据ID,无实际意义
     */
    public Long getDataId() {
        return dataId;
    }

    /**
     * set method 
     *
     * @param dataId  数据ID,无实际意义
     */
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.error_message：错误信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * set method 
     *
     * @param errorMessage  错误信息
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage == null ? null : errorMessage.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.excel_id：excel文件ID,同一个文件的数据该ID相同
     */
    public Long getExcelId() {
        return excelId;
    }

    /**
     * set method 
     *
     * @param excelId  excel文件ID,同一个文件的数据该ID相同
     */
    public void setExcelId(Long excelId) {
        this.excelId = excelId;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.status：数据是否生成,默认0未生成,1为已生成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * set method 
     *
     * @param status  数据是否生成,默认0未生成,1为已生成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.is_normal：校验是否正常,默认0未检验,1为正常,2为问题数据(存在错误信息)
     */
    public Byte getIsNormal() {
        return isNormal;
    }

    /**
     * set method 
     *
     * @param isNormal  校验是否正常,默认0未检验,1为正常,2为问题数据(存在错误信息)
     */
    public void setIsNormal(Byte isNormal) {
        this.isNormal = isNormal;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.business_order_no：业务订单号
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
     * @return t_ord_excel_data_temp.order_amount：订单金额
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * set method 
     *
     * @param orderAmount  订单金额
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.pay_time：支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * set method 
     *
     * @param payTime  支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.order_user：订购人
     */
    public String getOrderUser() {
        return orderUser;
    }

    /**
     * set method 
     *
     * @param orderUser  订购人
     */
    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser == null ? null : orderUser.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.company：订购公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * set method 
     *
     * @param company  订购公司
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.tax_identification_number：纳税人识别号
     */
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    /**
     * set method 
     *
     * @param taxIdentificationNumber  纳税人识别号
     */
    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber == null ? null : taxIdentificationNumber.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.contact_phone：联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * set method 
     *
     * @param contactPhone  联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.sku_id：对应商品的skuID
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * set method 
     *
     * @param skuId  对应商品的skuID
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.task_number：任务数量
     */
    public Integer getTaskNumber() {
        return taskNumber;
    }

    /**
     * set method 
     *
     * @param taskNumber  任务数量
     */
    public void setTaskNumber(Integer taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_data_temp.service_term_value：服务期限(单位:月份)
     */
    public Integer getServiceTermValue() {
        return serviceTermValue;
    }

    /**
     * set method 
     *
     * @param serviceTermValue  服务期限(单位:月份)
     */
    public void setServiceTermValue(Integer serviceTermValue) {
        this.serviceTermValue = serviceTermValue;
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
        sb.append(", dataId=").append(dataId);
        sb.append(", errorMessage=").append(errorMessage);
        sb.append(", excelId=").append(excelId);
        sb.append(", status=").append(status);
        sb.append(", isNormal=").append(isNormal);
        sb.append(", businessOrderNo=").append(businessOrderNo);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", payTime=").append(payTime);
        sb.append(", orderUser=").append(orderUser);
        sb.append(", company=").append(company);
        sb.append(", taxIdentificationNumber=").append(taxIdentificationNumber);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", skuId=").append(skuId);
        sb.append(", taskNumber=").append(taskNumber);
        sb.append(", serviceTermValue=").append(serviceTermValue);
        sb.append("]");
        return sb.toString();
    }
}