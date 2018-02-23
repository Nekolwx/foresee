package com.foresee.ftcsp.order.manual.restdto;

import java.util.Date;

public class RepCreateOrderDTO {
    /**
     * 订单编号
     * 表 : t_ord_order
     * 对应字段 : order_code
     */
    private String orderCode;

    /**
     * 支付流水
     * 表 : t_ord_pay_flowing
     * 对应字段 : pay_no
     */
    private String payNo;

    /**
     * 下单时间
     * 表 : t_ord_order
     * 对应字段 : order_time
     */
    private Date orderTime;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
