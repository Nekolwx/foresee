package com.foresee.ftcsp.order.service.config;

import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictConfig;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictValue;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 打赏支付相关字典
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/9/12
 */

@Component
@FtcspDictConfig(dictCode = "pay_reward_config",autowire = false)
public class PayRewardConfig {

    @FtcspDictValue(value = "payPageUrl")
    private String payPageUrl;

    public String getPayPageUrl() {
        return payPageUrl;
    }

    public void setPayPageUrl(String payPageUrl) {
        this.payPageUrl = payPageUrl;
    }
    
    @FtcspDictValue(value = "mobilePayPageUrl")
    private String mobilePayPageUrl;

    
    public String getMobilePayPageUrl() {
        return mobilePayPageUrl;
    }

    
    public void setMobilePayPageUrl(String mobilePayPageUrl) {
        this.mobilePayPageUrl = mobilePayPageUrl;
    }
    
    
}
