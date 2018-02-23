package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOrderContract;

public interface OrdOrderContractMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param contractId
     */
    int deleteByPrimaryKey(String contractId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdOrderContract record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOrderContract record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param contractId
     */
    OrdOrderContract selectByPrimaryKey(String contractId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOrderContract record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOrderContract record);
}