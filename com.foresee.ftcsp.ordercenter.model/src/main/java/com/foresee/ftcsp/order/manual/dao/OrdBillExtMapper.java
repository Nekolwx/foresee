package com.foresee.ftcsp.order.manual.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.dto.OrdInvoiceExtendDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceBuyerInfoResultDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryBillDetailDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryBillPageDTO;

@Repository
public interface OrdBillExtMapper {
	
	/**
     * 分页查询账单。
     * @param param
     * @return PageQueryResult<QueryBillPageDTO>
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
    PageQueryResult<QueryBillPageDTO> selectByPageQueryParam(PageQueryParam param);
	
	/**
     * 账单详情查询。
     * @param billId
     * @return QueryBillDetailDTO
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	QueryBillDetailDTO queryBillDetailByBillId(Long billId);
	
	/**
	 * 根据订单id查询账单
	 * @param orderId
	 * @return OrdBillDTO
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	List<OrdBillDTO> queryBillByOrderId(Long orderId);
	
	/**
	 * 根据DTO条件查询账单。
	 * @param ordBillDTO
	 * @return List<OrdBillDTO>
	 */
	List<OrdBillDTO> queryByOrdBillDTO(OrdBillDTO ordBillDTO);
	
	/**
	 * 根据订单Id查询下单时的购买方信息。
	 * @param orderId
	 * @return InvoiceBuyerInfoResultDTO
	 */
	InvoiceBuyerInfoResultDTO queryBuyerInfoByOrderId(@Param(value="orderId") Long orderId);
	
    /**
     * 根据发票流水ID查询订单、账单里需要的字段来判断更新父订单发票状态
     * @param invoiceId
     * @return List<OrdBillDTO>
     */
    List<OrdBillDTO> queryBillAndOrdByInvoiceId(@Param(value="invoiceId") Long invoiceId);
    
    /**
     * 根据订单和发票信息查询账单（用于红冲校验）
     * @param invoiceExtendDTO
     * @return List<OrdBillDTO>
     */
    List<OrdBillDTO> queryBillByOrderIdAndInvoiceInfo(OrdInvoiceExtendDTO invoiceExtendDTO);
}