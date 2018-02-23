package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdInvoice;

public interface OrdInvoiceMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param invoiceId
     */
    int deleteByPrimaryKey(Long invoiceId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdInvoice record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdInvoice record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param invoiceId
     */
    OrdInvoice selectByPrimaryKey(Long invoiceId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdInvoice record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdInvoice record);
}