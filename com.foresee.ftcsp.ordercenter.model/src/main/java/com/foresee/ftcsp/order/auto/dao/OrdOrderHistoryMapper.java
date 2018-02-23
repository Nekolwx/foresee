package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOrderHistory;

public interface OrdOrderHistoryMapper {
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
    int insert(OrdOrderHistory record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOrderHistory record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderId
     */
    OrdOrderHistory selectByPrimaryKey(Long orderId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOrderHistory record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOrderHistory record);
}