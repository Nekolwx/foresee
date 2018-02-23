package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * 手机端支付初始化接口入参DTO
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
public class MobilePaymentInitDTO {

    @NotBlank(message = "非法参数")
    private String businessUniqueNumber;

    @NotBlank(message = "非法参数")
    private String payTimestamp;

    @NotBlank(message = "非法参数")
    private String sign;

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
}
