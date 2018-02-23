/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * 打赏支付页面初始化接收参数DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年10月12日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class RewardPayInitDTO {
    
    @NotBlank(message = "签名非法")
    private String businessOrderNo;
    
    @NotBlank(message = "签名非法")
    private String payTimestamp;
    
    @NotBlank(message = "签名非法")
    private String sign;

    
    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    
    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    
    public String getPayTimestamp() {
        return payTimestamp;
    }

    
    public void setPayTimestamp(String payTimestamp) {
        this.payTimestamp = payTimestamp;
    }


    
    public String getSign() {
        return sign;
    }


    
    public void setSign(String sign) {
        this.sign = sign;
    }

    
}
