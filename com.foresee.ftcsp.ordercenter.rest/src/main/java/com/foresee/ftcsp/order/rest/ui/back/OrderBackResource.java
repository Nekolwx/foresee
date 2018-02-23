/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.ui.back;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.restdto.*;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.response.StringResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;

import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.order.service.IMessageProducerService;
import com.foresee.ftcsp.order.service.IOrderDetails;
import com.foresee.ftcsp.order.service.IOrderOperateService;
import com.foresee.ftcsp.order.service.IOrderService;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @date 2017年8月23日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/back/orderService")
public class OrderBackResource {
    private Logger        LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IOrderService orderService;   
    
    @Resource
    private IOrderDetails orderDetails;
    
    @Resource
    private IMessageProducerService messageProducerService;
    
    @Resource
    private IOrderOperateService orderOperateService;
    
    /**
     * 订单分页查询
     * @author chenzexin@foresee.com.cn
     * @date 2017年8月21日
     * @param param
     * @return Object
     */
    @RequestMapping(value="/queryOrderList",method=RequestMethod.POST)
    public Object queryOrderList(@RequestBody PageQueryParam param){
       return orderService.queryOrderList(param);
    }
    
    /**

     * 订单新增，未完成
     * @author chenzexin@foresee.com.cn
     * @date 2017年8月24日
     * @param addOrdOrderDTO
     * @return Object
     */
    @RequestMapping(value="/AddOrdOrder",method=RequestMethod.POST)
    public Object addOrdOrder(@Var @Valid AddOrdOrderDTO addOrdOrderDTO){
        Long orderId=orderService.addOrdOrder(addOrdOrderDTO);
        if(orderId==0 || orderId==null) return  new RestResponse("1","新增订单失败");
        RestResponse response=new RestResponse("0","新增订单成功");
        response.setBody(orderId);
         return  response;
    }
    
    /**
     * 订单收款新增
     * @author chenzexin@foresee.com.cn
     * @date 2017年8月24日
     * @param addOrdOrdeReceivablesrDTO
     * @return Object
     */
    @RequestMapping(value="/AddOrdOrdeReceivablesr",method=RequestMethod.POST)
    public Object addOrdOrdeReceivablesr(@Var @Valid AddOrdOrdeReceivablesrDTO addOrdOrdeReceivablesrDTO){
        boolean flag=orderService.addOrdOrdeReceivablesr(addOrdOrdeReceivablesrDTO);
        if(flag) return new RestResponse("0","新增收款成功");
        return new RestResponse("1","新增收款失败");
    }
    
    /**
     * 订单状态修改
     * @author chenzexin@foresee.com.cn
     * @date 2017年8月24日
     * @param updateOrderStatusDTO
     * @return Object
     */
    @RequestMapping(value="/updateOrderStatus",method=RequestMethod.POST)
    public Object updateOrderStatus(@Var @Valid UpdateOrderStatusDTO updateOrderStatusDTO){
        boolean flag=orderService.updateOrderStatus(updateOrderStatusDTO);
        if(flag) return new RestResponse("0","订单状态修改成功");
        return new RestResponse("1","订单状态修改失败");
    }
    
     /**
     * 根据子订单Id查询订单详情
     * @author weichunlei@foresee.com.cn
     * @date 2017年8月21日
     * TODO。
     * @param queryOrderDetailsDTO
     * @return Object
     */
    @RequestMapping(value = "queryOrderDetails", method = RequestMethod.POST)
    public Object queryOrderDetails(@Var @Valid QueryOrderDetailsDTO queryOrderDetailsDTO){
    	
    	return orderDetails.searchOrderDetails(queryOrderDetailsDTO);
    	    	
    }
    
    /**
	 * 子母订单聚合查询
	 * 
	 * @author weichunlei@foresee.com.cn
	 * @date 2017年8月23日
	 * @param queryOrderParentAndChildDTO
	 * @return Object
	 */
    @RequestMapping(value = "queryOrderParentAndChild", method = RequestMethod.POST)
	public Object queryOrderParentAndChild(@Var @Valid QueryOrderParentAndChildDTO queryOrderParentAndChildDTO) {
		
		return orderDetails.queryOrderParentAndChild(queryOrderParentAndChildDTO);
	}
    
