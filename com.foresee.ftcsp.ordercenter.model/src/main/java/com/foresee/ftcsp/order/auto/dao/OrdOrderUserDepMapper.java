package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdOrderUserDep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdOrderUserDepMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdOrderUserDep record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdOrderUserDep record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    OrdOrderUserDep selectByPrimaryKey(Long id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdOrderUserDep record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdOrderUserDep record);

    /**
     * 批量insert
     *
     * @return
     */
    int insertBatch(@Param("ordOrderUserDeps") List<OrdOrderUserDep> ordOrderUserDepList);
}