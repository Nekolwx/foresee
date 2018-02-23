/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import com.foresee.ftcsp.order.common.CommonDict;

/**
 * <pre>
 * 订单开通服务入参DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年12月29日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class OpenServiceDTO {
    
    /**
     * 订单id
     * 表 : t_ord_order
     * 对应字段 : order_id
     */
    @NotNull(message="订单id不能为空")
    @Range(min=CommonDict.ID_MIN_VALUE,
            max=CommonDict.ID_MAX_VALUE,
            message="订单id不合法")
    private Long orderId;
    
    
    public Long getOrderId() {
        return orderId;
    }

    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


}
