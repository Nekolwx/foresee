/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * 查看发票接口入参DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年11月10日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class QueryInvoiceDTO {
    
    @NotBlank(message = "业务订单号不能为空")
    private String businessOrderNo; //  必须  业务订单编号
    
    @NotNull(message = "渠道来源不能为空")
    @Pattern(regexp = "^\\d+$",message = "渠道来源参数不合法")
    private String channel ; // 必须 
    
    private String billCode;

    
    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    
    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    
    public String getChannel() {
        return channel;
    }

    
    public void setChannel(String channel) {
        this.channel = channel;
    }

    
    public String getBillCode() {
        return billCode;
    }

    
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
    
    

}
