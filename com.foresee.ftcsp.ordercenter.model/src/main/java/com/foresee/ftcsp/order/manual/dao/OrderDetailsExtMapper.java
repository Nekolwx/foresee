package com.foresee.ftcsp.order.manual.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.order.manual.dto.OrdBillItemDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
@Repository
public interface OrderDetailsExtMapper {
	/**
     * 根据订单编号查询订单详情
     * @param orderCode
     * @return OrdOrderDTO
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	OrdOrderDTO queryOrderByOrderId(Long orderId);
            
    /**
     * 根据parentId查子订单id
     * @param parentId
     * @return List<Long>
     */
	List<Long> queryOrderByParentId(Long parentId);
	
	/**
     * 根据订单id查询账单项目表，获取商品详情
     * @param orderId
     * @return List<OrdBillItemDTO>
     */
	 @Transactional(propagation=Propagation.NOT_SUPPORTED)
	List<OrdBillItemDTO> queryOrdBillItem(Long orderId);
}