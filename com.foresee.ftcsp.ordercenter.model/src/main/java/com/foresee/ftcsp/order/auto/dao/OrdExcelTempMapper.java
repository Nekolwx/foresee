package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdExcelTemp;

public interface OrdExcelTempMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param excelId
     */
    int deleteByPrimaryKey(Long excelId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdExcelTemp record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdExcelTemp record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param excelId
     */
    OrdExcelTemp selectByPrimaryKey(Long excelId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdExcelTemp record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdExcelTemp record);
}