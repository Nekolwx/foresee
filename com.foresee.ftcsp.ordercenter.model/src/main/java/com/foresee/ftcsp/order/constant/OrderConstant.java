/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.constant;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author huangzhao@foresee.com.cn
 * @date 2017年8月18日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public abstract class OrderConstant {

    /**
     * 0值
     */
    public static final Integer ZERO                      = 0;

    /**
     * 1 值
     */
    public static final Integer ONE                       = 1;

    /**
     * 2值
     */
    public static final Integer TWO                       = 2;
    
    /**
     * 3值
     */
    public static final Integer THREE                       = 3;
    
    /**
     * 4值
     */
    public static final Integer FOUR                       = 4;

    /**
     * 5值
     */
    public static final Integer FIVES                       = 5;

    /**
     * 订单类型0 新增
     */
    public static final Integer ORDER_TYPE_ADD            = 0;

    /**
     * 订单类型1 续期
     */
    public static final Integer ORDER_TYPE_RENEWAL        = 1;


    /**
     * 开票状态0 未开票
     */
    public static final Integer BILLING_STATUS_NO         = 0;

    /**
     * 开票状态1 已开票
     */
    public static final Integer BILLING_STATUS_YES        = 1;

    /**
     * 开票状态2 不需要开票
     */
    public static final Integer BILLING_STATUS_NO_NEED    = 2;

    /**
     * 是否需要配送 0 不需要
     */
    public static final Integer NEED_DELIVERY_NO          = 0;

    /**
     * 是否需要配送 1 需要
     */
    public static final Integer NEED_DELIVERY_YES         = 1;

    /**
     * 是否分期 0 全额
     */
    public static final Integer DIVIDE_PAY_NO             = 0;
    
    /**
     * 是否分期 1 分期
     */
    public static final Integer DIVIDE_PAY_YES             = 1;

    /**
     * 订单编号序列key
     */
    public static final String  ORDER_CODE_SEQUENCE_KEY   = "order_code_key";

    /**
     * 支付流水前缀
     */
    public static final String  ORDER_PAY_NO_PREFIX       = "PY";

    /**
     * 支付流水序列key
     */
    public static final String  ORDER_PAY_NO_SEQUENCE_KEY = "order_pay_sequence";

    /**
     * 服务期限单位 0-天 。
     */
    public static final Integer SERVICE_TERM_UNIT_DAY = 0;
    
    /**
     * 服务期限单位 1-月。
     */
    public static final Integer SERVICE_TERM_UNIT_MONTH = 1;
    
    /**
     * 服务期限单位 2-年。
     */
    public static final Integer SERVICE_TERM_UNIT_YEAR = 2;
    
    /**
     * 开通方式 3-付款开通。
     */
    public static final Integer OPENING_MODE_PAY = 3;

    
    /**
     * 服务方式 1-按次数服务。
     */
    public static final Integer SERVICE_MODE_TIMES = 1;
    
    /**
     * 服务方式 0-按时间服务 。
     */
    public static final Integer SERVICE_MODE_DATE = 0;
    


    
    /**
     * 付款状态已付款 4。
     */
    public static final Integer PAY_STATUS_PAID = 4;

    /**
     * 付款状态待付款 1;
     */
    public static final Integer PAY_STATUS_PREPARE_TO_PAY = 1;

    /**
     * 重复创单是否关注订单状态 Y：是。
     */
    public static final String CARE_ABOUT_ORDER_STATUS_YES = "Y";

    /**
     * 重复创单是否关注订单状态 N：否。
     */
    public static final String CARE_ABOUT_ORDER_STATUS_NO = "N";

    /**
     * 阿里一达通售卖渠道
     */
    public static final Integer CHANNEL_ALIYDT = 11;

    /*
     * 一达通代付款
     */
    public static final Integer PAY_STATUS_REPLACE = 5;

    /**
     * 支付失败
     */
    public static final Integer PAY_STATUS_FAIL = 6;

    /**
     * 订单的待付款状态
     */
    public static final Integer ORDER_STATUS_PENDING_PAY  = 0;


    /**
     * 订单的待处理状态
     */
    public static final Integer ORDER_STATUS_PREPARE_TO_HANDLE = 1;

    /**
     * 订单的服务中状态
     */
    public static final Integer ORDER_STATUS_SERVICING = 2;

    /**
     * 订单的已完成状态
     */
    public static final Integer ORDER_STATUS_COMPLETED = 3;

    /**
     * 订单的已取消状态
     */
    public static final Integer ORDER_STATUS_CANCELED = 4;


    /**
     * 订单的已终止状态
     */
    public static final Integer ORDER_STATUS_TERMINATION = 5;

    /**
     * 订单的已中止状态
     */
    public static final Integer ORDER_STATUS_STOP = 6;

    /**
     * 服务状态的未启动状态
     */
    public static final Integer SERVICE_STATUS_PREPARE_TO_START  = 0;

    /**
     * 服务状态的服务中状态
     */
    public static final Integer SERVICE_STATUS_SERVICING = 2;

    /**
     * 服务状态的已完成状态
     */
    public static final Integer SERVICE_STATUS_COMPLETED = 3;

    /**
     * 服务状态的已终止状态
     */
    public static final Integer SERVICE_STATUS_TERMINATION = 4;

    /**
     * 服务状态的已取消状态
     */
    public static final Integer SERVICE_STATUS_CANCELED = 5;

    /**
     * 服务状态的已中止状态
     */
    public static final Integer SERVICE_STATUS_STOP = 6;


    /**
     * 设置渠道，金财管家代账（渠道为12）
     */
    public static final Integer FINANCE_AND_TAX_STEWARD = 12;

    /**
     * 付款状态不需要付款 0。
     */
    public static final Integer PAY_STATUS_NOT_PAY = 0;

    public static final String SUBJECT ="金财管家代账版预充值";

    /**
     * 是否父订单-是父订单
     */
    public static final Integer IS_PARENT_TRUE = 0 ;

    /**
     * 不是父订单
     */
    public static final Integer IS_PARENT_FALSE = 1;

    /**
     * 订单事件类型---可用订单
     */
    public static final String ORDER_TYPE_ENABLED ="orderTypeEnabled" ;

    /**
     * 订单事件类型---不可用订单
     */
    public static final String ORDER_TYPE_DISABLED ="orderTypeDisabled";

}
