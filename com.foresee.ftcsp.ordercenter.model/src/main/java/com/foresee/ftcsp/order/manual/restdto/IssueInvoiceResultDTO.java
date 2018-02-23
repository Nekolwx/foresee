/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.manual.restdto;

/**
 * <pre>
 * 开具发票结果DTO
 * </pre>
 *
 * @author yangchengjun@foresee.com.cn
 * @date 2017年11月29日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class IssueInvoiceResultDTO {

    /**
     * returnCode:返回码。
     */
    String returnCode;

    /**
     * returnMsg:返回信息。
     */
    String returnMsg;

    /**
     * invoiceUrl:发票地址。
     */
    String invoiceUrl;


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

}
