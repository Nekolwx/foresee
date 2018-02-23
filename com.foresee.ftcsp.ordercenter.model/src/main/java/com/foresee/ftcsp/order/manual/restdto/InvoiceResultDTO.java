/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.util.List;

/**
 * <pre>
 * 查看发票接口返回结果DTO。
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

public class InvoiceResultDTO {
    private Integer dividePay;
    
    private String orderCode;
    
    private List<InvoiceDetailDTO> invoiceList;

    
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
    
    public List<InvoiceDetailDTO> getInvoiceList() {
        return invoiceList;
    }

    
    public void setInvoiceList(List<InvoiceDetailDTO> invoiceList) {
        this.invoiceList = invoiceList;
    }

    
    
    
    

}
