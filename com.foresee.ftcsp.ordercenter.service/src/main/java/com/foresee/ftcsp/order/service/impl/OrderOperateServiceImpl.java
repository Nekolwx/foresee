/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.service.IMessageProducerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.order.auto.dao.OrdOrderMapper;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.service.IOrderDataProcessingService;
import com.foresee.ftcsp.order.service.IOrderOperateService;


/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author foresee@foresee.com.cn
 * @date 2018年1月22日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional
public class OrderOperateServiceImpl implements IOrderOperateService {
    
    private Logger LOGGER = Logger.getLogger(this.getClass());

    @Autowired
    private OrdOrderMapper ordOrderMapper;

    @Autowired
    private OrdOrderExtMapper ordOrderExtMapper;
    
    @Autowired
    private IOrderDataProcessingService orderDataProcessingService;

    @Autowired
    private IMessageProducerService messageProducerService;
    
    @Override
    public boolean openService(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：所传的orderId对应的订单不存在");
        }
        //开通方式:人工开通后付款
        if(2 == order.getOpeningMode()){
            if(order.getOrderStatus()==1&&order.getServiceStatus()==0&&order.getPayStatus()==1){
                changeStatus(0,2,null,order,true);
                return true;
            }
            if(order.getOrderStatus()==6&&order.getServiceStatus()==6&&(order.getPayStatus()==0||order.getPayStatus()==4)){
                changeStatus(2,2,null,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：此订单不能进行开通操作，请检查状态");
            }
        }
        //开通方式:付款后人工开通
        if(5 == order.getOpeningMode()){
            if(order.getOrderStatus()==1&&order.getServiceStatus()==0&&(order.getPayStatus()==0||order.getPayStatus()==4)){
                changeStatus(2,2,null,order,true);
                return true;
            }
            if(order.getOrderStatus()==6&&order.getServiceStatus()==6&&(order.getPayStatus()==0||order.getPayStatus()==4)){
                changeStatus(2,2,null,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：此订单不能进行开通操作，请检查状态");
            }
        }
        //开通方式:按时开通后付款
        if(4 == order.getOpeningMode()){
            if(order.getOrderStatus()==6&&order.getServiceStatus()==6){
                changeStatus(2,2,null,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：此订单不能进行开通操作，请检查状态");
            }
        }
        //其他暂时不支持开通服务
        throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：此订单不能进行开通操作，请检查状态");
        

    }

    @Override
    public boolean closeService(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "关闭服务失败：所传的orderId对应的订单不存在");
        }
        //开通方式:人工开通后付款 或 付款后人工开通
        if(2 == order.getOpeningMode() || 5 == order.getOpeningMode()){
            if(order.getOrderStatus()==2&&order.getServiceStatus()==2&&(order.getPayStatus()==0||order.getPayStatus()==4)){
                changeStatus(6,6,null,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "关闭服务失败：此订单不能进行关闭操作，请检查状态");
            }
        }
        //开通方式:按时开通后付款
        if(4 == order.getOpeningMode()){
            if(order.getOrderStatus()==2&&order.getServiceStatus()==2){
                changeStatus(6,6,null,order,false);
                return true;
            }
        }
        //其他暂时不支持关闭服务
        throw new FtcspRuntimeException("09030034", (Object) "关闭服务失败：此订单不能进行关闭操作，请检查状态");
    }

