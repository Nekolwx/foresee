package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.order.manual.restdto.BusinessHandle;
import com.foresee.ftcsp.order.manual.restdto.TryBusinessHandle;

/**
 * <pre>
 * 业务办理接口类。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 *   <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2017年12月14日
 */
public interface IBusinessProcessingService {

    /**
     * 查询是否互斥商品规则。
     * @return
     */
    Object queryGoodsIsMutuallyExclusive(TryBusinessHandle tryBusinessHandle);

    /**
     * 试用商品业务办理添加订单接口
     * @param tryBusinessHandle
     * @return
     */
    Object addTryOutBusinessHandle(TryBusinessHandle tryBusinessHandle,String userId);

    /**
     * 详情业务办理添加订单接口
     * @param businessHandle
     * @param userId
     * @return
     */
    Object addDetailedBusinessHandle(BusinessHandle businessHandle, String userId);

}
