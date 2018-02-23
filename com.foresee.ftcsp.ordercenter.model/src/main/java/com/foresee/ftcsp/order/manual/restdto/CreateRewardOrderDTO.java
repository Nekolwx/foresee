package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/9/11
 */
public class CreateRewardOrderDTO {

    @Pattern(regexp = "^[01]$",message = "客户类型不存在")
    private String cusType; //		必须	客户类型:0为个人,1为单位

    @NotBlank(message = "客户名称不能为空")
    private String cusName; //		必须	客户名称

    @NotBlank(message = "用户id不能为空")
    private String userId; //		必须	用户id（下单人的唯一标识）

    @NotBlank(message = "客户识别号不能为空")
    private String taxIdentification; //	必须	单位类型：纳税人识别号 个人类型：手机号或者身份证号

    @NotBlank(message = "业务订单号不能为空")
    private String businessOrderNo; //	必须	业务订单编号

    @NotNull(message = "渠道来源不能为空")
    @Pattern(regexp = "^\\d+$",message = "渠道来源参数不合法")
    private String channel ; //	必须	渠道 4：财税知识服务平台

    @NotNull(message = "金额不能为空")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?",message = "交易金额异常，请确定金额是否为大于0的数字")
    private String payAmount;	//必须	支付金额

    @NotNull(message = "打赏商品不存在")
    private Long goodsSkuId;		//对应打赏商品的skuId

    @NotBlank(message = "URL不能为空")
    private String payRetUrl;     //同步回调跳转URL页面，即支付成功后需要跳转的页面URL，且不能跟任何参数

    @NotBlank(message = "URL不能为空")
    private String payNotifyUrl;//异步通知回调URL接口，支付成功后支付平台会回调通知到此URL接口，且不能跟任何参数

    private String appId;
    
    /**
     * supportPayWays:支持的支付方式（0:支付宝,1:微信,2:银联,4:财币）。如果不传，默认全支持
     */
    
    private String[] supportPayWays;
    
    /**
     * 商品描述。
     */
    private String goodsDescribe;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaxIdentification() {
        return taxIdentification;
    }

    public void setTaxIdentification(String taxIdentification) {
        this.taxIdentification = taxIdentification;
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

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    
    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    
    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }
    
    
    public String[] getSupportPayWays() {
        return supportPayWays;
    }

    
    public void setSupportPayWays(String[] supportPayWays) {
        this.supportPayWays = supportPayWays;
    }

    

}
