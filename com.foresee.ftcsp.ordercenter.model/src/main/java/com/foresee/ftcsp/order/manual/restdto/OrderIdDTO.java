package com.foresee.ftcsp.order.manual.restdto;

import com.foresee.ftcsp.order.common.CommonDict;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 订单di入参dto
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 * @date 2017/12/20
 */
public class OrderIdDTO {

    @NotNull
    @Range(min = CommonDict.ID_MIN_VALUE,max = CommonDict.ID_MAX_VALUE,message = "ID不合法")
    private Long OrderId;

    public Long getOrderId() {
        return OrderId;
    }

    public void setOrderId(Long orderId) {
        OrderId = orderId;
    }
}
