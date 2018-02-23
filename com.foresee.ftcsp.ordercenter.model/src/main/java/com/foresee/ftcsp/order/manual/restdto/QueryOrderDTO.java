package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/10/13
 */
public class QueryOrderDTO {

    @NotBlank(message = "业务订单号不能为空")
    private String businessOrderNo;

    @NotNull(message = "渠道来源不能为空")
    @Pattern(regexp = "^\\d+$",message = "渠道来源参数不合法")
    private String channel;

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
}
