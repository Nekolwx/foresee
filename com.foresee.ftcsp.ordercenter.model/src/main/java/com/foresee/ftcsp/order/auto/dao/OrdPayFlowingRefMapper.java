package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdPayFlowingRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdPayFlowingRefMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param orderPayId
     */
    int deleteByPrimaryKey(Long orderPayId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdPayFlowingRef record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdPayFlowingRef record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param orderPayId
     */
    OrdPayFlowingRef selectByPrimaryKey(Long orderPayId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdPayFlowingRef record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdPayFlowingRef record);

    /**
     * 批量insert
     * @param records
     * @return
     */
    int insertBatch(@Param("refs") List<OrdPayFlowingRef> records);
}