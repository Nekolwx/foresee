package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOrderAttribute;

public interface OrdOrderAttributeMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param orderAttributeId
     */
    int deleteByPrimaryKey(Long orderAttributeId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdOrderAttribute record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOrderAttribute record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderAttributeId
     */
    OrdOrderAttribute selectByPrimaryKey(Long orderAttributeId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOrderAttribute record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOrderAttribute record);
}