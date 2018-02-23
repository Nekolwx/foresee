/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.config;

import org.springframework.stereotype.Component;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictConfig;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictValue;

/**
 * <pre>
 * 公众号支付配置类。
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
@FtcspDictConfig(dictCode = "pay_publicNumber_config", autowire = true)
public class PayByPublicNumberConfig {
    
    /**
     * payPageDomainName:公众号支付h5页面所在的域名地址。
     */
    private String payPageDomainName;

    
    public String getPayPageDomainName() {
        return payPageDomainName;
    }

    
    public void setPayPageDomainName(String payPageDomainName) {
        this.payPageDomainName = payPageDomainName;
    }
    
    
    

}
