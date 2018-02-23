/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service;

import java.util.List;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;

/**
 * <pre>
 * 支付流水接口。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月19日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IPayFlowingService {
    
    /**
     * 根据支付流水查询支付信息。
     * @param payFlowingDTO
     * @return OrdPayFlowingDTO
     */
    OrdPayFlowingDTO queryPayFlowingInfoByPayNo(OrdPayFlowingDTO payFlowingDTO);
    
    /**
     * 根据支付流水查询订单ID。
     * @return List<OrdPayFlowingDTO>
     */
    List<OrdPayFlowingDTO> queryOrderIdByPayNo(OrdPayFlowingDTO payFlowingDTO);
    
    /**
     * 根据订单编号查询业务流水。
     * @param payFlowingDTO
     * @return OrdPayFlowingDTO
     */
    OrdPayFlowingDTO queryPayNoByOrderCode(OrdPayFlowingDTO payFlowingDTO);

    /**
     * 根据支付流水号更新支付流水表信息。
     * @param payFlowingDTO
     * @return int
     */
    int updateByPayNoSelective(OrdPayFlowingDTO payFlowingDTO);
}
