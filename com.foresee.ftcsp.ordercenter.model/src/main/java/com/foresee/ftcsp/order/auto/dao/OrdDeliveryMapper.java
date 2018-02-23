package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdDelivery;

public interface OrdDeliveryMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param deliveryId
     */
    int deleteByPrimaryKey(Long deliveryId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdDelivery record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdDelivery record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param deliveryId
     */
    OrdDelivery selectByPrimaryKey(Long deliveryId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdDelivery record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdDelivery record);
}