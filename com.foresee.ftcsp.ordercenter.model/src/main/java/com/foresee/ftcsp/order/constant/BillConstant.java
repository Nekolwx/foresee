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

public abstract class BillConstant {

    /**
     * 账单类型（0-全额）
     */
    public static final Integer BILL_TYPE_FULL                   = 0;

    /**
     * 账单类型（1-分期）
     */
    public static final Integer BILL_TYPE_STAGES                 = 1;

    /**
     * 账单类型（2-按量）
     */
    public static final Integer BILL_TYPE_VOLUME                 = 2;

    /**
     * 支付状态（0-已付）。
     */
    public static final Integer BILL_PAY_STATUS_ALREADY          = 0;

    /**
     * 支付状态（1-待付）。
     */
    public static final Integer BILL_PAY_STATUS_PENDING          = 1;

    /**
     * 支付状态（2-失败）。
     */
    public static final Integer BILL_PAY_STATUS_FAIL             = 2;

    /**
     * 开票状态（0 未开 ）
     */
    public static final Integer BILL_INVOICE_STATUS_NOT_BLUE     = 0;

    /**
     * 开票状态（1 已开票）
     */
    public static final Integer BILL_INVOICE_STATUS_BLUE_ALREADY = 1;

    /**
     * 开票状态（2 不需要开票）
     */
    public static final Integer BILL_INVOICE_STATUS_NOT_NEED     = 2;

    /**
     * 开票状态（3 开票中）
     */
    public static final Integer BILL_INVOICE_STATUS_BLUE_ING     = 3;

    /**
     * 开票状态（ 4 开票失败）
     */
    public static final Integer BILL_INVOICE_STATUS_BLUE_FAIL    = 4;

    /**
     * 开票状态（5 红冲中）
     */
    public static final Integer BILL_INVOICE_STATUS_RED_ING      = 5;

    /**
     * 开票状态（6 已红冲）
     */
    public static final Integer BILL_INVOICE_STATUS_RED_ALREADY  = 6;

    /**
     * 开票状态（7 红冲失败）
     */
    public static final Integer BILL_INVOICE_STATUS_RED_FAIL     = 7;

}
