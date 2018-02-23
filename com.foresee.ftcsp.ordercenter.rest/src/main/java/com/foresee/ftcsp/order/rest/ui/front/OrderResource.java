/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.ui.front;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

import com.foresee.ftcsp.order.manual.restdto.RepCreateOrderDTO;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.manual.restdto.CancelOrderDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.order.service.IOrderService;

/**
 * <pre>
 * 订单前端控制器。
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
@RestController("/front/orderService")
@RequestMapping("/front/orderService")
public class OrderResource {

    private Logger        LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IOrderService orderService;

    /**
     * 创建订单 TODO。
     * @return Object
     */
    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public Object createOrder(@Var @Valid OrderDTO orderDTO) {
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

    /**
     * 网站取消订单
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月20日 TODO。
     * @param cancelOrderDTO
     * @return Object
     */
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    public Object cancelOrder(@Var @Valid CancelOrderDTO cancelOrderDTO) {
        boolean result = orderService.isCancelOrder(cancelOrderDTO.getOrderCode());
        Map<String, Object> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("errorCode", "0");
            resultMap.put("errorMsg", "订单取消成功");
        } else {
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg", "订单取消不成功");
        }
        return result;
    }

    /**
     * 查看个人订单详情
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月22日
     * TODO。
     * @param param
     * @return Object
     */
    @RequestMapping(value = "queryPersonalOrderPage", method = RequestMethod.POST)
    public Object queryPersonalOrderPage(@RequestBody PageQueryParam param){
        return orderService.queryPersonalOrderPage(param);
    }
    
}
