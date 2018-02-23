package com.foresee.ftcsp.order.common;

/**
 * Created by keshijun on 2018-02-06.
 */
public class SignDTO {

    // 支付时间戳
    private String payTime;

    // 更新人
    private String updateUser;

    // 签名
    private String sign;

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
