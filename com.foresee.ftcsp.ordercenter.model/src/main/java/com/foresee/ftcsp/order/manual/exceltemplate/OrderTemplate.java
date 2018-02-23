package com.foresee.ftcsp.order.manual.exceltemplate;

import com.foresee.ftcsp.order.util.ExcelColumnName;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * 批量导入订单Excel模板类
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/21
 */
public class OrderTemplate  {

    @ExcelColumnName("序号")
    private String serialNum;

    /**
     * 业务订单号
     * 表 : t_ord_excel_data_temp
     * 对应字段 : business_order_no
     */
    @ExcelColumnName(value = "业务参考号")
    private String businessOrderNo;

    @ExcelColumnName("商品名称\n(请选择)")
    private String goodsName;


    /**
     * 订单金额
     * 表 : t_ord_excel_data_temp
     * 对应字段 : order_amount
     */
    @ExcelColumnName(value = "订单总金额\n(面议时请自行输入实际金额)")
    private String orderAmountTemp;

    /**
     * 支付时间
     * 表 : t_ord_excel_data_temp
     * 对应字段 : pay_time
     */
    @ExcelColumnName(value = "付款时间")
    private String payTimeTemp;

    /**
     * 订购人
     * 表 : t_ord_excel_data_temp
     * 对应字段 : order_user
     */
    @ExcelColumnName(value = "订购人姓名")
    private String orderUser;

    /**
     * 订购公司
     * 表 : t_ord_excel_data_temp
     * 对应字段 : company
     */
    @ExcelColumnName(value = "订购公司")
    private String company;

    /**
     * 纳税人识别号
     * 表 : t_ord_excel_data_temp
     * 对应字段 : tax_identification_number
     */
    @ExcelColumnName(value = "纳税人识别号")
    private String taxIdentificationNumber;

    /**
     * 联系电话
     * 表 : t_ord_excel_data_temp
     * 对应字段 : contact_phone
     */
    @ExcelColumnName(value = "联系电话")
    private String contactPhone;

    /**
     * 对应商品的skuID
     * 表 : t_ord_excel_data_temp
     * 对应字段 : sku_id
     */
    @ExcelColumnName(value = "商品sku\n(勿编辑)")
    private String skuId;

    /**
     * 任务数量
     * 表 : t_ord_excel_data_temp
     * 对应字段 : task_number
     */
    @ExcelColumnName(value = "任务数量\n(勿编辑)")
    private String taskNumber;

    /**
     * 服务期限(单位:月份)
     * 表 : t_ord_excel_data_temp
     * 对应字段 : service_term_value
     */
    @ExcelColumnName(value = "服务期限(月份)")
    private String serviceTermValue;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public String getOrderAmountTemp() {
        return orderAmountTemp;
    }

    public void setOrderAmountTemp(String orderAmountTemp) {
        this.orderAmountTemp = orderAmountTemp;
    }

    public String getPayTimeTemp() {
        return payTimeTemp;
    }

    public void setPayTimeTemp(String payTimeTemp) {
        this.payTimeTemp = payTimeTemp;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
