package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.order.manual.dto.response.CommonResPonseDTO;
import com.foresee.ftcsp.ordercenter.api.dto.UpdateOrderByDeductionsSuccess;

/**
 * <pre>
 * 操作订单接口
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2018/01/31
 */
public interface IOperateOrderService {

    /**
     * 计费扣款后更新订单信息
     * @param updateOrderInfo
     * @return
     */
    CommonResPonseDTO updateOrderByDeductions(UpdateOrderByDeductionsSuccess updateOrderInfo);

}
