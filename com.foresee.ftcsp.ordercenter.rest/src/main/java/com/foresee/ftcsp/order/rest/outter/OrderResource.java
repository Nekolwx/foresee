/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.outter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

import com.foresee.ftcsp.common.core.rest.FtcspRequestContextHolder;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.restdto.*;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.order.service.IOrderService;

/**
 * <pre>
 * 订单外部接口控制器。
 * </pre>
 *
 * @author huangzhao@foresee.com.cn
 * @date 2017年8月19日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@RestController("/outter/orderService")
@RequestMapping("/outter/orderService")
public class OrderResource {

    private Logger        LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IOrderService orderService;


    /**
     * 外部接口创建订单 TODO。
     * @return Object
     */
    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public Object createOrder(@Var @Valid OrderDTO orderDTO) {
        LOGGER.info("order/outter/orderService/createOrder" + OrderDTO.class.toString());
        if (StringUtils.isEmpty(orderDTO.getCusId())) {
            return new RestResponse("1", "创建失败,cusId不能为空!");
        }
        if(StringUtils.isEmpty(orderDTO.getGoodsGroup())){
            return new RestResponse("1", "创建失败,goodsGroup不能为空!");
        }
        if (StringUtils.isEmpty(orderDTO.getBusinessOrderNo())) {
            return new RestResponse("1", "创建失败,businessOrderNo不能为空!");
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
     * 代账系统修改订单
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月20日
     * @param updateOrderInfoDTO
     * @return Object
     */
    @RequestMapping(value = "updateOrderInfo", method = RequestMethod.POST)
    public Object updateOrderInfo(@Var @Valid UpdateOrderInfoDTO updateOrderInfoDTO) {
        Map<String, Object> resultMap = orderService.updateOrderForProxy(updateOrderInfoDTO);
        if(resultMap.containsKey("errorCode")){
            return new RestResponse(resultMap.get("errorCode").toString(), resultMap.get("errorMsg").toString());
        }
        return resultMap;
    }

    /**
     * 代账系统设置订单的会计主管、助理扩展信息
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月20日 TODO。
     * @param setAccountantInfoDTO
     * @return Object
     */
    @RequestMapping(value = "setAccountantInfo", method = RequestMethod.POST)
    public Object setAccountantInfo(@Var @Valid SetAccountantInfoDTO setAccountantInfoDTO) {
        Map<String, Object> resultMap = orderService.setAccountantInfo(setAccountantInfoDTO);
        if(resultMap.containsKey("errorCode")){
            return new RestResponse(resultMap.get("errorCode").toString(), resultMap.get("errorMsg").toString());
        }
        return resultMap;
    }

    /**
     * 创建打赏订单；
     * @param createRewardOrderDTO
     * @return Object
     */
    @RequestMapping(value = "createRewardOrder" ,method = RequestMethod.POST)
    public Object createRewardOrder(@Var @Valid CreateRewardOrderDTO createRewardOrderDTO){
        Map<String,Object> result = orderService.insertRewardOrder(createRewardOrderDTO);
        RestResponse response = new RestResponse((String)result.get("errorCode"),(String)result.get("errorMsg"));
        if ("1".equals(response.getHead().getErrorCode())) {
            return response;
        }
        response.setBody(Collections.singletonMap("payPageUrl",result.get("payPageUrl")));
        return response;
    }

    @RequestMapping(value = "queryOrder", method = RequestMethod.POST)
    public Object queryOrder(@Var @Valid QueryOrderDTO queryOrderDTO) {
        OrdOrderDTO ordOrderDTO = orderService.queryOrderByBusinessNoAndChannel(queryOrderDTO);
        if (ordOrderDTO == null){
            return RestResponse.error("1","没有该订单信息");
        }
        Map<String,Object> resultMap = new HashMap();
        resultMap.put("orderCode",ordOrderDTO.getOrderCode());
        resultMap.put("orderStatue",ordOrderDTO.getOrderStatus());
        resultMap.put("billingStatus", ordOrderDTO.getBillingStatus());
        resultMap.put("payStatue", ordOrderDTO.getPayStatus());
        resultMap.put("payAmount", ordOrderDTO.getPayAmount().toString());
        resultMap.put("orderUser", ordOrderDTO.getOrderUser());
        resultMap.put("goodsId", ordOrderDTO.getGoodsId());
        resultMap.put("goodsSkuId",ordOrderDTO.getSkuId());
        resultMap.put("payWay",ordOrderDTO.getPayWay());
        return resultMap;
    }
    
    @RequestMapping(value = "createSynchronizedOrder", method = RequestMethod.POST)
    public Object createSynchronizedOrder(@Var @Valid @NotEmpty List<SynchronizedOrderDTO> synchronizedOrderList){
        Map<String,Object> result = orderService.createSynchronizedOrder(synchronizedOrderList);
        RestResponse response = new RestResponse((String)result.get("errorCode"),(String)result.get("errorMsg"));
        if(!"0".equals(response.getHead().getErrorCode())){
            HashMap<String, Object> responseBody = new HashMap<String,Object>();
            responseBody.put("orderCodeErrorMap",result.get("orderCodeErrorMap"));
            responseBody.put("orderCodeSuccessList", result.get("orderCodeSuccessList"));
            response.setBody(responseBody);
        }
        return response;
    }
    
    


}
