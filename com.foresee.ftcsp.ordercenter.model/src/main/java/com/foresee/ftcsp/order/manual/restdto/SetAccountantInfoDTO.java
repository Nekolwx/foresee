/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.foresee.ftcsp.order.common.CommonDict;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @date 2017年8月19日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class SetAccountantInfoDTO {

   
    @NotEmpty(message="businessOrderNo不能为空")
    private String businessOrderNo;
    
    @NotNull(message="channel不能为空")
    private Integer channel;
    
    private String accountingSupervisorId;
    
    private String accountingAssistantId;
    
    @NotEmpty(message="factoryId不能为空")
    private String factoryId;
    
    
    public String getBusinessOrderNo() {
        return businessOrderNo;
    }
    
    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public Integer getChannel() {
        return channel;
    }
    
    public void setChannel(Integer channel) {
        this.channel = channel;
    }
    
    public String getAccountingSupervisorId() {
        return accountingSupervisorId;
    }
    
    public void setAccountingSupervisorId(String accountingSupervisorId) {
        this.accountingSupervisorId = accountingSupervisorId;
    }
    
    public String getAccountingAssistantId() {
        return accountingAssistantId;
    }
    
    public void setAccountingAssistantId(String accountingAssistantId) {
        this.accountingAssistantId = accountingAssistantId;
    }
    
    public String getFactoryId() {
        return factoryId;
    }
    
    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }
    
    
    
}
