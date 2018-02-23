package com.foresee.ftcsp.order.manual.dao;

import java.util.List;
import com.foresee.ftcsp.order.auto.model.OrdInvoiceItem;

public interface OrdInvoiceItemExtMapper {
    /**
     * 根据发票流水ID查询发票项目
     *
     * @param invoiceItemId
     */
    List<OrdInvoiceItem> queryInvoiceItemByInvoiceId(Long invoiceId);
}