package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdBillMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param billId
     */
    int deleteByPrimaryKey(Long billId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdBill record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdBill record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param billId
     */
    OrdBill selectByPrimaryKey(Long billId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdBill record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdBill record);

    /**
     * 批量insert
     * @param ordBills
     * @return
     */
    int insertBatch(@Param("ordBills") List<OrdBill> ordBills);
}