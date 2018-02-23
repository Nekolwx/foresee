package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.manual.dto.OrdOrderStatusDTO;

/**
 * <pre>
 * 订单数据处理接口类。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 *   <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2017年12月26日
 */
public interface IOrderDataProcessingService {

    /**
     * 创单流程赋予的服务状态和付款状态
     * 根据订单对象的商品信息进行查询
     * 根据查询结果更改订单对象里面的服务状态和付款状态
     * @param ordOrder
     */
    void orderServiceChangeByGoods(OrdOrder ordOrder);

    /**
     * 支付接口回调返回需要插入订单的相关状态
     * @param orderId
     * @return
     */
    OrdOrderStatusDTO afterPaymentToOrdSattus(Long orderId);

    /**
     * 根据订单的服务值来计算服务时间。服务开始时间都默认为当前时间。
     * @param ordOrder
     */
    void ServiceTimeByUnit(OrdOrder ordOrder);


}
