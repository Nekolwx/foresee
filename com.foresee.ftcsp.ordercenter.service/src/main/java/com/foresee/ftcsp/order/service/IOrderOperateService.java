/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service;

/**
 * <pre>
 * 订单操作按钮服务类。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2018年1月22日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IOrderOperateService {
    
    /**
     * 订单开通服务。
     * @param orderId
     * @return boolean
     */
    public boolean openService(Long orderId);
    
    /**
     * 订单关闭服务。
     * @param orderId
     * @return boolean
     */
    public boolean closeService(Long orderId);
    
    /**
     * 终止订单。
     * @param orderId
     * @return boolean
     */
    public boolean terminateOrder(Long orderId);
    
    /**
     * 取消订单。
     * @param orderId
     * @return boolean
     */
    public boolean cancelOrder(Long orderId);

    /**
     * 服务时间到点开通服务。
     */
    public void timingOpenService();

    /**
     * 服务时间到点关闭服务。
     */
    public void timingCloseService();

}
