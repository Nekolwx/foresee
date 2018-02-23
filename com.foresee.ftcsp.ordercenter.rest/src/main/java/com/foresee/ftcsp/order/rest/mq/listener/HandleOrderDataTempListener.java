package com.foresee.ftcsp.order.rest.mq.listener;

import com.foresee.ftcsp.common.core.util.Loggers;
import com.foresee.ftcsp.mq.listener.ForeseeMessageListener;
import com.foresee.ftcsp.mq.message.impl.JobHandleMessage;
import com.foresee.ftcsp.order.service.IBatchExcelService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/28
 */
@Component
public class HandleOrderDataTempListener  extends ForeseeMessageListener<JobHandleMessage> {

    Logger logger = Loggers.make();

    @Autowired
    IBatchExcelService iBatchExcelService;

    @Override
    protected void handleMessage(JobHandleMessage jobHandleMessage) throws Exception {
        //根据excel文件id去解析临时数据
        try {
            logger.info("收到解析订单临时表数据校验任务,进行校验并写入订单表!");
            Map param = (Map)jobHandleMessage.getObject();
            Long excelId = (Long) param.get("excelId");
            String taskId = (String) param.get("taskId");
            iBatchExcelService.handleDataByExcelId(excelId,taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
