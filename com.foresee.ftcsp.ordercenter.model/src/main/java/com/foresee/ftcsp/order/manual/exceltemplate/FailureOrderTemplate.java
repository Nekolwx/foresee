package com.foresee.ftcsp.order.manual.exceltemplate;

import com.foresee.ftcsp.order.util.ExcelColumnName;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/30
 */
public class FailureOrderTemplate {

    /**
     * 业务订单号
     * 表 : t_ord_excel_data_temp
     * 对应字段 : business_order_no
     */
    @ExcelColumnName(value = "业务参考号",index = 0)
    private String businessOrderNo;

    /**
     * 订单金额
     * 表 : t_ord_excel_data_temp
     * 对应字段 : order_amount
     */
    @ExcelColumnName(value = "订单总金额",index = 2)
    private String orderAmount;

    @ExcelColumnName(value = "付款时间",index = 3)
    private String payTime;

    /**
     * 订购人
     * 表 : t_ord_excel_data_temp
     * 对应字段 : order_user
     */
    @ExcelColumnName(value = "订购人姓名",index = 4)
    private String orderUser;

    /**
     * 订购公司
     * 表 : t_ord_excel_data_temp
     * 对应字段 : company
     */
    @ExcelColumnName(value = "订购公司",index = 5)
    private String company;

    /**
     * 纳税人识别号
     * 表 : t_ord_excel_data_temp
     * 对应字段 : tax_identification_number
     */
    @ExcelColumnName(value = "纳税人识别号",index = 6)
    private String taxIdentificationNumber;

    /**
     * 联系电话
     * 表 : t_ord_excel_data_temp
     * 对应字段 : contact_phone
     */
    @ExcelColumnName(value = "联系电话",index = 7)
    private String contactPhone;

    /**
     * 对应商品的skuID
     * 表 : t_ord_excel_data_temp
     * 对应字段 : sku_id
     */
    @ExcelColumnName(value = "商品skuId",index=1)
    private String skuId;

    /**
     * 任务数量
     * 表 : t_ord_excel_data_temp
     * 对应字段 : task_number
     */
    @ExcelColumnName(value = "任务数量",index = 8)
    private String taskNumber;

    /**
     * 服务期限(单位:月份)
     * 表 : t_ord_excel_data_temp
     * 对应字段 : service_term_value
     */
    @ExcelColumnName(value = "服务期限",index = 9)
    private String serviceTermValue;

    /**
     * 错误信息
     * 表 : t_ord_excel_data_temp
     * 对应字段 : error_message
     */
    @ExcelColumnName(value = "错误信息",index = 10)
    private String errorMessage;

    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getServiceTermValue() {
        return serviceTermValue;
    }

    public void setServiceTermValue(String serviceTermValue) {
        this.serviceTermValue = serviceTermValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
