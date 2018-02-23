/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import java.util.List;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;

/**
 * <pre>
 * 根据订单查询开票状态结果DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年11月9日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class InvoiceStatusResultDTO {
    
    /**
     * dividePay:订单是否分期 0全额 1分期。
     */
    private Integer dividePay;
    
    /**
     * orderCode:订单编码。
     */
    private String orderCode;
    
    /**
     * orderInvoiceStatus:订单开票状态。
     */
    private Integer orderInvoiceStatus;
    
    /**
     * orderPayStatus:订单支付状态。
     */
    private Integer orderPayStatus;
    
    /**
     * orderPayAmount:订单支付金额。
     */
    private BigDecimal orderPayAmount;
    
    /**
     * billStatusList:分期订单关联的子账单集合。
     */
    private List<BillInvoiceStatusDTO> billStatusList; 
    
    /**
     * orderPayTime:订单支付时间 yyyy-MM-dd hh:mm:ss。
     */
    private String orderPayTime;

    
    public Integer getDividePay() {
        return dividePay;
    }

    
    public void setDividePay(Integer dividePay) {
        this.dividePay = dividePay;
    }

    
    public String getOrderCode() {
        return orderCode;
    }

    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    
    public Integer getOrderInvoiceStatus() {
        return orderInvoiceStatus;
    }

    
    public void setOrderInvoiceStatus(Integer orderInvoiceStatus) {
        this.orderInvoiceStatus = orderInvoiceStatus;
    }

    
    public Integer getOrderPayStatus() {
        return orderPayStatus;
    }

    
    public void setOrderPayStatus(Integer orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }

    
    public BigDecimal getOrderPayAmount() {
        return orderPayAmount;
    }

    
    public void setOrderPayAmount(BigDecimal orderPayAmount) {
        this.orderPayAmount = orderPayAmount;
    }

    
    
    public List<BillInvoiceStatusDTO> getBillStatusList() {
        return billStatusList;
    }


    
    public void setBillStatusList(List<BillInvoiceStatusDTO> billStatusList) {
        this.billStatusList = billStatusList;
    }


    public String getOrderPayTime() {
        return orderPayTime;
    }


    
    public void setOrderPayTime(String orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    
    

}
