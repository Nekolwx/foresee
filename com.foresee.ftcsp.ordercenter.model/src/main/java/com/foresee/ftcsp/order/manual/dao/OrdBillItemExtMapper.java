package com.foresee.ftcsp.order.manual.dao;

import java.util.List;
import com.foresee.ftcsp.order.auto.model.OrdBillItem;

public interface OrdBillItemExtMapper {
    
    /**
     * 根据账单ID查询账单项目
     *
     * @param billId
     */
    List<OrdBillItem> queryBillItemByBillId(Long billId);
}