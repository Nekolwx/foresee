package com.foresee.ftcsp.order.manual.dto.response;

/**
 * Created by foresee on 2018-02-01.
 */
public class CommonResPonseDTO {
    /**
     * 标识
     */
    boolean flag;

    /**
     * 信息
     */
    String message;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
