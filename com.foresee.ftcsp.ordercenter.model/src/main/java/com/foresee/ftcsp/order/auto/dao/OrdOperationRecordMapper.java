package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOperationRecord;

public interface OrdOperationRecordMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param operationId
     */
    int deleteByPrimaryKey(Long operationId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdOperationRecord record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOperationRecord record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param operationId
     */
    OrdOperationRecord selectByPrimaryKey(Long operationId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOperationRecord record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOperationRecord record);
}