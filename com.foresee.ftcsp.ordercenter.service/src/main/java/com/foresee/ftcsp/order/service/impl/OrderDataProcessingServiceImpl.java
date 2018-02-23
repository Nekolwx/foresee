package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.manual.restdto.GoodsIdAndSkuIdDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryBriefGoodsForOrder;
import com.foresee.ftcsp.order.auto.dao.OrdOrderMapper;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dto.OrdOrderStatusDTO;
import com.foresee.ftcsp.order.service.IGoodsInnerService;
import com.foresee.ftcsp.order.service.IOrderDataProcessingService;
import com.foresee.ftcsp.order.service.IOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <pre>
 * 订单数据处理实现类。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2017年12月14日
 */

@Service
public class OrderDataProcessingServiceImpl implements IOrderDataProcessingService {
    private Logger LOGGER = Logger.getLogger(this.getClass());

    @Autowired
    private IGoodsInnerService goodsInnerService;

    @Autowired
    private OrdOrderMapper ordOrderMapper;


    /**
     * 创单流程赋予的服务状态和付款状态
     * 根据订单对象的商品信息进行查询
     * 根据查询结果更改订单对象里面的服务状态和付款状态
     * @param ordOrder
     */
    @Override
    public void orderServiceChangeByGoods(OrdOrder ordOrder){
        //判断订单里面的goodsId和skuId是否存在
        if (null==ordOrder.getGoodsId()&&null==ordOrder.getSkuId()){
            throw new FtcspRuntimeException("09030034", (Object) "该订单里面没有skuId和goodsId");
        }
        //查询商品的信息
        QueryBriefGoodsForOrder queryBriefGoodsForOrder = goodsInnerService.queryBriefGoods(ordOrder.getGoodsId(),ordOrder.getSkuId());
        //根据商品的开通方式和价格来进行服务状态和付款状态的修改。
        changeOrderByBriefGoods(ordOrder,queryBriefGoodsForOrder);
    }

    /**
     * 基础创单--根据商品的开通方式和价格来进行服务状态和付款状态，订单状态的修改。
     * @param ordOrder
     * @param queryBriefGoodsForOrder
     */
    public void changeOrderByBriefGoods(OrdOrder ordOrder,QueryBriefGoodsForOrder queryBriefGoodsForOrder){
        //商品的开通方式是按时开通后付款不能用于基础订单创建
        if (OrderConstant.FOUR==queryBriefGoodsForOrder.getOpeningWay()){
            throw new FtcspRuntimeException("09030034", (Object) "该订单里面skuId和goodsId的开通方式是按时开通后付款，不适用于基础订单创建");
        }
        //赋予订单的服务状态和付款状态初始值
        //付款状态待付款
        ordOrder.setPayStatus(OrderConstant.PAY_STATUS_PREPARE_TO_PAY);
        //服务状态未启动
        ordOrder.setServiceStatus(OrderConstant.ZERO);
        //根据开通方式来设定服务状态。
        //除了及时开通的服务状态和订单状态服务中，其它的服务状态是未启动状态和订单状态是待付款。
        if (OrderConstant.ONE == queryBriefGoodsForOrder.getOpeningWay()){
            ordOrder.setServiceStatus(OrderConstant.SERVICE_STATUS_SERVICING);
            ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING);
        }else if (OrderConstant.TWO == queryBriefGoodsForOrder.getOpeningWay()||OrderConstant.FIVES== queryBriefGoodsForOrder.getOpeningWay()){
            //赋予订单的订单状态为待处理
            ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_PREPARE_TO_HANDLE);
        }
        //根据skuId的价格设置付款状态。如果是手动开通不涉及付款状态。
        if (null!=ordOrder.getSkuId()&&null!=queryBriefGoodsForOrder.getPrice()){
            if (0==queryBriefGoodsForOrder.getPrice().compareTo(BigDecimal.ZERO)){
                ordOrder.setPayStatus(OrderConstant.PAY_STATUS_NOT_PAY);
                //0元商品除了付款后人工开通的服务状态是未启动，其它为启动。订单状态也同理
                if (OrderConstant.FIVES != queryBriefGoodsForOrder.getOpeningWay()){
                    ordOrder.setServiceStatus(OrderConstant.SERVICE_STATUS_SERVICING);
                    ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING);
                    ServiceTimeByUnit(ordOrder);
                }
            }
        }
    }

    /**
     * 支付接口回调返回需要插入订单的相关状态
     * @param orderId
     * @return
     */
    @Override
    public OrdOrderStatusDTO afterPaymentToOrdSattus(Long orderId){
        OrdOrderStatusDTO ordOrderStatusDTO = new OrdOrderStatusDTO();
        //查询订单对象
        OrdOrder ordOrder = ordOrderMapper.selectByPrimaryKey(orderId);
        //初始付款状态为已付款,因为是支付完调用
        ordOrder.setPayStatus(OrderConstant.PAY_STATUS_PAID);
        //修改服务时间为空
        ordOrder.setServiceStartDate(null);
        ordOrder.setServiceEndDate(null);
        //拿订单的开通方式,如果开通方式为3、付款后自动开通，对服务状态，订单状态进行修改服务中
        if (OrderConstant.THREE==ordOrder.getOpeningMode()){
            ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING);
            ordOrder.setServiceStatus(OrderConstant.SERVICE_STATUS_SERVICING);
            ServiceTimeByUnit(ordOrder);
        }
        //将订单赋值进去
        BeanUtils.copyProperties(ordOrder,ordOrderStatusDTO);
        return ordOrderStatusDTO;
    }

    /**
     * 根据订单的服务值来计算服务时间。服务开始时间都默认为当前时间。
     * @param ordOrder
     */
    @Override
    public void ServiceTimeByUnit(OrdOrder ordOrder){
        ordOrder.setServiceStartDate(new Date());
        Calendar calendar = new GregorianCalendar();
        if(null!=ordOrder.getServiceTermUnit()&&null!=ordOrder.getServiceTermValue()){
            if (ordOrder.getServiceTermUnit() == OrderConstant.ZERO) {
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, ordOrder.getServiceTermValue());
            } else if (ordOrder.getServiceTermUnit() == OrderConstant.ONE) {
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH, ordOrder.getServiceTermValue());
            } else if (ordOrder.getServiceTermUnit() == OrderConstant.TWO) {
                calendar.setTime(new Date());
                calendar.add(Calendar.YEAR, ordOrder.getServiceTermValue());
            }
            calendar.add(Calendar.DAY_OF_YEAR,-1);
            Date endDate = calendar.getTime();
            ordOrder.setServiceEndDate(endDate);
        }
    }
}
