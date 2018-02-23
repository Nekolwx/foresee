package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdDiscountEntry;

public interface OrdDiscountEntryMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param orderDiscountEntryId
     */
    int deleteByPrimaryKey(Long orderDiscountEntryId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdDiscountEntry record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdDiscountEntry record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderDiscountEntryId
     */
    OrdDiscountEntry selectByPrimaryKey(Long orderDiscountEntryId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdDiscountEntry record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdDiscountEntry record);
}