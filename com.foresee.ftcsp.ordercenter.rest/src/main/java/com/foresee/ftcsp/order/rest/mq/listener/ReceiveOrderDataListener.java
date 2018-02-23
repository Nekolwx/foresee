/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.mq.listener;

import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.foresee.ftcsp.common.core.rest.RestRequestParam;
import com.foresee.ftcsp.mq.listener.ForeseeMessageListener;
import com.foresee.ftcsp.mq.message.impl.JobHandleMessage;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamDTO;
import com.foresee.ftcsp.order.service.IOrderService;

/**
 * <pre>
 * 异步创建订单。
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
@Component
public class ReceiveOrderDataListener  extends ForeseeMessageListener<JobHandleMessage> {
    
    private static final Logger           log = LoggerFactory.getLogger(ReceiveOrderDataListener.class);
    
    @Resource
    private IOrderService orderService;

    @Override
    protected void handleMessage(JobHandleMessage t) throws Exception {
        
        log.info("进入到真正的业务处理之前");
        @SuppressWarnings("unchecked") Map<String,String> map = (Map<String,String>) t.getObject();
        orderService.createOrderAsynchronism(map);      

    }

}
