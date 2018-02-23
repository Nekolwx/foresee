/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

/**
 * <pre>
 * 发票购买方信息返回结果DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年11月14日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class InvoiceBuyerInfoResultDTO {
    /**
     * 购买方类型0 个人 1 单位
     */
    private Integer buyerType;

    /**
     * 购买方纳税人识别号
     */
    private String buyerTaxpayerNo;

    /**
     * 购买方名称
     */
    private String buyerName;

    /**
     * 购买方地址、电话
     */
    private String buyerAddressPhone;

    /**
     * 购买方银行账号
     */
    private String buyerBankCard;

    /**
     * 购买方电子邮箱地址
     */
    private String buyerEmail;

    /**
     * 购买方联系手机号码
     */
    private String buyerMobile;

    
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

    
    public String getBuyerBankCard() {
        return buyerBankCard;
    }

    
    public void setBuyerBankCard(String buyerBankCard) {
        this.buyerBankCard = buyerBankCard;
    }

    
    public String getBuyerEmail() {
        return buyerEmail;
    }

    
    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    
    public String getBuyerMobile() {
        return buyerMobile;
    }

    
    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }
    
    

}
