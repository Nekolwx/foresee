package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdBillInvoiceRel;

public interface OrdBillInvoiceRelMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param billInvoiceRelId
     */
    int deleteByPrimaryKey(Long billInvoiceRelId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdBillInvoiceRel record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdBillInvoiceRel record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param billInvoiceRelId
     */
    OrdBillInvoiceRel selectByPrimaryKey(Long billInvoiceRelId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdBillInvoiceRel record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdBillInvoiceRel record);
}