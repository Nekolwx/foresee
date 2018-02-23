/*
* Copyright（c） Foresee Science & Technology Ltd.
*/

package com.foresee.ftcsp.ordercenter.api.inner;

import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.common.core.page.PageQueryResultDefaultImpl;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.api.annotation.FtcspRestApi;
import com.foresee.ftcsp.common.core.rest.response.StringResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.ordercenter.api.dto.*;
import com.foresee.ftcsp.ordercenter.url.InnerURL;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwenlong@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月28日
 */
@FtcspRestApi("ordercenter")
public interface OrdercenterApi {
    /**
     * 给予用户接口，终止一个来源的多个cusId的所有订单。
     * @param changOrderStatusByCusIdAndChannelDTO
     * @return
     */
    @RequestMapping(value = InnerURL.Order.changOrderStatusByCusIdAndChannel, method = RequestMethod.POST)
    RestResponse changOrderStatusByCusIdAndChannel(@Var @Valid ChangOrderStatusByCusIdAndChannelDTO changOrderStatusByCusIdAndChannelDTO);

    /**
     * 内部创单接口，办税助手
     * @param orderDTO
     * @return
     */
    @RequestMapping(value = InnerURL.Order.createOrder, method = RequestMethod.POST)
    RestResponse createOrder(@Var @Valid OrderDTO orderDTO);

    /**
     * 仅供任务调用，更改订单状态为已完成
     * @param UpdateOrderStatusByTaskDTO
     * @return
     */
    @RequestMapping(value = InnerURL.Order.updateOrderStatusByTask, method = RequestMethod.POST)
    RestResponse updateOrderStatusByTask(@Var @Valid UpdateOrderStatusByTaskDTO updateOrderStatusByTaskDTO);

    /**
     * 订单开通服务接口。
     * @param openServiceDTO
     * @return RestResponse
     */
    @RequestMapping(value = InnerURL.Order.openService, method = RequestMethod.POST)
    RestResponse<StringResponse> openService(@Var @Valid OpenServiceDTO openServiceDTO);

    /**
     * 订单关闭服务接口。
     * @param CloseServiceDTO
     * @return RestResponse
     */
    @RequestMapping(value = InnerURL.Order.closeService, method = RequestMethod.POST)
    RestResponse<StringResponse> closeService(@Var @Valid CloseServiceDTO closeServiceDTO);
    /**
     * 过滤拥有订单的客户
     * @param QueryOrderByCusIdDTO
     * @return
     */
    @RequestMapping(value = InnerURL.Order.queryHavingOrderByCusId, method = RequestMethod.POST)
    RestResponse queryHavingOrderByCusId(@Var @Valid QueryOrderByCusIdDTO queryOrderByCusIdDTO);

    /**
     * 分页查询计费订单列表
     * @param param
     * @return
     */
    @RequestMapping(value = InnerURL.Order.queryChargingOrderList,method = RequestMethod.POST)
    RestResponse<PageQueryResultDefaultImpl> queryChargingOrderList(QueryChargingListDTO queryChargingListDTO);

    /**
     * 划扣更新订单信息的接口
     * @param updateOrderInfo
     * @return
     */
    @RequestMapping(value = InnerURL.Order.updateOrderInfo, method = RequestMethod.POST)
    RestResponse updateOrderInfo(@Var @Valid UpdateOrderByDeductionsSuccess updateOrderInfo);

    /**
     * 根据渠道和时间，终止其相关联的试用商品的订单。
     * @param updateTestGoodsOrderToEndDTO
     * @return
     */
    @RequestMapping(value = InnerURL.Order.updateTestGoodsOrderToEnd, method = RequestMethod.POST)
    RestResponse updateTestGoodsOrderToEnd(@Var @Valid UpdateTestGoodsOrderToEndDTO updateTestGoodsOrderToEndDTO);

    /**
     * 订单服务时间到点开通服务。
     */
    @RequestMapping(value = InnerURL.Order.timingOpenService,method = RequestMethod.POST)
    RestResponse timingOpenService();

    /**
     * 订单到期关闭服务。
     */
    @RequestMapping(value = InnerURL.Order.timingCloseService,method = RequestMethod.POST)
    RestResponse timingCloseService();

}
