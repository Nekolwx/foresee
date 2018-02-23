package com.foresee.ftcsp.order.manual.dao;

import java.util.Date;
import java.util.List;

import com.foresee.ftcsp.order.manual.dto.OrdOrderStatusDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderListDTO;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.manual.dto.response.QueryOrderByCusIdResPonseDTO;
@Repository
public interface OrdOrderExtMapper {
    
    /**
     * 根据订单编码查询订单支付信息。
     * @param ordOrderDTO
     * @return OrdOrderDTO
     */
    //@Transactional(propagation=Propagation.NOT_SUPPORTED)
    OrdOrderDTO queryOrderPayInfoByCode(OrdOrderDTO ordOrderDTO);
    
    /**
     * 根据支付流水号查询订单支付信息。
     * @param ordPayFlowingDTO
     * @return OrdOrderDTO
     */
    OrdOrderDTO queryOrderPayInfoByPayNo(OrdPayFlowingDTO ordPayFlowingDTO);
    
    /**
     * 根据orderCode获取orderId
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月19日
     * TODO。
     * @param orderCode
     * @return Long
     */
    Long selectOrderIdByOrderCode(String orderCode);
    
    /**
     * 根据订单ID取消订单，会把其子订单一起取消。
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月19日
     * TODO。
     * @param orderCode
     * @return int
     */
    int cancelOrderAndChildOrderByOrderId(Long orderId);
    
    /**
     * 批量更新订单支付状态。
     * @param payStatus 支付状态
     * @param orderStatus 订单状态
     * @param payFlowingList 支付流水信息
     * @return int
     */
    int bathUpdateOrderPayStatus(@Param(value="payStatus")int payStatus, @Param(value="orderStatus")int orderStatus, @Param(value="payFlowingList")List<OrdPayFlowingDTO> payFlowingList);
    
    /**
     * 根据订单id和来源确定订单是否存在
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月19日
     * TODO。
     * @param orderId
     * @param channel
     * @return int
     */
    int selectOrderByOrderIdAndchannel(@Param("orderId")Long orderId,@Param("channel")Integer channel);
    
    /**
     * 网站分页查看个人订单信息(查询父订单)
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月20日
     * TODO。
     * @param param
     * @return PageQueryResult<OrdOrderDTO>
     */
    PageQueryResult<OrdOrderDTO> queryPersonalOrderPage(PageQueryParam param);
    
    /**
     * 查看个人订单信息（根据父订单查子订单）
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月22日
     * TODO。
     * @param userId
     * @param parentOrderList
     * @return List<OrdOrderDTO>
     */
    List<OrdOrderDTO> queryChildOrder(@Param("userId")String userId,@Param("parentOrderList")List<Long> parentOrderList);

    /**
     * 分页查询开票信息。
     * @param param
     * @return PageQueryResult<OrdOrderListDTO>
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    PageQueryResult<OrdOrderListDTO> queryOrderList(PageQueryParam param);            
    
    /**
     * 根据业务订单号和来源确定订单
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月23日
     * TODO。
     * @param businessOrderNo
     * @param channel
     * @return OrdOrder
     */
    OrdOrder selectByBusinessOrderNoAndChannel(@Param("businessOrderNo")String businessOrderNo,@Param("channel")Integer channel);

    /**
     * 根据skuId，cusId，渠道获取是否有订单
     * @param cusId
     * @param channle
     * @param skuIdList
     * @return
     */
    int selectByCusIdAndSkuAndChannle(@Param("cusId") String cusId,@Param("channle") String channle,@Param("skuIdList") List<Long> skuIdList);
    
    /**
     * 根据skuId，cusId，渠道获取是否有订单状态不是关闭（即订单状态为（0待付款 1待处理 2服务中 3已完成）的订单。
     * @param cusId
     * @param channle
     * @param skuIdList
     * @return int
     */
    int selectByCusIdAndSkuAndChannleExt(@Param("cusId") String cusId,@Param("channle") String channle,@Param("skuIdList") List<Long> skuIdList);

    int updateOrderByCusIdAndChannle(@Param("cusIds")List<String> cusIds,@Param("channle") Integer channle,@Param("orderStatus")Integer orderStatus,@Param("serviceStatus")Integer serviceStatus);

