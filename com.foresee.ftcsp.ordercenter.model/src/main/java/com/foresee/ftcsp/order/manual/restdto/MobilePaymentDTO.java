package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * <pre>
 * 手机端支付入参DTO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 * @date 2018/1/15
 */
public class MobilePaymentDTO {

    @NotBlank(message = "签名非法")
    private String businessUniqueNumber;

    @NotBlank(message = "签名非法")
    private String payTimestamp;

    @NotBlank(message = "签名非法")
    private String sign;

    @NotBlank(message = "请选择支付方式")
    @Pattern(regexp = "\\d+",message = "支付方式参数非法")
    private String payWay;

    public String getBusinessUniqueNumber() {
        return businessUniqueNumber;
    }

    public void setBusinessUniqueNumber(String businessUniqueNumber) {
        this.businessUniqueNumber = businessUniqueNumber;
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
