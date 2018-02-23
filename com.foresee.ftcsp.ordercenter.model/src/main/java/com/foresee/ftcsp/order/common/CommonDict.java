package com.foresee.ftcsp.order.common;

/**
 * <pre>
 *  存放一些公用的常量
 * </pre>
 *
 * @author jiangrunqiao@foresee.com.cn
 * @version 1.00.00
 * @date 2017/8/11
 * <p>
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
public interface CommonDict {
    /**
     * ID最小值
     */
    long ID_MIN_VALUE = 10000000000000000L;
    /**
     * ID最大值
     */
    long ID_MAX_VALUE = Long.MAX_VALUE;
    /**
     * 产品状态最小值
     */
    int PRODUCT_STATUS_MIN = 0;
    /**
     * 产品状态最大值
     */
    int PRODUCT_STATUS_MAX = 2;
    /**
     * 产品性质最小值
     */
    int PRODUCT_NATURE_MIN = 1;
    /**
     * 产品性质最大值
     */
    int PRODUCT_NATURE_MAX = 2;
    /**
     * 产品单位最小值
     */
    int PRODUCT_UNIT_MIN = 1;
    /**
     * 产品单位最大值
     */
    int PRODUCT_UNIT_MAX = 4;
    /**
     * 产品库存状态值: 无库存
     */
    int PRODUCT_INVENTORY_FALSE = 0;
    /**
     * 产品库存状态值: 有库存
     */
    int PRODUCT_INVENTORY_TRUE = 1;
    /**
     * 附属费用管理模式: 最小值
     */
    int PRODUCT_CAST_MIN = 0;
    /**
     * 附属费用管理模式: 最大值
     */
    int PRODUCT_CAST_MAX = 1;
    /**
     * 产品类型: 产品
     */
    int PRODUCT_TYPE_PRODUCT = 1;
    /**
     * 产品类型: 产品包
     */
    int PRODUCT_TYPE_PRODUCT_BAG = 2;
    /**
     * 产品状态: 停用
     */
    int PRODUCT_STATUS_DISABLED = 0;
    /**
     * 产品状态: 启用
     */
    int PRODUCT_STATUS_ENABLED = 1;
    /**
     * 产品状态: 录入
     */
    int PRODUCT_STATUS_ENTERING = 2;
    /**
     * 是否删除: 是
     */
    int IS_DELETE_TRUE = 1;
    /**
     * 是否删除: 否
     */
    int IS_DELETE_FALSE = 0;
    /**
     * 是否默认: 是
     */
    int IS_DEFAULT_TRUE = 1;
    /**
     * 是否默认: 否
     */
    int IS_DEFAULT_FALSE = 0;
    /**
     * 描述展现渠道: 最小值
     */
    int DESCRIPTION_DISPLAY_TYPE_MIN = 1;
    /**
     * 描述展现渠道: 最大值
     */
    int DESCRIPTION_DISPLAY_TYPE_MAX = 2;
    /**
     * 描述填写方式: 最小值
     */
    int DESCRIPTION_FILL_WAY_MIN = 1;
    /**
     * 描述填写方式: 最大值
     */
    int DESCRIPTION_FILL_WAY_MAX = 2;
    /**
     * 修改产品状态失败: 产品包中使用
     */
    int CHANGE_STATUS_FAILED_USED_IN_BAG = -1;
    /**
     * 修改产品状态失败: 商品中使用
     */
    int CHANGE_STATUS_FAILED_USED_IN_GOODS = -2;
    /**
     * 修改产品状态失败: 商品中使用
     */
    int CHANGE_STATUS_FAILED_USED_IN_COMBO = -3;

    /**
     * 连接符号: ,
     */
    String MARK_COMMA = ",";

}
