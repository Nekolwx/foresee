/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.mq;

import com.foresee.ftcsp.order.rest.mq.listener.*;
import com.foresee.ftcsp.order.service.impl.OrderOperateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foresee.ftcsp.mq.consumer.IMessageConsumer;
import com.foresee.ftcsp.mq.producer.IMessageProducer;
import com.foresee.ftcsp.order.constant.QueueConstants;
//import com.foresee.ftcsp.order.rest.mq.listener.ReceiveRechargeDataListener;

/**
 * <pre>
 * 队列初始化器。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年9月19日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

@Service
public class QueueInitializer {

    @Autowired
    private IMessageConsumer          rabbitMQConsumer;

    @Autowired
    private IMessageProducer          messageProducer;

    @Autowired
    private IMessageConsumer          messageConsumer;

    @Autowired
    private ReceiveOrderDataListener  receiveOrderDataListener;

    @Autowired
    private IssueInvoiceListener      issueInvoiceListener;

    //@Autowired
    //private ReceiveRechargeDataListener receiveRechargeDataListener;


    @Autowired
    private ChangeOrderStatusListener     changeOrderStatusListener;

    @Autowired
    private HandleOrderDataTempListener handleOrderDataTempListener;

    

    /**
     * 初始化
     */
    public void init(){
        
        //创建工作队列
        messageProducer.createQueue(QueueConstants.ORDER_CREATE_ORDER_EXCHANGE, QueueConstants.ORDER_CREATE_ORDER_QUEUE);
        //监听工作队列
        messageConsumer.startListen(QueueConstants.ORDER_CREATE_ORDER_QUEUE, 5, receiveOrderDataListener);
        //解析excel文件对应的已存临时数据的对列
        messageProducer.createQueue(QueueConstants.ORDER_CREATE_ORDER_EXCHANGE, QueueConstants.HANDLE_ORDER_DATA_TEMP);
        messageConsumer.startListen(QueueConstants.HANDLE_ORDER_DATA_TEMP,2,handleOrderDataTempListener);
        
        messageProducer.createQueue(QueueConstants.ORDER_CREATE_ORDER_EXCHANGE, QueueConstants.PRE_RECHARGE_QUEUE);
        //监听者由account服务那边实现
        //messageConsumer.startListen(QueueConstants.PRE_RECHARGE_QUEUE,5,receiveRechargeDataListener);

        //修改服务期限已过期的订单状态定时任务
        rabbitMQConsumer.startListen(QueueConstants.ORDER_STATU_SCHEDULE_QUEUE, 10, changeOrderStatusListener);

        //调度开具发票
        rabbitMQConsumer.startListen(QueueConstants.ORDER_ISSUE_INVOICE_QUEUE, 10, issueInvoiceListener);

    }

}
