package com.foresee.ftcsp.order.manual.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.foresee.ftcsp.order.auto.model.OrdPayFlowing;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;

public interface OrdPayFlowingExtMapper {
    
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
     * 根据订单编号查询支付流水。
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
    
    /**
     * 根据orderId查询支付流水表信息。
     * @param orderId
     * @return OrdPayFlowingDTO
     */
    OrdPayFlowingDTO selectByOrderId (@Param("orderId")Long orderId);

    /**
     * 批量insert
     * @param ordPayFlowingDTOS
     * @return
     */
    int insertPayFlowingInBatch(@Param("playFlows")List<OrdPayFlowingDTO> ordPayFlowingDTOS);

}