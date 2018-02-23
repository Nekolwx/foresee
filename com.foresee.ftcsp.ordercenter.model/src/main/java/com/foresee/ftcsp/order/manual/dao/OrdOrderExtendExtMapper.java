/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.dao;

import com.foresee.ftcsp.order.manual.dto.OrdOrderExtendDTO;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @date 2017年8月20日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface OrdOrderExtendExtMapper {

    /**
     * 根据订单Id查询是否存在
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月20日
     * TODO。
     * @param orderId
     * @return OrdOrderExtendDTO
     */
    OrdOrderExtendDTO selectByOrderId(Long orderId);
}
