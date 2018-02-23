/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.mq.listener;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.foresee.ftcsp.mq.listener.ForeseeMessageListener;
import com.foresee.ftcsp.mq.message.impl.JobMessage;
import com.foresee.ftcsp.order.auto.model.OrdInvoice;
import com.foresee.ftcsp.order.auto.model.OrdInvoiceItem;
import com.foresee.ftcsp.order.constant.InvoiceConstant;
import com.foresee.ftcsp.order.manual.dao.OrdInvoiceExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdInvoiceItemExtMapper;
import com.foresee.ftcsp.order.service.IOrdInvoiceService;

/**
 * <pre>
 * 发票开具调度
 * </pre>
 *
 * @author yangchengjun@foresee.com.cn
 * @date 2017年11月27日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Component
public class IssueInvoiceListener extends ForeseeMessageListener<JobMessage>{
    
    private static final Logger           log = LoggerFactory.getLogger(IssueInvoiceListener.class);
    
    @Resource
    private OrdInvoiceExtMapper ordInvoiceExtMapper;
    
    @Resource
    private IOrdInvoiceService ordInvoiceService; 
    
    @Resource
    private OrdInvoiceItemExtMapper ordInvoiceItemExtMapper;
    
    @Override
    protected void handleMessage(JobMessage jobMessage) throws Exception {
        log.info("查询发票流水表里未调用的发票信息");
        //实现具体调度
        List<OrdInvoice> invoiceList=ordInvoiceExtMapper.queryOrdInvoiceForTask();
        log.info("未调用的发票信息条数为==========="+invoiceList.size());
        if (!invoiceList.isEmpty()) {
            for (OrdInvoice ordInvoice : invoiceList) {
                try {
                    List<OrdInvoiceItem> invoiceItemList=ordInvoiceItemExtMapper.queryInvoiceItemByInvoiceId(ordInvoice.getInvoiceId());
                    if (invoiceItemList.isEmpty()) {
                        log.error("发票流水id为"+ordInvoice.getInvoiceId()+"的发票项目为空");
                    }else {
                        ordInvoiceService.generateInvoice(ordInvoice, invoiceItemList, InvoiceConstant.INTERFACE_GENERATE_TYPE.AUTO);
                    }
                } catch (Exception e) {
                    log.error("发票流水id为"+ordInvoice.getInvoiceId()+"的发票开具失败："+e.getMessage());
                }
            }
        }
        log.info("调用开具发票结束！");
    }

}
