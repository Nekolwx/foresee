/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * 打赏支付接口请求参数DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年9月11日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class RewardPayDTO {
    
    @NotBlank(message = "签名非法")
    private String businessOrderNo;
    
    @NotBlank(message = "签名非法")
    private String payTimestamp;
    
    @NotBlank(message = "签名非法")
    private String sign;
    
    @NotBlank(message = "请选择支付方式")
    private String payWay;

    
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

    
    public String getPayWay() {
        return payWay;
    }

    
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    
    
  
    
    

}
