/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.outter;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.constant.InvoiceConstant;
import com.foresee.ftcsp.order.manual.restdto.InvoiceBuyerInfoResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceStatusResultDTO;
import com.foresee.ftcsp.order.manual.restdto.IssueElectronicInvoiceDTO;
import com.foresee.ftcsp.order.manual.restdto.IssueInvoiceResultDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceBuyerInfoDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceStatusDTO;
import com.foresee.ftcsp.order.service.IOrdInvoiceService;

/**
 * <pre>
 * 开票管理外部接口控制器。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年11月9日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

@RestController("/outter/invoiceService")
@RequestMapping("/outter/invoiceService")
public class InvoiceManageResource {
    
    private Logger        LOGGER = Logger.getLogger(this.getClass());
    
    @Resource
    private IOrdInvoiceService ordInvoiceService; 
    
    /**
     * 查询开票状态接口。
     * @param queryInvoiceStatusDTO
     * @return RestResponse<InvoiceStatusResultDTO>
     */
    @RequestMapping(value = "/queryInvoiceStatus", method = { RequestMethod.POST })
    public RestResponse<InvoiceStatusResultDTO> queryInvoiceStatus(@Var @Valid QueryInvoiceStatusDTO queryInvoiceStatusDTO){
        InvoiceStatusResultDTO invoiceStatusResultDTO= ordInvoiceService.queryInvoiceStatus(queryInvoiceStatusDTO);
        
        return RestResponse.success("查询开票状态成功",invoiceStatusResultDTO);
    }
    
    /**
     * 查看发票接口。
     * @param queryInvoiceDTO
     * @return RestResponse<InvoiceResultDTO>
     */
    @RequestMapping(value = "/queryInvoice", method = { RequestMethod.POST })
    public RestResponse<InvoiceResultDTO> queryInvoice(@Var @Valid QueryInvoiceDTO queryInvoiceDTO){
        InvoiceResultDTO invoiceResultDTO= ordInvoiceService.queryInvoice(queryInvoiceDTO);
        
        return RestResponse.success("查看发票成功", invoiceResultDTO);
    }
    
    /**
     * 查询发票购买方信息接口。
     * @param queryInvoiceBuyerInfoDTO
     * @return RestResponse<InvoiceBuyerInfoResultDTO>
     */
    @RequestMapping(value = "/queryInvoiceBuyerInfo", method = { RequestMethod.POST })
    public RestResponse<InvoiceBuyerInfoResultDTO> queryInvoiceBuyerInfo(@Var @Valid QueryInvoiceBuyerInfoDTO queryInvoiceBuyerInfoDTO){
        InvoiceBuyerInfoResultDTO invoiceBuyerInfoResultDTO = ordInvoiceService.
                queryInvoiceBuyerInfo(queryInvoiceBuyerInfoDTO);
        return RestResponse.success("查询开票购买方信息成功", invoiceBuyerInfoResultDTO);
    }
    
    @RequestMapping(value = "/issueElectronicInvoice", method = { RequestMethod.POST })
    public RestResponse<IssueInvoiceResultDTO> issueElectronicInvoice (@Var @Valid IssueElectronicInvoiceDTO issueElectronicInvoiceDTO){
        LOGGER.info("申请开具发票接口请求参数===="+issueElectronicInvoiceDTO.toString());
        IssueInvoiceResultDTO issueInvoiceResultDTO=ordInvoiceService.issueElectronicInvoice(issueElectronicInvoiceDTO);
        if (InvoiceConstant.INVOICE_SERVICE_PLATFORM_RESPONSE_CODE.equals(issueInvoiceResultDTO.getReturnCode())) {
            return RestResponse.success("发票开具成功", issueInvoiceResultDTO);
        }else{
            return RestResponse.error(issueInvoiceResultDTO.getReturnCode(), issueInvoiceResultDTO.getReturnMsg()); 
        }
    }

}
