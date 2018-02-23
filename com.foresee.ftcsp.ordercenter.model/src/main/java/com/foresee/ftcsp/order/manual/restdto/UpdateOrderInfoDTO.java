/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import com.foresee.ftcsp.order.common.CommonDict;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @date 2017年8月19日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class UpdateOrderInfoDTO {

    @NotEmpty(message="businessOrderNo不能为空")
    private String businessOrderNo;
    
    
    private String salesPerson;
    
    private Date orderTime;
    
    private Integer orderStatus;
    
    private Date serviceStartDate;
    
    private Date serviceEndDate;
    
    @NotNull(message="channel不能为空")
    private Integer channel;
    
    private Integer needInvoice;
    
    private Integer buyerType;
    
    private String buyerTaxpayerNo;
    
    private String buyerName;
    
    private String buyerAddressPhone;

    
    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    
    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    
    public Date getOrderTime() {
        return orderTime;
    }

    
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    
    public Integer getOrderStatus() {
        return orderStatus;
    }

    
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    
    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    
    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    
    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    
    public Integer getChannel() {
        return channel;
    }

    
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    
    public Integer getNeedInvoice() {
        return needInvoice;
    }

    
    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    
    public Integer getBuyerType() {
        return buyerType;
    }

    
    public void setBuyerType(Integer buyerType) {
        this.buyerType = buyerType;
    }

    
    public String getBuyerTaxpayerNo() {
        return buyerTaxpayerNo;
    }

    
    public void setBuyerTaxpayerNo(String buyerTaxpayerNo) {
        this.buyerTaxpayerNo = buyerTaxpayerNo;
    }

    
    public String getBuyerName() {
        return buyerName;
    }

    
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    
    public String getBuyerAddressPhone() {
        return buyerAddressPhone;
    }

    
    public void setBuyerAddressPhone(String buyerAddressPhone) {
        this.buyerAddressPhone = buyerAddressPhone;
    }
    
}
