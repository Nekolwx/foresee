package com.foresee.ftcsp.order.manual.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.order.auto.model.OrdBillInvoiceRel;
import com.foresee.ftcsp.order.auto.model.OrdInvoice;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.dto.OrdInvoiceListDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceBuyerInfoResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceDetailDTO;

@Repository
public interface OrdInvoiceExtMapper {
   
    /**
     * 分页查询开票信息。
     * @param param
     * @return PageQueryResult<OrdOrderListDTO>
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    PageQueryResult<OrdInvoiceListDTO> queryOrdInvoiceList(PageQueryParam param);
    
    /**
     * 根据billId查看开票信息。
     * @param ordBillDTO
     * @return List<InvoiceDetailDTO>
     */
    List<InvoiceDetailDTO> queryInvoiceByBillId(OrdBillDTO ordBillDTO);
    
    /**
     * 根据订单Id查询已开票信息中日期最近的购买方信息，父订单没有子订单情况。
     * @param orderId
     * @return List<InvoiceBuyerInfoResultDTO>
     */
    List<InvoiceBuyerInfoResultDTO> queryBuyerInfoByOrderId(@Param("orderId") Long orderId);
    
    /**
     * 根据父订单Id查询已开票信息中日期最近的购买方信息，父订单有子订单情况。
     * @param orderId    订单Id
     * @param orderTime 下单时间
     * @return List<InvoiceBuyerInfoResultDTO>
     */
    List<InvoiceBuyerInfoResultDTO> queryBuyerInfoByParentId(@Param("parentId") Long parentId,@Param("orderTime") Date orderTime);
    
    /**
     * 更新发票是否被红冲状态
     * @param invoiceCode
     * @param invoiceNumber
     * @param invalid
     * @return int
     */
    int updateInvoiceExtendInValid(@Param("invoiceCode")String invoiceCode,@Param("invoiceNumber")String invoiceNumber,@Param("invalid")Integer invalid);
    
    /**
     * 查询失败的开票流水
     * @return List<OrdInvoice>
     */
    List<OrdInvoice> queryOrdInvoiceFailByBillId(@Param("billId")Long billId,@Param("callStatus")Integer callStatus,@Param("billingType")Integer billingType);
    
    /**查询未调用的开票记录，最多40条  */
    List<OrdInvoice> queryOrdInvoiceForTask();
}