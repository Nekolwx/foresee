package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdExcelDataTemp;
import com.foresee.ftcsp.order.manual.exceltemplate.OrderTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdExcelDataTempMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param dataId
     */
    int deleteByPrimaryKey(Long dataId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdExcelDataTemp record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdExcelDataTemp record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param dataId
     */
    OrdExcelDataTemp selectByPrimaryKey(Long dataId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdExcelDataTemp record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdExcelDataTemp record);

    /**
     * 批量insert
     * @param objList
     * @return
     */
    int batchInsert(@Param("dataList") List<OrdExcelDataTemp> objList);

    List<OrdExcelDataTemp> selectByExcelId(@Param("excelId")Long excelId,@Param("start")Long start,@Param("end")Long end);

    int batchUpdate(@Param("dataList") List<OrdExcelDataTemp> objList);

}