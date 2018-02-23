package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.order.auto.model.*;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;

/**
 * <pre>
 * 对象组装接口
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2018年01月16日
 */
public interface IObjectAssemblyService {

    /**
     * 生成支付流水 TODO。
     *
     * @return String
     */
    String generatePayNo();

    /**
     * 生成订单编号 TODO。
     *
     * @return String
     */
    String generateOrderCode();

    /**
     * 基础创单--初始化流水对象与流水订单关联对象
     * @param ordPayFlowing
     * @param ordPayFlowingRef
     * @param ordOrder
     */
    void initializationOrdPayFlowing(OrdPayFlowing ordPayFlowing, OrdPayFlowingRef ordPayFlowingRef, OrdOrder ordOrder);

    /**
     * 基础创单--生成配送信息对象
     * @param orderDTO
     * @return
     */
    OrdDelivery createDelivery(OrderDTO orderDTO, OrdOrder ordOrder);

    /**
     * 基础创单--生成开票信息
     * @param orderDTO
     * @return
     */
    OrdBillingInfo createAnInvoice(OrderDTO orderDTO);

    /**
     * 基础创单--根据商品补充订单信息
     * @param orderDTO
     * @param ordOrder
     * @param goodsDTO
     */
    void supplementOrderByGoods(OrderDTO orderDTO,OrdOrder ordOrder,GoodsDTO goodsDTO);

    /**
     * 基础创单--父订单标识与子订单标识组装
     * @param orderDTO
     * @param parentOrdOrder
     * @param ordOrder
     */
    void assemblyParentAndSubToOrder(OrderDTO orderDTO,OrdOrder parentOrdOrder,OrdOrder ordOrder);

    /**
     * 组装订单基础信息
     * @param orderDTO
     * @param ordOrder
     */
    void creadMainOrder(OrderDTO orderDTO,OrdOrder ordOrder);
}
