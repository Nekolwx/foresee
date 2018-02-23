/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * <pre>
 * 申请开具发票接口入参DTO。
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

public class IssueElectronicInvoiceDTO {
    /**
     * businessOrderNo:业务订单号。
     */
    @NotBlank(message = "业务订单号不能为空")
    private String businessOrderNo;
    
    /**
     * channel:来源。
     */
    @NotNull(message = "渠道来源不能为空")
    @Pattern(regexp = "^\\d+$",message = "渠道来源参数不合法")
    private String channel;
    /**
     * billCode:账单编码。
     */
    private String billCode;
    
    /**
     * buyerType:购买方类型，0:个人,1:单位。
     */
    private Integer buyerType;
    
    /**
     * buyerName:购买方名称（发票抬头）。
     */
    private String buyerName;
    
    /**
     * buyerTaxpayerNo:购买方纳税人识别号。
     */
    private String buyerTaxpayerNo;
    
    /**
     * buyer_address:购买方地址。
     */
    private String buyerAddress;
    
    
    /**
     * buyerPhone:购买方电话。
     */
    private String buyerPhone;
    
    /**
     * buyer_mobile:购买方联系手机号码。
     */
    private String buyerMobile;
    
    /**
     * buyerBankName:购买方开户行。
     */
    private String buyerBankName;
    
    /**
     * buyerBankAccount:购买方银行账号。
     */
    private String buyerBankAccount;
    
    /**
     * billingType:开票类型（0=蓝字发票；1=红字发票）
     */
    @NotNull(message = "开票类型[billingType]不能为空")
    @Range(min=0 ,max=1,message="开票类型[billingType]错误，请选择0[蓝票]或者1[红票]")
    private Integer billingType;
    
    /**
     * originalInvoiceCode:原发票代码。
     */
    private String originalInvoiceCode;
    
    /**
     * originalInvoiceNo:原发票号码。
     */
    private String originalInvoiceNo;

    /**调用接口的用户ID
     * userId:TODO。
     */
    private String userId;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    
    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    
    public String getChannel() {
        return channel;
    }

    
    public void setChannel(String channel) {
        this.channel = channel;
    }

    
    public String getBillCode() {
        return billCode;
    }

    
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    
    public Integer getBuyerType() {
        return buyerType;
    }

    
    public void setBuyerType(Integer buyerType) {
        this.buyerType = buyerType;
    }

    
    public String getBuyerName() {
        return buyerName;
    }

    
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    
    public String getBuyerTaxpayerNo() {
        return buyerTaxpayerNo;
    }

    
    public void setBuyerTaxpayerNo(String buyerTaxpayerNo) {
        this.buyerTaxpayerNo = buyerTaxpayerNo;
    }

    
    public String getBuyerAddress() {
        return buyerAddress;
    }

    
    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    
    public String getBuyerPhone() {
        return buyerPhone;
    }

    
    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    
    public String getBuyerMobile() {
        return buyerMobile;
    }

    
    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    
    public String getBuyerBankName() {
        return buyerBankName;
    }

    
    public void setBuyerBankName(String buyerBankName) {
        this.buyerBankName = buyerBankName;
    }

    
    public String getBuyerBankAccount() {
        return buyerBankAccount;
    }

    
    public void setBuyerBankAccount(String buyerBankAccount) {
        this.buyerBankAccount = buyerBankAccount;
    }

    
    public Integer getBillingType() {
        return billingType;
    }

    
    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", businessOrderNo=").append(businessOrderNo);
        sb.append(", billCode=").append(billCode);
        sb.append(", buyerName=").append(buyerName);
        sb.append(", buyerTaxpayerNo=").append(buyerTaxpayerNo);
        sb.append(", buyerType=").append(buyerType);
        sb.append(", buyerPhone=").append(buyerPhone);
        sb.append(", buyerAddress=").append(buyerAddress);
        sb.append(", buyerMobile=").append(buyerMobile);
        sb.append(", buyerBankAccount=").append(buyerBankAccount);
        sb.append(", buyerBankName=").append(buyerBankName);
        sb.append(", billingType=").append(billingType);
        sb.append(", originalInvoiceCode=").append(originalInvoiceCode);
        sb.append(", originalInvoiceNo=").append(originalInvoiceNo);
        sb.append(", channel=").append(channel);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
    
}
