package com.foresee.ftcsp.order.bill.service.impl;

import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.order.bill.service.IBillAssistService;
import com.foresee.ftcsp.order.constant.BillConstant;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * 账单辅助实现类
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *             修改后版本:     修改人：  修改日期:     修改内容:
 *                   </pre>
 * @date 2018-02-08
 */
@Service
public class BillAssistServiceImpl implements IBillAssistService{

    @Resource
    private IDUtil idUtil;

    @Override
    public void setBillTime(OrdBillDTO billDTO, Integer channel) {
        if(null != channel){
            switch(channel){
                case 12: // 金财管家代账版,需要把账单时间设置到上一个月的第一天跟最后一天
                    // 设置开始时间为上个月1号
                    Calendar billStartTime=Calendar.getInstance();
                    billStartTime.add(Calendar.MONTH, -1);
                    billStartTime.set(Calendar.DAY_OF_MONTH,1);
                    billDTO.setBillStartTime(billStartTime.getTime());
                    // 设置结束时间为上个月最后一天
                    Calendar billEndTime = Calendar.getInstance();
                    billEndTime.set(Calendar.DAY_OF_MONTH,0);
                    billDTO.setBillEndTime(billEndTime.getTime());
                    // 设置为不需要开票
                    billDTO.setInvoiceStatus(BillConstant.BILL_INVOICE_STATUS_NOT_NEED);
                    break;
                default:
                    // 设置为未开票，账单时间根据具体需求补充
                    billDTO.setInvoiceStatus(BillConstant.BILL_INVOICE_STATUS_NOT_BLUE);
            }
        }
    }

    @Override
    public String generateBillCode() {
        String billDateStr = LocalDateUtil.getDateStr(LocalDateUtil.YMD, new Date());
        String billCodeKey = String.format(idUtil.createSequenceId("bill_code_key"), "1");
        return billDateStr+billCodeKey;
    }

}