    /**

     * 平台创建订单
     * @author mowentao@foresee.com.cn
     * @date 2017年9月06日
     * @param OrderDTO
     * @return Object
     */
    @RequestMapping(value="/addOrder",method=RequestMethod.POST)
    public Object addOrder(@Var @Valid OrderDTO orderDTO){
     	 LOGGER.info("back/orderService/addOrder" + OrderDTO.class.toString());
     	 if(!StringUtils.isEmpty(orderDTO.getContactNumber())){
     		orderDTO.setReceiverPhone(orderDTO.getContactNumber());
     	 }
         if (StringUtils.isEmpty(orderDTO.getCusId())) {
             return new RestResponse("1", "创建失败,cusId不能为空!");
         }
         if (StringUtils.isEmpty(orderDTO.getBuyerType())) {
             return new RestResponse("1", "创建失败,buyerType不能为空!");
         }
         if(StringUtils.isEmpty(orderDTO.getGoodsGroup())){
             return new RestResponse("1", "创建失败,goodsGroup不能为空!");
         }
        RepCreateOrderDTO repCreateOrderDTO = orderService.createBasisOrder(orderDTO);
        if (null==repCreateOrderDTO) {
            return new RestResponse("1", "创建失败");
        } else {
            RestResponse response = new RestResponse("0", "创建成功");
            response.setBody(repCreateOrderDTO);
            return response;
        }
    }
    
    /**
     * 终止订单
     * @author huanghaiyuan@foresee.com.cn
     * @date 2017年11月13日
     * @param terminateOrderDTO
     * @return Object
     */
    @RequestMapping(value="/orderTerminate",method=RequestMethod.POST)
    public Object orderTerminate(@Var @Valid TerminateOrderDTO terminateOrderDTO){
        boolean flag=orderService.terminateOrderStatus(terminateOrderDTO);
        if(flag){ 
            return new RestResponse("0","终止订单成功");
        }
        return new RestResponse("1","终止订单失败");
    }
    
    /**
     * 订单开通服务
     * @author huangzekun@foresee.com.cn
     * @date 2017年11月13日
     * @param openServiceDTO
     * @return RestResponse<StringResponse>
     */
    @RequestMapping(value="/openService",method=RequestMethod.POST)
    public RestResponse<StringResponse> openService(@Var @Valid OpenServiceDTO openServiceDTO){
        orderOperateService.openService(openServiceDTO.getOrderId());
        messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_ENABLED,openServiceDTO.getOrderId());
        return RestResponse.create("message", "开通服务成功");
    }
    
    /**
     * 订单关闭服务
     * @author huangzekun@foresee.com.cn
     * @date 2017年11月13日
     * @param closeServiceDTO
     * @return RestResponse<StringResponse>
     */
    @RequestMapping(value="/closeService",method=RequestMethod.POST)
    public RestResponse<StringResponse> closeService(@Var @Valid CloseServiceDTO closeServiceDTO){
        orderOperateService.closeService(closeServiceDTO.getOrderId());
        messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_DISABLED,closeServiceDTO.getOrderId());
        return RestResponse.create("message", "关闭服务成功");
    }
    

    /**
     * 终止订单功能。
     * @author huangzekun@foresee.com.cn
     * @date 2018年1月2日
     * @param terminateOrderDTO
     * @return RestResponse<StringResponse>
     */
    @RequestMapping(value="/terminateOrder",method=RequestMethod.POST)
    public RestResponse<StringResponse> terminateOrder(@Var @Valid TerminateOrderDTO terminateOrderDTO){
        orderOperateService.terminateOrder(terminateOrderDTO.getOrderId());
        return RestResponse.create("message", "终止订单成功");
    }
    
    /**
     * 取消订单功能。
     * @author huangzekun@foresee.com.cn
     * @date 2018年1月2日
     * @param terminateOrderDTO
     * @return RestResponse<StringResponse>
     */
    @RequestMapping(value="/cancelOrder",method=RequestMethod.POST)
    public RestResponse<StringResponse> cancelOrder(@Var @Valid BackCancelOrderDTO backCancelOrderDTO){
        orderOperateService.cancelOrder(backCancelOrderDTO.getOrderId());
        return RestResponse.create("message", "取消订单成功");
    }
    
    

    /**
     * 服务商订单查看
     * @param param
     * @return
     */
    @RequestMapping(value="/queryOrderByServiceManager",method=RequestMethod.POST)
    public Object queryOrderByServiceManager(@RequestBody PageQueryParam param){
        param.put("ownerId",param.getString("depId"));
        return orderService.queryOrderByServiceProvider(param);
    }

    /**
     * 服务人员订单查看
     * @param param
     * @return
     */
    @RequestMapping(value="/queryOrderByServiceProvider",method=RequestMethod.POST)
    public Object queryOrderByServiceProvider(@RequestBody PageQueryParam param){
        param.put("ownerId", param.getString("SESSION_USER_ID"));
        return orderService.queryOrderByServiceProvider(param);
    }


}
