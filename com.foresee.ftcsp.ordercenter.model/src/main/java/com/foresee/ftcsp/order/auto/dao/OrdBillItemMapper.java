package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdBillItem;

public interface OrdBillItemMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param billItemId
     */
    int deleteByPrimaryKey(Long billItemId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdBillItem record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdBillItem record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param billItemId
     */
    OrdBillItem selectByPrimaryKey(Long billItemId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdBillItem record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdBillItem record);
}