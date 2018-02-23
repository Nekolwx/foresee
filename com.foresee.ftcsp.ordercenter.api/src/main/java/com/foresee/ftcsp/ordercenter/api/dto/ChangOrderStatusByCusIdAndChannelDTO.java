package com.foresee.ftcsp.ordercenter.api.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


public class ChangOrderStatusByCusIdAndChannelDTO {

    @NotEmpty(message = "用户id为空")
    private String cusIds;

    @NotNull(message = "来源为空")
    private Integer channel;

    public String getCusIds() {
        return cusIds;
    }

    public void setCusIds(String cusIds) {
        this.cusIds = cusIds;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
