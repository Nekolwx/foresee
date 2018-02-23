package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdReimburse;

public interface OrdReimburseMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param reimburseId
     */
    int deleteByPrimaryKey(Long reimburseId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdReimburse record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdReimburse record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param reimburseId
     */
    OrdReimburse selectByPrimaryKey(Long reimburseId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdReimburse record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdReimburse record);
}