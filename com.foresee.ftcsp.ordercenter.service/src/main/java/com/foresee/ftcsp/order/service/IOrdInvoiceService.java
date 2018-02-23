/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service;

import java.util.List;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.order.auto.model.OrdInvoice;
import com.foresee.ftcsp.order.auto.model.OrdInvoiceExtend;
import com.foresee.ftcsp.order.auto.model.OrdInvoiceItem;
import com.foresee.ftcsp.order.manual.dto.OrdInvoiceListDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceBuyerInfoResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceStatusResultDTO;
import com.foresee.ftcsp.order.manual.restdto.IssueElectronicInvoiceDTO;
import com.foresee.ftcsp.order.manual.restdto.IssueInvoiceResultDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceBuyerInfoDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceStatusDTO;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @date 2017年8月31日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IOrdInvoiceService {
   
    /**
     * 分页查询开票信息。
     * @author chenzexin@foresee.com.cn
     * @date 2017年8月31日
     * @param param
     * @return PageQueryResult<OrdInvoiceListDTO>
     */
    PageQueryResult<OrdInvoiceListDTO> queryOrdInvoiceList(PageQueryParam param);
    
    /**
     * 根据业务订单号和来源查询开票状态接口。
     * @param queryInvoiceStatusDTO
     * @return InvoiceStatusResultDTO
     */
    InvoiceStatusResultDTO queryInvoiceStatus(QueryInvoiceStatusDTO queryInvoiceStatusDTO);

    /**
     * 根据业务订单号和来源查询发票信息。
     * @param queryInvoiceDTO
     * @return InvoiceResultDTO
     */
    InvoiceResultDTO queryInvoice(QueryInvoiceDTO queryInvoiceDTO);
    
    /**
     * 根据业务订单号和来源查询发票购买方信息接口。
     * @param queryInvoiceBuyerInfoDTO
     * @return InvoiceBuyerInfoResultDTO
     */
    InvoiceBuyerInfoResultDTO queryInvoiceBuyerInfo(QueryInvoiceBuyerInfoDTO queryInvoiceBuyerInfoDTO);
    
    /**
     * 申请开票接口。
     * @param issueElectronicInvoiceDTO
     * @return Map<String,Object>
     */
    IssueInvoiceResultDTO issueElectronicInvoice(IssueElectronicInvoiceDTO issueElectronicInvoiceDTO);
    
    /**
     * 调用发票服务平台开具发票
     * @param ordInvoice
     * @param invoiceItemList
     * @param generateType   开票方式：0手动 1自动
     * @return OrdInvoiceExtend
     */
    OrdInvoiceExtend generateInvoice(OrdInvoice ordInvoice, List<OrdInvoiceItem> invoiceItemList,String generateType);
    
}
