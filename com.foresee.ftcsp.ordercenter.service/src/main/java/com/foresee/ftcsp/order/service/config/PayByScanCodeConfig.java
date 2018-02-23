/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.config;

import org.springframework.stereotype.Component;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictConfig;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictValue;

/**
 * <pre>
 * 扫码支付配置类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Component
@FtcspDictConfig(dictCode = "pay_scanCode_config", autowire = true)
public class PayByScanCodeConfig {
    
    /**
     * wechatPayAppId:微信支付应用ID。
     */
    private String wechatPayAppId;
    
    /**
     * wechatPayChannelId:微信支付来源ID。
     */
    private String wechatPayChannelId;
    
    /**
     * wechatPayAccountId:微信支付账号ID。
     */
    private String wechatPayAccountId;
    
    /**
     * alipayPayAppId:支付宝支付应用ID。
     */
    private String alipayPayAppId;
    
    /**
     * alipayAccountId:支付宝支付账号ID。
     */
    private String alipayAccountId;
    
    /**
     * alipayChannelId:支付宝支付来源ID。
     */
    private String alipayChannelId;
    
    /**
     * paySign:支付签名key。
     */
    private String paySignKey;

    
    public String getWechatPayAppId() {
        return wechatPayAppId;
    }

    
    public void setWechatPayAppId(String wechatPayAppId) {
        this.wechatPayAppId = wechatPayAppId;
    }

    
    public String getWechatPayChannelId() {
        return wechatPayChannelId;
    }

    
    public void setWechatPayChannelId(String wechatPayChannelId) {
        this.wechatPayChannelId = wechatPayChannelId;
    }

    
    public String getWechatPayAccountId() {
        return wechatPayAccountId;
    }

    
    public void setWechatPayAccountId(String wechatPayAccountId) {
        this.wechatPayAccountId = wechatPayAccountId;
    }

    
    public String getAlipayPayAppId() {
        return alipayPayAppId;
    }

    
    public void setAlipayPayAppId(String alipayPayAppId) {
        this.alipayPayAppId = alipayPayAppId;
    }

    
    public String getAlipayAccountId() {
        return alipayAccountId;
    }

    
    public void setAlipayAccountId(String alipayAccountId) {
        this.alipayAccountId = alipayAccountId;
    }

    
    public String getAlipayChannelId() {
        return alipayChannelId;
    }

    
    public void setAlipayChannelId(String alipayChannelId) {
        this.alipayChannelId = alipayChannelId;
    }


    
    public String getPaySignKey() {
        return paySignKey;
    }


    
    public void setPaySignKey(String paySignKey) {
        this.paySignKey = paySignKey;
    }
    
    
    

}
