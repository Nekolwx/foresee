/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年12月14日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author foresee@foresee.com.cn
 * @date 2017年12月14日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class PreRechargeDTO {
    /**
     * ownerId:账号拥有者ID 用户ID或企业ID。
     */
    private String ownerId;
    
    
    /**
     * ownerType:拥有者类型：1个人 2 企业。
     */
    private String ownerType;
    
    
    /**
     * currency:币种:1财币，2人民币。
     */
    private String currency;
    
    
    /**
     * outTradeNo:外部交易号：可用UUID，由调用方生成，64个字符以内，需保证调用端不重复（平台订单的流水）。
     */
    private String outTradeNo;
    
    
    /**
     * outPayNo:外部支付流水号（页面上的流水）。
     */
    private String outPayNo;
    
    
    /**
     * rechargeWay:充值方式：1银联，2支付宝，3微信。
     */
    private String rechargeWay;
    
    
    /**
     * certificate:充值凭证。
     */
    private String certificate;
    
    
    /**
     * subject:主题，填写充值原因。
     */
    private String subject;
    
    
    /**
     * amount:充值金额。
     */
    private String amount;

    /**
     * givenAmount:赠送金额
     */
    private  String givenAmount;
    
    /**
     * rechargeType:1线上充值，2线下充值。
     */
    private String rechargeType;
    
    /**
     * rechargeDate:充值时间。
     */
    private String rechargeDate;
    
    
    /**
     * partyAccountNo:对方账号。
     */
    private String partyAccountNo;
    
    
    /**
     * partyAccountName:对方账号名称。
     */
    private String partyAccountName;
    
    
    /**
     * remark:备注。
     */
    private String remark;

    /**
     * 收款方
     */
    private String payeeAccountNo;

    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public String getOwnerId() {
        return ownerId;
    }


    
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    
    public String getOwnerType() {
        return ownerType;
    }


    
    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }


    
    public String getCurrency() {
        return currency;
    }


    
    public void setCurrency(String currency) {
        this.currency = currency;
    }


    
    public String getOutTradeNo() {
        return outTradeNo;
    }


    
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }


    
    public String getOutPayNo() {
        return outPayNo;
    }


    
    public void setOutPayNo(String outPayNo) {
        this.outPayNo = outPayNo;
    }


    
    public String getRechargeWay() {
        return rechargeWay;
    }


    
    public void setRechargeWay(String rechargeWay) {
        this.rechargeWay = rechargeWay;
    }


    
    public String getCertificate() {
        return certificate;
    }


    
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }


    
    public String getSubject() {
        return subject;
    }


    
    public void setSubject(String subject) {
        this.subject = subject;
    }


    
    public String getAmount() {
        return amount;
    }


    
    public void setAmount(String amount) {
        this.amount = amount;
    }


    
    public String getPartyAccountNo() {
        return partyAccountNo;
    }


    
    public void setPartyAccountNo(String partyAccountNo) {
        this.partyAccountNo = partyAccountNo;
    }


    
    public String getPartyAccountName() {
        return partyAccountName;
    }


    
    public void setPartyAccountName(String partyAccountName) {
        this.partyAccountName = partyAccountName;
    }


    
    public String getRemark() {
        return remark;
    }


    
    public void setRemark(String remark) {
        this.remark = remark;
    }



    
    public String getRechargeType() {
        return rechargeType;
    }



    
    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }



    
    public String getRechargeDate() {
        return rechargeDate;
    }



    
    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public String getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(String givenAmount) {
        this.givenAmount = givenAmount;
    }
}
