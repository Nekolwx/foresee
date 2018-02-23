/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service;

import java.util.List;
import java.util.Map;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.common.core.rest.RestRequestParam;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderListDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderStatusDTO;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.manual.dto.response.QueryOrderByCusIdResPonseDTO;
import com.foresee.ftcsp.order.manual.restdto.*;
import com.foresee.ftcsp.ordercenter.api.dto.ChangOrderStatusByCusIdAndChannelDTO;
import com.foresee.ftcsp.ordercenter.api.dto.UpdateOrderStatusByTaskDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.ordercenter.api.dto.QueryOrderByCusIdDTO;

/**
 * <pre>
 * 支付接口。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IOrderService {

	/**
	 * 根据订单编码查询订单支付信息。
	 * 
	 * @param ordOrderDTO
	 * @return OrdOrderDTO
	 */
	OrdOrderDTO queryOrderPayInfoByCode(OrdOrderDTO ordOrderDTO);
	
	/**
	 * 根据支付流水号查询订单支付信息。
	 * @param ordPayFlowingDTO
	 * @return OrdOrderDTO
	 */
	OrdOrderDTO queryOrderPayInfoByPayNo(OrdPayFlowingDTO ordPayFlowingDTO);

	/**
	 * 创建订单 TODO。
	 * 
	 * @return Map<String,Ojbect>
	 */
	public Map<String, Object> createOrder(OrderDTO orderDTO);

	/**
	 * 根据orderCode获取orderId
	 * 
	 * @author linliangwei@foresee.com.cn
	 * @date 2017年8月19日 TODO。
	 * @param orderCode
	 * @return Long
	 */
	Long selectOrderIdByOrderCode(String orderCode);

	/**
	 * 根据订单ID取消订单，会把其子订单一起取消。
	 * 
	 * @author linliangwei@foresee.com.cn
	 * @date 2017年8月19日 TODO。
	 * @param orderId
	 * @return int
	 */
	int cancelOrderAndChildOrderByOrderId(Long orderId);

	/**
	 * 是否成功的根据orderCode取消订单和把其子订单一起取消。
	 * 
	 * @author linliangwei@foresee.com.cn
	 * @date 2017年8月19日 TODO。
	 * @param orderCode
	 * @return boolean
	 */
	boolean isCancelOrder(String orderCode);

	/**
	 * 批量更新支付状态。
	 * 
	 * @author keshijun@foresee.com.cn
	 * @param orderList
	 * @return int
	 */
	int bathUpdateOrderPayStatus(int payStatus, int orderStatus, List<OrdPayFlowingDTO> payFlowingList);

	/**
	 * 代账系统修改订单
	 * 
	 * @author linliangwei@foresee.com.cn
	 * @date 2017年8月19日 TODO。
	 * @param updateOrderInfoDTO
	 * @return Map<String,Object>
	 */
	Map<String, Object> updateOrderForProxy(UpdateOrderInfoDTO updateOrderInfoDTO);

	/**
	 * 代账系统设置订单的会计主管、助理扩展信息
	 * 
	 * @author linliangwei@foresee.com.cn
	 * @date 2017年8月20日 TODO。
	 * @param setAccountantInfoDTO
	 * @return Map<String,Object>
	 */
	Map<String, Object> setAccountantInfo(SetAccountantInfoDTO setAccountantInfoDTO);

	/**
	 * 网站分页查看个人订单信息
	 * 
	 * @author linliangwei@foresee.com.cn
	 * @date 2017年8月20日 TODO。
	 * @param queryPersonalOrderPageDTO
	 * @return List<OrdOrderDTO>
	 */
	Object queryPersonalOrderPage(PageQueryParam param);

	/**
	 * 分页查询订单信息。
	 * 
	 * @author chenzexin@foresee.com.cn
	 * @date 2017年8月22日
	 * @param param
	 * @return PageQueryResult<OrdOrderDTO>
	 */
	PageQueryResult<OrdOrderListDTO> queryOrderList(PageQueryParam param);

	/**
	 * 新增订单信息,成功返回订单id
	 * 
	 * @author chenzexin@foresee.com.cn
	 * @date 2017年8月23日
	 * @param addOrdOrderDTO
	 * @return long
	 */
	long addOrdOrder(AddOrdOrderDTO addOrdOrderDTO);

	/**
	 * 修改订单状态信息,成功返回true
	 * 
	 * @author chenzexin@foresee.com.cn
	 * @date 2017年8月24日
	 * @param updateOrderStatusDTO
	 * @return boolean
	 */
	boolean updateOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO);

	/**
	 * 新增收款信息,成功返回true
	 * 
	 * @author chenzexin@foresee.com.cn
	 * @date 2017年8月24日
	 * @param addOrdOrdeReceivablesrDTO
	 * @return boolean
	 */
	boolean addOrdOrdeReceivablesr(AddOrdOrdeReceivablesrDTO addOrdOrdeReceivablesrDTO);

	/**
	 * 建立打赏订单，返回订单编号
	 * @param createRewardOrderDTO
	 * @return String
	 */
    Map<String,Object> insertRewardOrder(CreateRewardOrderDTO createRewardOrderDTO);


	/**
	 * 终止一个来源的多个cusId的所有订单。
	 * @param cusIds
	 * @param channle
	 * @param orderStatus
	 * @return
	 */
    boolean updateOrderByCusIdAndChannle(ChangOrderStatusByCusIdAndChannelDTO changOrderStatusByCusIdAndChannelDTO);
    
    /**
     * 异常创建订单接口。
     * @param map void
     */
    public void createOrderAsynchronism(Map<String,String> map);

	/**
	 * 根据来源和业务订单号查询唯一的订单
	 * @param queryOrderDTO
	 * @return OrdOrderDTO
	 */
	OrdOrderDTO queryOrderByBusinessNoAndChannel(QueryOrderDTO queryOrderDTO);
	
	/**
	 * 旧平台订单迁移接口。
	 * @param synchronizedOrderList
	 * @return Map<String,Object>
	 */
	Map<String,Object> createSynchronizedOrder(List<SynchronizedOrderDTO> synchronizedOrderList);
	
	/**
	 * 改变服务期限已过期的订单状态。
	 * @return int
	 */
	int updateOrderStatusTimeOut();
	
	/**
	 * 终止订单
	 * @return boolean
	 */
	public boolean terminateOrderStatus(TerminateOrderDTO terminateOrderDTO);
	
	/**
	 * 订单开通服务。
	 * @param orderId
	 * @return boolean
	 */
	public boolean openService(Long orderId);
	
	/**
	 * 订单关闭服务。
	 * @param orderId
	 * @return boolean
	 */
	public boolean closeService(Long orderId);
	
	/**
	 * 终止订单。
	 * @param orderId
	 * @return boolean
	 */
	public boolean terminateOrder(Long orderId);
	
	/**
	 * 取消订单。
	 * @param orderId
	 * @return boolean
	 */
	public boolean cancelOrder(Long orderId);
	
	


	/**
	 * 订单查看(服务商)
	 * @param param
	 * @return
	 */
	PageQueryResult<OrdOrderListDTO> queryOrderByServiceProvider(PageQueryParam param);

	/**
	 * 仅供任务调用，更改订单状态为已完成
	 * @param updateOrderStatusByTaskDTO
	 * @return
	 */
	boolean updateOrderStatusByTask(UpdateOrderStatusByTaskDTO updateOrderStatusByTaskDTO);

	List<QueryOrderByCusIdResPonseDTO> queryHavingOrderByCusId(QueryOrderByCusIdDTO queryOrderByCusIdDTO);

	/**
	 * 基础创单接口。
	 * @param orderDTO
	 * @return
	 */
	RepCreateOrderDTO createBasisOrder(OrderDTO orderDTO);

	/**
	 * 根据订单id更换订单状态,支付状态，服务状态，服务时间。
	 * @param ordOrderStatusDTO
	 * @param orderId
	 * @return
	 */
	int updateOrderSatusByOrdSatus(OrdOrderStatusDTO ordOrderStatusDTO, Long orderId);
}