    @Override
    public boolean terminateOrder(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：所传的orderId对应的订单不存在");
        }
        //开通方式:人工开通后付款
        if(2 == order.getOpeningMode()){
            if((order.getOrderStatus()==2&&order.getServiceStatus()==2&&order.getPayStatus()==4)||
                    (order.getOrderStatus()==6&&order.getServiceStatus()==6&&order.getPayStatus()==4)){
                changeStatus(5,4,4,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：此订单不能进行终止操作，请检查状态");
            }
        }
        //开通方式:付款后人工开通
        if(5 == order.getOpeningMode()){
            if((order.getOrderStatus()==2&&order.getServiceStatus()==2&&order.getPayStatus()==4)||
                    (order.getOrderStatus()==6&&order.getServiceStatus()==6&&order.getPayStatus()==4)||
                    (order.getOrderStatus()==1&&order.getServiceStatus()==0&&order.getPayStatus()==4)){
                changeStatus(5,4,4,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：此订单不能进行终止操作，请检查状态");
            }
        }
        //开通方式:按时开通后付款
        if(4 == order.getOpeningMode()){
            if((order.getOrderStatus()==2&&order.getServiceStatus()==2)||
                    (order.getOrderStatus()==6&&order.getServiceStatus()==6)){
                changeStatus(5,4,null,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：此订单不能进行终止操作，请检查状态");
            }
        }
        //开通方式:即时开通后付款  或者 付款后自动开通
        if(1 == order.getOpeningMode() || 3 == order.getOpeningMode()){
            if(order.getOrderStatus()==2&&order.getServiceStatus()==2&&order.getPayStatus()==4){
                changeStatus(5,4,4,order,false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：此订单不能进行终止操作，请检查状态");
            }
        }
        throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：此订单不能进行终止操作，请检查状态");
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：所传的orderId对应的订单不存在");
        }
        //开通方式:人工开通后付款
        if(2 == order.getOpeningMode()){
            if(order.getOrderStatus()==1&&order.getServiceStatus()==0&&order.getPayStatus()==1){
                changeStatus(4, 5, 0, order, false);
                return true;
            }
            if(order.getOrderStatus()==2&&order.getServiceStatus()==2&&order.getPayStatus()==0){
                changeStatus(4, 5, 0, order, false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：此订单不能进行取消操作，请检查状态");
            }
        }
        //开通方式:付款后人工开通
        if(5 == order.getOpeningMode()){
            if(order.getOrderStatus()==1&&order.getServiceStatus()==0&&order.getPayStatus()==1){
                changeStatus(4, 5, 0, order, false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：此订单不能进行取消操作，请检查状态");
            }
        }
        //开通方式:按时开通后付款
        if(4 == order.getOpeningMode()){
            if(order.getOrderStatus()==0&&order.getServiceStatus()==0&&order.getPayStatus()==1){
                changeStatus(4, 5, 0, order, false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：此订单不能进行取消操作，请检查状态");
            }
        }
        //开通方式:即时开通后付款 或者 付款后自动开通
        if(1 == order.getOpeningMode() || 3 == order.getOpeningMode()){
            if(order.getOrderStatus()==0&&order.getServiceStatus()==0&&order.getPayStatus()==1){
                changeStatus(4, 5, 0, order, false);
                return true;
            }
            if(order.getOrderStatus()==2&&order.getServiceStatus()==2&&(order.getPayStatus()==0||order.getPayStatus()==5)){
                changeStatus(4, 5, 0, order, false);
                return true;
            } else{
                throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：此订单不能进行取消操作，请检查状态");
            }
        }
        throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：此订单不能进行取消操作，请检查状态");
    }
    
    /**
     * 更改订单的各种状态。
     * @param orderStatus 订单状态
     * @param serviceStatus 服务状态
     * @param payStatus 支付状态
     * @param order 订单对象
     * @param setServiceDateFlag true：需要设置订单服务开始时间和服务结束时间  false：不需要
     */
    public void changeStatus(Integer orderStatus,Integer serviceStatus,Integer payStatus,
            OrdOrder order,boolean setServiceDateFlag){
        if(orderStatus!=null){
            order.setOrderStatus(orderStatus);
        }
        if(serviceStatus!=null){
            order.setServiceStatus(serviceStatus); 
        }
        if(payStatus!=null){
            order.setPayStatus(payStatus);
        }
        if(setServiceDateFlag){
            //设置订单服务时间
            orderDataProcessingService.ServiceTimeByUnit(order);
        }
        ordOrderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void timingOpenService() {
        Date now = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.DATE, -2); // 减少2天，防止这次的当前时间比上一次的当前时间的秒晚了
        Date resultDate = ca.getTime();
        List<OrdOrder> orderList =ordOrderExtMapper.queryOrderByServiceStartDate(resultDate,now);
        if(orderList != null && !orderList.isEmpty()){
            int count = 0;
            for(OrdOrder order:orderList){
                //按时开通后付款 或者 即时开通后付款
                if(order.getOpeningMode() == 4 || order.getOpeningMode() == 1){
                    changeStatus(2,2,null,order,false);
                    messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_ENABLED,order.getOrderId());
                    count++;
                    continue;
                }
                //付款后自动开通
                if(order.getOpeningMode() == 3){
                    if(order.getPayStatus() == 1 || order.getPayStatus() == 4){
                        changeStatus(2,2,null,order,false);
                        messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_ENABLED,order.getOrderId());
                        count++;
                    }
                }
            }
            LOGGER.info(String.format("服务时间到点开通服务：本次已更新的订单条数为%s",count));
        }else{
            LOGGER.info("服务时间到点开通服务：本次已更新的订单条数为0");
        }
    }

    @Override
    public void timingCloseService() {
        Date now = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.DATE, -2); // 减少2天，防止这次的当前时间比上一次的当前时间的秒晚了
        Date resultDate = ca.getTime();
        List<OrdOrder> orderList =ordOrderExtMapper.queryOrderByServiceEndDate(resultDate,now);
        if(orderList != null && !orderList.isEmpty()) {
            int count = 0;
            for(OrdOrder order:orderList){
                changeStatus(3,3,null,order,false);
                messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_DISABLED,order.getOrderId());
                count++;
            }
            LOGGER.info(String.format("订单到期关闭服务：本次已更新的订单条数为%s",count));
        }else{
            LOGGER.info("订单到期关闭服务：本次已更新的订单条数为0");
        }
    }
}
