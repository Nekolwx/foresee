/*
* Copyright（c） Foresee Science & Technology Ltd.
*/

package com.foresee.ftcsp.ordercenter.url;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwenlong@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月28日
 */
public interface InnerURL {


    interface Order {
        String changOrderStatusByCusIdAndChannel = "/inner/orderService/changOrderStatusByCusIdAndChannel";
        String createOrder ="/inner/orderService/createOrder";
        String updateOrderStatusByTask = "/inner/orderService/updateOrderStatusByTask";
        String queryHavingOrderByCusId = "/inner/orderService/queryHavingOrderByCusId";
        String openService = "/inner/orderService/openService";
        String closeService = "/inner/orderService/closeService";
        String queryChargingOrderList = "/inner/orderService/queryChargingOrderList";
        String updateOrderInfo = "/inner/orderService/updateOrderInfo";
        String updateTestGoodsOrderToEnd = "/inner/orderService/updateTestGoodsOrderToEnd";
        String timingOpenService = "/inner/orderService/timingOpenService";
        String timingCloseService = "/inner/orderService/timingCloseService";
    }
}
