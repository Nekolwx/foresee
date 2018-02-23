package com.foresee.ftcsp.order.bill.service;

import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;

/**
 * <pre>
 * 账单辅助接口
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
public interface IBillAssistService {
    /**
     * 设置账单开始、结束时间
     * @param billDTO
     * @param channel
     */
    public void setBillTime(OrdBillDTO billDTO, Integer channel);

    /**
     * 生成账单编号
     * @return
     */
    public String generateBillCode();

}
