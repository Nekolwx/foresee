package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdBillPayRef;

public interface OrdBillPayRefMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param billPayRefId
     */
    int deleteByPrimaryKey(Long billPayRefId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdBillPayRef record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdBillPayRef record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param billPayRefId
     */
    OrdBillPayRef selectByPrimaryKey(Long billPayRefId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdBillPayRef record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdBillPayRef record);
}