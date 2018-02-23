package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOrder;

public interface OrdOrderMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param orderId
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdOrder record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOrder record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderId
     */
    OrdOrder selectByPrimaryKey(Long orderId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOrder record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOrder record);
}