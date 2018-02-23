package com.foresee.ftcsp.order.manual.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrdOrderStatusDTO {

    /**
     * 订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）
     * 表 : t_ord_order
     * 对应字段 : order_status
     */
    private Integer orderStatus;

    /**
     * 付款状态（0不需要付款 1待付款 2付款中 3部分付款 4 已付款 ）
     * 表 : t_ord_order
     * 对应字段 : pay_status
     */
    private Integer payStatus;

    /**
     * 服务状态（0未启动 1待服务 2服务中 3已完成 4 已终止 5 已取消）
     * 表 : t_ord_order
     * 对应字段 : service_status
     */
    private Integer serviceStatus;

    /**
     * 服务开始日期
     * 表 : t_ord_order
     * 对应字段 : service_start_date
     */
    private Date serviceStartDate;

    /**
     * 服务结束日期
     * 表 : t_ord_order
     * 对应字段 : service_end_date
     */
    private Date serviceEndDate;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
