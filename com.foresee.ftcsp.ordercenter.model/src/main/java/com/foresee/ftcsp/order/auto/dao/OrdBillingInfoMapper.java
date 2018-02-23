package com.foresee.ftcsp.order.auto.dao;

import com.foresee.ftcsp.order.auto.model.OrdBillingInfo;

public interface OrdBillingInfoMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param billingInfoId
     */
    int deleteByPrimaryKey(Long billingInfoId);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OrdBillingInfo record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(OrdBillingInfo record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param billingInfoId
     */
    OrdBillingInfo selectByPrimaryKey(Long billingInfoId);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OrdBillingInfo record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OrdBillingInfo record);
}