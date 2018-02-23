/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.mq.listener;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.foresee.ftcsp.mq.listener.ForeseeMessageListener;
import com.foresee.ftcsp.mq.message.impl.JobMessage;
import com.foresee.ftcsp.order.service.IOrderService;

/**
 * <pre>
 * 定时改变服务期限已过期的订单状态。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年10月24日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Component
public class ChangeOrderStatusListener extends ForeseeMessageListener<JobMessage>{
    private static final Logger           log = LoggerFactory.getLogger(ChangeOrderStatusListener.class);
    
    @Resource
    private IOrderService orderService;

    @Override
    protected void handleMessage(JobMessage t) throws Exception {
        log.info("接收到调度消息，改变服务期限已过期的订单状态任务开始");
        try {
            int number = orderService.updateOrderStatusTimeOut();
            log.info(String.format("接收到调度消息，改变服务期限已过期的订单状态任务结束,共更新%s条记录", number));
        } catch (Exception e) {
            log.error(e.toString());
            log.info("接收到调度消息，改变服务期限已过期的订单状态任务异常");
        }
        
        
    }

}
