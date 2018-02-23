/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.manual.dto.response;

/**
 * <pre>
 * 接收回文DTO。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月25日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class PayResPonseDTO {

    /**
     * 微信公众号支付返回js调起支付控件所需参数。
     */
    String appId;

    /**
     * 微信公众号支付返回js调起支付控件所需参数。
     */
    String packageName;

    /**
     * 微信公众号支付返回js调起支付控件所需参数。
     */
    String signType;

    /**
     * 微信公众号支付返回js调起支付控件所需参数。
     */
    String timeStamp;

    /**
     * 微信公众号支付返回js调起支付控件所需参数。
     */
    String nonceStr;

    /**
     * 微信公众号支付返回js调起支付控件所需参数。
     */
    String paySign;

    /**
     * 项目名称。
     */
    String payProject;

    /**
     * 支付成功跳转页面。
     */
    String payRetUrl;

    /**
     * 支付失败跳转页面。
     */
    String payFailureUrl;

    
    public String getAppId() {
        return appId;
    }

    
    public void setAppId(String appId) {
        this.appId = appId;
    }

    
    public String getPackageName() {
        return packageName;
    }

    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    
    public String getSignType() {
        return signType;
    }

    
    public void setSignType(String signType) {
        this.signType = signType;
    }

    
    public String getTimeStamp() {
        return timeStamp;
    }

    
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    
    public String getNonceStr() {
        return nonceStr;
    }

    
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    
    public String getPaySign() {
        return paySign;
    }

    
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    
    public String getPayProject() {
        return payProject;
    }

    
    public void setPayProject(String payProject) {
        this.payProject = payProject;
    }

    
    public String getPayRetUrl() {
        return payRetUrl;
    }

    
    public void setPayRetUrl(String payRetUrl) {
        this.payRetUrl = payRetUrl;
    }

    
    public String getPayFailureUrl() {
        return payFailureUrl;
    }

    
    public void setPayFailureUrl(String payFailureUrl) {
        this.payFailureUrl = payFailureUrl;
    }

    
}
