/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;

/**
 * <pre>
 * 子账单开票状态查询返回结果。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年11月10日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class BillInvoiceStatusDTO {
    /**
     * billCode:账单编码。
     */
    private String billCode;
    
    /**
     * billInvoiceStatus:账单开票状态。
     */
    private Integer billInvoiceStatus;
    
    /**
     * billPayStatus:账单支付状态。
     */
    private Integer billPayStatus;
    
    /**
     * billPayAmount:账单支付金额。
     */
    private BigDecimal billPayAmount;
    
    /**
     * billPayTime:账单支付时间 yyyy-MM-dd hh:mm:ss。
     */
    private String billPayTime;

    
    public String getBillCode() {
        return billCode;
    }

    
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    
    public Integer getBillInvoiceStatus() {
        return billInvoiceStatus;
    }

    
    public void setBillInvoiceStatus(Integer billInvoiceStatus) {
        this.billInvoiceStatus = billInvoiceStatus;
    }

    
    public Integer getBillPayStatus() {
        return billPayStatus;
    }

    
    public void setBillPayStatus(Integer billPayStatus) {
        this.billPayStatus = billPayStatus;
    }

    


    
    
    public BigDecimal getBillPayAmount() {
        return billPayAmount;
    }


    
    public void setBillPayAmount(BigDecimal billPayAmount) {
        this.billPayAmount = billPayAmount;
    }


    public String getBillPayTime() {
        return billPayTime;
    }

    
    public void setBillPayTime(String billPayTime) {
        this.billPayTime = billPayTime;
    }

}
