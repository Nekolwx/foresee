package com.foresee.ftcsp.ordercenter.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UpdateTestGoodsOrderToEndDTO {

    /**
     * 售卖渠道（0 平台 1 网站 2金财代账 3 微信商城）
     * 表 : t_ord_order
     * 对应字段 : channel
     */
    @NotNull(message = "channel不能为空")
    private Integer channel;

    /**
     * 服务开始日期
     * 表 : t_ord_order
     * 对应字段 : service_start_date
     */
    @NotNull(message = "serviceStartDate不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceStartDate;

    /**
     * 服务开始日期加一天
     */
    private Date serviceStartDateAddOneDay;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Date getServiceStartDateAddOneDay() {
        return serviceStartDateAddOneDay;
    }

    public void setServiceStartDateAddOneDay(Date serviceStartDateAddOneDay) {
        this.serviceStartDateAddOneDay = serviceStartDateAddOneDay;
    }
}
