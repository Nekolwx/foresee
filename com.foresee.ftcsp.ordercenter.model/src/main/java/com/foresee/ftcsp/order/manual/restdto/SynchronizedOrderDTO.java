/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * 旧平台订单迁移接口入参DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年10月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class SynchronizedOrderDTO {
    
    @NotBlank(message= "[customerId]不能为空")
    private String customerId;
    
    @NotBlank(message= "[orderCode]不能为空")
    private String orderCode;
    
    @NotNull(message= "[goodsId]不能为空")
    private Long goodsId;
    
    @NotNull(message= "[skuId]不能为空")
    private Long skuId;
    
    @NotBlank(message= "[areaCode]不能为空")
    private String areaCode;
    
    @NotNull(message= "[payAmount]不能为空")
    private BigDecimal payAmount;
    
    @NotNull(message= "[serviceStartDate]不能为空")
    private Date serviceStartDate;
    
    @NotNull(message= "[serviceEndDate]不能为空")
    private Date serviceEndDate;
    
    

    private Integer payWay;
    
    private Date payTime;
    
    @NotNull(message= "[orderStatus]不能为空")
    private Integer orderStatus;
    
    @NotNull(message= "[payStatus]不能为空")
    private Integer payStatus;
    
    @NotNull(message= "[refundStatus]不能为空")
    private Integer refundStatus;
    
    @NotNull(message= "[serviceStatus]不能为空")
    private Integer serviceStatus;
    
    @NotNull(message= "[channel]不能为空")
    private Integer channel;
    
    private String contactPerson;
    
    private String contactPhone;
    
    private Integer channelOption;

    
    public String getCustomerId() {
        return customerId;
    }

    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    
    public String getOrderCode() {
        return orderCode;
    }

    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    
    public Long getGoodsId() {
        return goodsId;
    }

    
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    
    public Long getSkuId() {
        return skuId;
    }

    
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    
    public String getAreaCode() {
        return areaCode;
    }

    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    public BigDecimal getPayAmount() {
        return payAmount;
    }

    
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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

    
    public Integer getPayWay() {
        return payWay;
    }

    
    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    
    public Date getPayTime() {
        return payTime;
    }

    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    
    public Integer getOrderStatus() {
        return orderStatus;
    }

    
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    
    public Integer getPayStatus() {
        return payStatus;
    }

    
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    
    public Integer getRefundStatus() {
        return refundStatus;
    }

    
    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    
    public Integer getServiceStatus() {
        return serviceStatus;
    }

    
    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    
    public Integer getChannel() {
        return channel;
    }

    
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    
    public String getContactPerson() {
        return contactPerson;
    }

    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    
    public String getContactPhone() {
        return contactPhone;
    }

    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    
    public Integer getChannelOption() {
        return channelOption;
    }

    
    public void setChannelOption(Integer channelOption) {
        this.channelOption = channelOption;
    }
    
    
    
    
    
    
    

}
