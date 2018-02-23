package com.foresee.ftcsp.ordercenter.api.dto;

import com.foresee.ftcsp.common.core.page.BasePageQueryParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 * @date 2018/2/2
 */
public class QueryChargingListDTO extends BasePageQueryParam {

    @NotBlank(message = "查询渠道不能为空")
    private String channel;

    private Long serviceEndDate;

    private Long orderId;

    private String customerId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Long serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }
}
