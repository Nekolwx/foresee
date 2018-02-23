/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import com.foresee.ftcsp.order.common.CommonDict;


/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author foresee@foresee.com.cn
 * @date 2017年8月24日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class UpdateOrderStatusDTO implements Serializable{

    /**
     * serialVersionUID:TODO。
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键,订单id
     * 表 : t_ord_order
     * 对应字段 : order_id
     */
    @NotNull(message="订单id不能为空")
    @Range(min=CommonDict.ID_MIN_VALUE,
            max=CommonDict.ID_MAX_VALUE,
            message="ID不合法")
    private Long orderId;
    
    /**
     * 订单状态（0待付款 1待处理 2服务中 3已完成 4 已取消 5 已终止）
     * 表 : t_ord_order
     * 对应字段 : order_status
     */
    @NotNull(message="订单状态不能为空")
    private Integer orderStatus;
    
    /**
     * 服务状态（0未启动 1待服务 2服务中 3已完成 4 已终止 5 已取消）
     * 表 : t_ord_order
     * 对应字段 : service_status
     */
    @NotNull(message="服务状态不能为空")
    private Integer serviceStatus;

    
    public Long getOrderId() {
        return orderId;
    }

    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    
    public Integer getOrderStatus() {
        return orderStatus;
    }

    
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    
    public Integer getServiceStatus() {
        return serviceStatus;
    }

    
    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    
    
    
}
