package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdPayFlowing;

public interface OrdPayFlowingMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param payId
     */
    int deleteByPrimaryKey(Long payId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdPayFlowing record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdPayFlowing record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param payId
     */
    OrdPayFlowing selectByPrimaryKey(Long payId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdPayFlowing record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdPayFlowing record);
}