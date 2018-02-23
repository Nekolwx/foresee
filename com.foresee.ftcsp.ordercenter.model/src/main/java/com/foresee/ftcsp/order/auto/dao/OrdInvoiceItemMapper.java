package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdInvoiceItem;

public interface OrdInvoiceItemMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param invoiceItemId
     */
    int deleteByPrimaryKey(Long invoiceItemId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdInvoiceItem record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdInvoiceItem record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param invoiceItemId
     */
    OrdInvoiceItem selectByPrimaryKey(Long invoiceItemId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdInvoiceItem record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdInvoiceItem record);
}