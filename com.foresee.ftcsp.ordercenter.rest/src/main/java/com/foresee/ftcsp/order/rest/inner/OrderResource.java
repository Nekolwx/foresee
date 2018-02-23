/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.inner;

import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.common.core.page.PageQueryResultDefaultImpl;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.RestResponseHead;
import com.foresee.ftcsp.common.core.rest.response.StringResponse;
import com.foresee.ftcsp.common.core.util.Jackson;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dto.response.CommonResPonseDTO;
import com.foresee.ftcsp.order.manual.dto.response.QueryOrderByCusIdResPonseDTO;
import com.foresee.ftcsp.order.manual.restdto.RepCreateOrderDTO;
import com.foresee.ftcsp.order.service.*;
import com.foresee.ftcsp.ordercenter.api.dto.*;
import com.foresee.ftcsp.ordercenter.api.inner.OrdercenterApi;
import com.foresee.ftcsp.ordercenter.url.InnerURL;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * <pre>
 * 订单内部接口控制器。
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
@RestController
public class OrderResource implements OrdercenterApi {

    private Logger        LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IOrderService orderService;

    @Resource
    private IOrderOperateService orderOperateService;

    @Resource
    private IMessageProducerService messageProducerService;

    @Resource
    private IOperateOrderService operateOrderService;

    @Resource
    private IQueryOrderInnerService queryOrderInnerService;

    /**
     * 创建订单 TODO。
     * @return Object
     */
    public RestResponse createOrder(@Var @Valid OrderDTO orderDTO) {
        LOGGER.info("order/front/orderService/createOrder" + OrderDTO.class.toString());
        if (StringUtils.isEmpty(orderDTO.getUserId())) {
            return new RestResponse("1", "创建失败,userId不能为空!");
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

    public RestResponse<?> changOrderStatusByCusIdAndChannel(@Var @Valid ChangOrderStatusByCusIdAndChannelDTO changOrderStatusByCusIdAndChannelDTO){
        boolean result = orderService.updateOrderByCusIdAndChannle(changOrderStatusByCusIdAndChannelDTO);
        if (result){
            return RestResponse.successMessage("更新成功");
        }else {
            return RestResponse.error("1","更新失败或没订单可更新");
        }
    }

    /**
     * 仅供任务调用，更改订单状态为已完成
     * @param updateOrderStatusByTaskDTO
     * @return
     */
    public RestResponse<?> updateOrderStatusByTask(@Var @Valid UpdateOrderStatusByTaskDTO updateOrderStatusByTaskDTO){
        boolean result = orderService.updateOrderStatusByTask(updateOrderStatusByTaskDTO);
        if (result){
            return RestResponse.successMessage("更新成功");
        }else {
            return RestResponse.error("1","更新失败或没订单可更新");
        }
    }

    /**
     * 过滤拥有订单的客户
     * @param queryOrderByCusIdDTO
     * @return
     */
    public RestResponse queryHavingOrderByCusId(@Var @Valid QueryOrderByCusIdDTO queryOrderByCusIdDTO){
        if(queryOrderByCusIdDTO.getCusIds()==null || queryOrderByCusIdDTO.getCusIds().size()==0){
            return RestResponse.error("1","参数cusIds不能为空");
        }else {
            List<QueryOrderByCusIdResPonseDTO> queryOrderByCusIdResPonseDTO = orderService.queryHavingOrderByCusId(queryOrderByCusIdDTO);

            return RestResponse.successData(queryOrderByCusIdResPonseDTO);
        }
    }


    @Override
    public RestResponse<StringResponse> openService(@Var @Valid OpenServiceDTO openServiceDTO) {
        orderOperateService.openService(openServiceDTO.getOrderId());
        messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_ENABLED,openServiceDTO.getOrderId());
        return RestResponse.create("message", "开通服务成功");
    }

    @Override
    public RestResponse<StringResponse> closeService(@Var @Valid CloseServiceDTO closeServiceDTO) {
        orderOperateService.closeService(closeServiceDTO.getOrderId());
        messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_DISABLED,closeServiceDTO.getOrderId());
        return RestResponse.create("message", "关闭服务成功");
    }

    @Override
    public RestResponse<PageQueryResultDefaultImpl> queryChargingOrderList(@Var @Valid QueryChargingListDTO queryChargingListDTO) {
        PageQueryParam param = new PageQueryParam();
        param.setPageIndex(queryChargingListDTO.getPageIndex());
        param.setPageSize(queryChargingListDTO.getPageSize());
        param.put("cusId", queryChargingListDTO.getCustomerId());
        param.put("orderId", queryChargingListDTO.getOrderId());
        param.put("chargingTime", new Date());
        if (!Objects.isNull(queryChargingListDTO.getServiceEndDate())){
            Long serviceEndTime = queryChargingListDTO.getServiceEndDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(serviceEndTime));
            String serviceEndDateStart = LocalDateUtil.getDateStr(LocalDateUtil.Y_M_D, calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            String serviceEndDateEnd = LocalDateUtil.getDateStr(LocalDateUtil.Y_M_D, calendar.getTime());
            param.put("serviceEndDateStart",serviceEndDateStart);
            param.put("serviceEndDateEnd",serviceEndDateEnd);
            param.remove("serviceEndDate");
        }
        RestResponse restResponse = new RestResponse("0","查询成功");
        restResponse.setBody(orderService.queryOrderList(param));
        return restResponse;
    }

    @Override
    public RestResponse updateOrderInfo(@Var @Valid UpdateOrderByDeductionsSuccess updateOrderInfo) {
        CommonResPonseDTO commonResPonseDTO = operateOrderService.updateOrderByDeductions(updateOrderInfo);
        RestResponse restResponse = new RestResponse();
        //restResponse.setBody(commonResPonseDTO);
        if(commonResPonseDTO.isFlag()){
            return RestResponse.successMessage(commonResPonseDTO.getMessage());
        }else{
            return RestResponse.error("1",commonResPonseDTO.getMessage());
        }

    }

    @Override
    public RestResponse updateTestGoodsOrderToEnd(@Var @Valid UpdateTestGoodsOrderToEndDTO updateTestGoodsOrderToEndDTO) {
        Integer result = queryOrderInnerService.updateTestGoodsOrderToEnd(updateTestGoodsOrderToEndDTO);
        return RestResponse.successMessage("终止的试用商品订单条数为："+result);
    }

    @Override
    public RestResponse timingOpenService() {
        orderOperateService.timingOpenService();
        return RestResponse.successMessage("请求服务时间到点开通服务内部接口成功");
    }

    @Override
    public RestResponse timingCloseService() {
        orderOperateService.timingCloseService();
        return RestResponse.successMessage("请求订单到期关闭服务内部接口成功");
    }
}
