/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.common.core.model.Event;
import com.foresee.ftcsp.order.manual.restdto.BusinessHandleDataDTO;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamDTO;
import com.foresee.ftcsp.order.manual.restdto.PreRechargeDTO;


/**
 * <pre>
 * 异步创建订单发送数据接口。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年9月25日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IMessageProducerService {
    
    /**
     * 异步创建订单消息发送。
     * @param payParamDTO
     * @return Object
     */
    public Object sendOrderData(GetPayParamDTO payParamDTO);
    
    /**
     * 充值请求消息发送。
     * @param preRechargeDTO
     * @return Object
     */
    public Object sendRechargeData(PreRechargeDTO preRechargeDTO);
    
    /**
     * 业务办理后要报文写入mq，mq服务根据报文信息调用悦财税接口。
     * @param o
     * @return Object
     */
    public Object sendBusinessHandleData(BusinessHandleDataDTO b,String msgId);

    /**
     * 订单按钮操作写入事件队列mq，mq服务根据报文信息调用悦财税接口。
     * @param String eventType
     * @param Long orderId
     * @return Object
     */
    public Object sendOrderOperateData(String eventType,Long orderId);

}

