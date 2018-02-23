package com.foresee.ftcsp.order.bill.service;

import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;

/**
 * <pre>
 * 账单基础接口
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
public interface IBillBaseService {

    /**
     * 创建账单
     * @param billDTO
     * @return
     */
    public OrdBillDTO createBill(OrdBillDTO billDTO);

}
