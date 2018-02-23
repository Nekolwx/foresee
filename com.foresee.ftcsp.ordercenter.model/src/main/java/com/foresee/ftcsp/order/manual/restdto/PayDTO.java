/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 * 扫码支付接口请求参数DTO。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月15日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class PayDTO {

    /**
     * orderCode:订单编码。
     */
    @NotBlank(message = "参数[orderCode]不能为空")
    private String orderCode;

    /**
     * payWay:支付方式 0-支付宝，1-微信支付 2-银联。
     */
    @NotBlank(message = "参数[payWay]不能为空")
    private String payWay;

    /**
     * paySuccessRetUrl:回调跳转URL。
     */
    @NotBlank(message = "参数[paySuccessRetUrl]不能为空")
    private String paySuccessRetUrl;
    
    @NotBlank(message = "参数[payNotifyUrl]不能为空")
    private String payNotifyUrl;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPaySuccessRetUrl() {
        return paySuccessRetUrl;
    }

    public void setPaySuccessRetUrl(String paySuccessRetUrl) {
        this.paySuccessRetUrl = paySuccessRetUrl;
    }

    
    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    
    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }
    
    
}
