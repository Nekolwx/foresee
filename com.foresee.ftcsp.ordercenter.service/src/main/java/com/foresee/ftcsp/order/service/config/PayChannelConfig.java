/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.config;

import org.springframework.stereotype.Component;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictConfig;

/**
 * <pre>
 * 支付渠道配置类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年9月22日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Component
@FtcspDictConfig(dictCode = "pay_channelId_confg", autowire = true)
public class PayChannelConfig {
    
    /**
     * 微信H5渠道ID。
     */
    private String weChatHtmlChannel;
    
    /**
     * 支付宝H5渠道ID。
     */
    private String alipayHtmlChannel;
    
    /**
     * 微信公众号渠道ID。
     */
    private String weChatPublicNumberChannel;
     
    /**
     * 银联支付渠道ID。
     */
    private String unionPayChannel;

    
    public String getWeChatHtmlChannel() {
        return weChatHtmlChannel;
    }

    
    public void setWeChatHtmlChannel(String weChatHtmlChannel) {
        this.weChatHtmlChannel = weChatHtmlChannel;
    }

    
    public String getAlipayHtmlChannel() {
        return alipayHtmlChannel;
    }

    
    public void setAlipayHtmlChannel(String alipayHtmlChannel) {
        this.alipayHtmlChannel = alipayHtmlChannel;
    }

    
    public String getWeChatPublicNumberChannel() {
        return weChatPublicNumberChannel;
    }

    
    public void setWeChatPublicNumberChannel(String weChatPublicNumberChannel) {
        this.weChatPublicNumberChannel = weChatPublicNumberChannel;
    }


    
    public String getUnionPayChannel() {
        return unionPayChannel;
    }


    
    public void setUnionPayChannel(String unionPayChannel) {
        this.unionPayChannel = unionPayChannel;
    }
    
    

}
