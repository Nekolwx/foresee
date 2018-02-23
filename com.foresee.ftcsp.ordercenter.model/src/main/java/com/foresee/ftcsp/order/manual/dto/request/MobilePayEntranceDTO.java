/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.foresee.ftcsp.order.common.CommonDict;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * <pre>
 * 统一移动端支付接入接口入参DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2018年1月15日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class MobilePayEntranceDTO {
    
    /**
     * cusType:客户类型:0为个人,1为单位。
     */
    //@Pattern(regexp = "^[01]$",message = "客户类型不存在")
    private String cusType;
    
    /**
     * taxIdentification:单位类型：纳税人识别号
     *                   个人类型：手机号或者身份证号。
     */
    //@Pattern(regexp = "^\\w+$",message = "纳税人识别号不符合标准")
    private String taxIdentification;
    
    /**
     * cusName:客户名称。
     */
    @Pattern(regexp = "^.{0,200}$",message = "客户名称超长")
    private String cusName;
    
    /**
     * cusId:客户ID。
     */
    private String cusId;
    
    /**
     * userId:用户id。
     */
    //@NotBlank(message="用户id不能为空")
    private String userId;
    
    /**
     * businessOrderNo:业务订单编号（业务方的订单唯一标识）。
     */
    @NotBlank(message="业务订单号不能为空")
    @Pattern(regexp = "^.{1,64}$",message = "业务订单号超长")
    private String businessOrderNo;
    
    /**
     * channel:渠道。
     */
    @NotBlank(message="渠道来源不能为空")
    @Pattern(regexp = "^\\d+$",message = "渠道来源参数不合法")
    private String channel;
    
    
    /**
     * payAppId:接入支付应用ID。
     */
    @NotBlank(message="接入支付应用ID不能为空")
    private String payAppId;
    
    /**
     * payAmount:支付金额。
     */
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?",message = "支付金额异常，请输入合法金额")
    private String payAmount;
    
    /**
     * goodsSkuId:商品SKUID。
     */
    @NotNull(message = "商品SKUID不能为空")
    @Range(min = CommonDict.ID_MIN_VALUE,max = CommonDict.ID_MAX_VALUE,message = "商品skuId不符合规定")
    private Long goodsSkuId;
    
    /**
     * goodsQuantity:商品数量。
     */
    @Range(min=1,message="商品数量最小为1")
    private Integer goodsQuantity;
    
    /**
     * goodsDescribe:商品描述。
     */
    @Pattern(regexp = "^.{0,60}$",message = "商品描述超长")
    private String goodsDescribe;
    
    
    /**
     * payRetUrl:同步回调跳转URL页面。
     */
    @NotBlank(message = "同步回调跳转URL不能为空")
    private String payRetUrl;
    
    /**
     * payNotifyUrl:异步通知回调URL接口。
     */
    @NotBlank(message = "异步通知回调URL不能为空")
    private String payNotifyUrl;
    
    /**
     * supportPayWays:支持的支付方式（2:银联,4:财币,5:公众号6:支付宝,7:微信,）且5与7不可同时存在。
     */
    @Valid
    @NotEmpty(message="支持的支付方式不能为空")
    private String[] supportPayWays;
    
    /**
     * payClientIp:APP和网页支付提交用户端ip。
     */
    private String payClientIp;
    
    /**
     * payOpenId:微信用户在商户对应appid下的唯一标识。
     */
    private String payOpenId;
    
    /**
     * payFailureUrl:公众号支付失败或者用户取消支付后跳转的页面。
     */
    private String payFailureUrl;
    
    private Long goodsId;
    
    private Boolean containPublicNumber;

    
    public String getCusType() {
        return cusType;
    }

    
    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    
    public String getTaxIdentification() {
        return taxIdentification;
    }

    
    public void setTaxIdentification(String taxIdentification) {
        this.taxIdentification = taxIdentification;
    }

    
    public String getCusName() {
        return cusName;
    }

    
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    
    public String getCusId() {
        return cusId;
    }

    
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    
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

    
    public String getPayAppId() {
        return payAppId;
    }

    
    public void setPayAppId(String payAppId) {
        this.payAppId = payAppId;
    }

    
    public String getPayAmount() {
        return payAmount;
    }

    
    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    
    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    
    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    
    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    
    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    
    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    
    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    
    public String getPayRetUrl() {
        return payRetUrl;
    }

    
    public void setPayRetUrl(String payRetUrl) {
        this.payRetUrl = payRetUrl;
    }

    
    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    
    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    
   

    
    
    public String[] getSupportPayWays() {
        return supportPayWays;
    }


    
    public void setSupportPayWays(String[] supportPayWays) {
        this.supportPayWays = supportPayWays;
    }


    public String getPayClientIp() {
        return payClientIp;
    }

    
    public void setPayClientIp(String payClientIp) {
        this.payClientIp = payClientIp;
    }

    
    public String getPayOpenId() {
        return payOpenId;
    }

    
    public void setPayOpenId(String payOpenId) {
        this.payOpenId = payOpenId;
    }

    
    public String getPayFailureUrl() {
        return payFailureUrl;
    }

    
    public void setPayFailureUrl(String payFailureUrl) {
        this.payFailureUrl = payFailureUrl;
    }


    
    public Long getGoodsId() {
        return goodsId;
    }


    
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }


    
    public Boolean getContainPublicNumber() {
        return containPublicNumber;
    }


    
    public void setContainPublicNumber(Boolean containPublicNumber) {
        this.containPublicNumber = containPublicNumber;
    }
    
    

}
