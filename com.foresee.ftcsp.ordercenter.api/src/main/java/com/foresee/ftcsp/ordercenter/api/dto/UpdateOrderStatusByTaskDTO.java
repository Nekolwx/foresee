package com.foresee.ftcsp.ordercenter.api.dto;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 仅供任务调用，更改订单状态为已完成的接口入参DTO
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年11月27日
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class UpdateOrderStatusByTaskDTO {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
