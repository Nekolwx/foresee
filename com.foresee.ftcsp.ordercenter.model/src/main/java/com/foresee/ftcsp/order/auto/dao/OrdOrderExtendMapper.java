package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOrderExtend;

public interface OrdOrderExtendMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param orderExtendId
     */
    int deleteByPrimaryKey(Long orderExtendId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdOrderExtend record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOrderExtend record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderExtendId
     */
    OrdOrderExtend selectByPrimaryKey(Long orderExtendId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOrderExtend record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOrderExtend record);
}