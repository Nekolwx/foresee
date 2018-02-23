/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * 查看发票接口返回发票详细信息DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年11月13日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class InvoiceDetailDTO {
    
    /**
     * invoiceId:发票Id。
     */
    private Long invoiceId;
    
    /**
     * billCode:账单编码
     */
    private String billCode;
    
    /**
     * price:价税合计，单位：元。
     */
    private BigDecimal price;
    
    /**
     * invoiceCode:发票代码。
     */
    private String invoiceCode;
    
    /**
     * invoiceNo:发票号码。
     */
    private String invoiceNo;
    
    /**
     * validateCode:校验码。
     */
    private String validateCode;
    
    /**
     * invalid:是否红冲作废(0：否，1：已红冲)。
     */
    private Integer invalid;
    
    /**
     * originalInvoiceCode:原发票代码。
     */
    private String originalInvoiceCode;
    
    /**
     * originalInvoiceNo:原发票号码。
     */
    private String originalInvoiceNo;
    
    /**
     * billingTime:开票时间。
     */
    private String billingTime;
    
    /**
     * billingTimeD:开票时间。
     */
    private Date billingTimeD;
    
    /**
     * invoiceUrl:查看发票地址url。
     */
    private String invoiceUrl;
    
    private String downloadCode;
    
    
    public Long getInvoiceId() {
        return invoiceId;
    }

    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getBillCode() {
        return billCode;
    }
    
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }


    public BigDecimal getPrice() {
        return price;
    }

    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
    public String getInvoiceCode() {
        return invoiceCode;
    }

    
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    
    public String getInvoiceNo() {
        return invoiceNo;
    }

    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    
    public String getValidateCode() {
        return validateCode;
    }

    
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Integer getInvalid() {
        return invalid;
    }

    
    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    
    public String getOriginalInvoiceCode() {
        return originalInvoiceCode;
    }

    
    public void setOriginalInvoiceCode(String originalInvoiceCode) {
        this.originalInvoiceCode = originalInvoiceCode;
    }

    
    public String getOriginalInvoiceNo() {
        return originalInvoiceNo;
    }

    
    public void setOriginalInvoiceNo(String originalInvoiceNo) {
        this.originalInvoiceNo = originalInvoiceNo;
    }

    
    public String getBillingTime() {
        return billingTime;
    }

    
    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }
    
    
    public Date getBillingTimeD() {
        return billingTimeD;
    }

    
    public void setBillingTimeD(Date billingTimeD) {
        this.billingTimeD = billingTimeD;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    
    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }


    
    public String getDownloadCode() {
        return downloadCode;
    }


    
    public void setDownloadCode(String downloadCode) {
        this.downloadCode = downloadCode;
    }
    
    
    

}
