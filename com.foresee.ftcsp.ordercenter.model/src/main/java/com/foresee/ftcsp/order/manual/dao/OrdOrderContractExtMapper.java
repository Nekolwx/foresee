package com.foresee.ftcsp.order.manual.dao;

import com.foresee.ftcsp.order.manual.dto.OrdOrderContractDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdOrderContractExtMapper {

    /**
     * 根据合同编码查询合同
     * @param contractCode
     * @return
     */
    OrdOrderContractDTO queryContractByContractCode(@Param("contractCode") String contractCode);

    /**
     * 根据订单id查找合同信息
     * @param orderId
     * @return
     */
    OrdOrderContractDTO queryContractByOrderId(Long orderId);

}