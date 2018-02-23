/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.constant;

import com.foresee.ftcsp.mq.enums.QueueNamePrefix;

/**
 * <pre>
 * 队列常量。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年10月26日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface QueueConstants {
    
    //异步创建订单
    String ORDER_CREATE_ORDER_EXCHANGE = "order_exchange_create_order";
    
    String ORDER_CREATE_ORDER_QUEUE = "order_queue_create_order";

    //解析excel文件对应的已存临时数据
    String HANDLE_ORDER_DATA_TEMP = "handle_order_excel_data_temp_queue";
    
    //
    String PRE_RECHARGE_QUEUE = "pre_recharge_queue";
    
    //订单状态定时调度
    String ORDER_STATU_SCHEDULE_QUEUE = QueueNamePrefix.SCHEDULE_SEND.getKey()+"order_task_changeOrderStatus";
    
    //调度开具发票
    String ORDER_ISSUE_INVOICE_QUEUE = QueueNamePrefix.SCHEDULE_SEND.getKey()+"order_issue_invoice_queue";

}
