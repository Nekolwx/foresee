package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdInvoiceExtend;

public interface OrdInvoiceExtendMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param invoiceExtendId
     */
    int deleteByPrimaryKey(Long invoiceExtendId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdInvoiceExtend record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdInvoiceExtend record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param invoiceExtendId
     */
    OrdInvoiceExtend selectByPrimaryKey(Long invoiceExtendId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdInvoiceExtend record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdInvoiceExtend record);
}