    /**
     * 根据业务订单号和来源查询出唯一的订单
     * @param businessOrderNo
     * @param channel
     * @return OrdOrder
     */
    OrdOrderDTO queryOrderByBusinessNoAndChannel(@Param("businessOrderNo") String businessOrderNo, @Param("channel") String channel);

    /**
     * 改变服务期限已过期的订单状态。
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return int
     */
    int updateOrderStatusTimeOut(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    /**
     * 
     * 根据主键终止订单
     * @param orderId
     * @return int
     */
    int updateOrderStatusByOrderId(@Param("orderId")Long orderId);
    
    
    /**
     * 根据parentId查询订单。
     * @param parentId
     * @return List<OrdOrderDTO>
     */
    List<OrdOrderDTO> selectByParentId(@Param("parentId")Long parentId,@Param("orderTime") Date orderTime);
    
    /**
     * 根据invoiceID更新订单开票状态
     * @param invoiceStatus
     * @param invoiceId
     * @return int
     */

    int updateBillingStatusByBillId(@Param(value = "invoiceStatus") Integer invoiceStatus, @Param(value = "billId") Long billId);

    int updateBillingStatusByInvoiceId(@Param(value = "invoiceStatus") Integer invoiceStatus, @Param(value = "invoiceId") Long invoiceId);

    PageQueryResult<OrdOrderListDTO> queryOrderByServiceProvider(PageQueryParam param);  
    
    /**
     * 仅供任务调用，更改订单状态为已完成。
     * @param orderId
     * @return int
     */
    int updateOrderStatusByTask(@Param("orderId")Long orderId);

    int insertOrderInBatch(@Param("orders")List<OrdOrderDTO> ordOrderDTOS);

    int queryOrderByGoodsCode(@Param("goodsCodes") List<String> goodsCodes,@Param("cusId") String cusId);
    
    List<QueryOrderByCusIdResPonseDTO> queryHavingOrderByCusId(@Param("cusIds")List<String> cusIds,@Param("channle") Integer channle,@Param("goodsId")Integer goodsId);

    /**
     * 根据订单id更换订单状态,支付状态，服务状态，服务时间。
     * @param ordOrderStatusDTO
     * @param orderId
     * @return
     */
    int updateOrderSatusByOrdSatus(@Param("ordOrderStatusDTO")OrdOrderStatusDTO ordOrderStatusDTO,@Param("orderId")Long orderId);

    /**
     * 根据来源，服务开始时间的范围，查询出订单ID，客户ID
     * @param channel
     * @param startDate
     * @param endDate
     * @return
     */
    List<OrdOrder> queryOrderByChannelAndServiceDate(@Param("channel")Integer channel,@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("goodsCodeList")List<String> goodsCodeList);

    /**
     * 根据渠道，客户id和goodsCode进行订单状态和服务状态终止操作
     * @param channel
     * @param customerIdList
     * @param goodsCodeList
     * @return
     */
    int updateTestGoodsOrderToEnd(@Param("channel")Integer channel,@Param("customerIdList")List<String> customerIdList,@Param("goodsCodeList")List<String> goodsCodeList);

    /**
     * 查询服务开始时间在所设定的区间内，并且服务状态为未启动的订单。
     * @param startDate 服务开始时间所在区间的最小值
     * @param endDate 服务开始时间所在区间的最大值
     * @return List<OrdOrder>
     */
    @Select("select * from `t_ord_order` o where o.`service_status` = 0 " +
            "and o.service_start_date > #{startDate,jdbcType=TIMESTAMP} " +
            "and o.service_start_date < #{endDate,jdbcType=TIMESTAMP} " )
    List<OrdOrder> queryOrderByServiceStartDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询服务结束时间在所设定的区间内，并且服务状态为服务中的订单。
     * @param startDate 服务结束时间所在区间的最小值
     * @param endDate 服务结束时间所在区间的最大值
     * @return List<OrdOrder>
     */
    @Select("select * from `t_ord_order` o where o.`service_status` = 2 " +
            "and o.service_end_date > #{startDate,jdbcType=TIMESTAMP} " +
            "and o.service_end_date < #{endDate,jdbcType=TIMESTAMP} " )
    List<OrdOrder> queryOrderByServiceEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}