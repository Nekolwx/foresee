/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.constant;

/**
 * <pre>
 * 支付常量类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public abstract class PayConstant {
    
    /**
     * 支付方式 0-支付宝
     */
    public static final int PAYWAY_ALIPAY = 0;
    
    /**
     * 支付方式 1-微信支付 
     */
    public static final int PAYWAY_WECHAT = 1;
    
    /**
     * 支付方式 2-银联。
     */
    public static final int PAYWAY_UNION = 2;
    
    /**
     * 支付方式 3-现金。
     */
    public static final int PAYWAY_CASH = 3;
    
    /**
     * 支付方式 4-财币支付。
     */
    public static final int PAYWAY_CAIBI = 4;
    
    /**
     * 支付方式 5-公众号支付。
     */
    public static final int PAYWAY_PUBLIC = 5;
    
    /**
     * 支付方式 6-支付宝h5手机端支付
     */
    public static final int PAYWAY_ALIPAY_MOBILE = 6;
    
    /**
     * 支付方式 7-微信h5手机端支付
     */
    public static final int PAYWAY_WECHAT_MOBILE = 7;


    /**
     * 支付方式 10-阿里一达通
     */
    public static final int PAYWAY_ALIYDT = 10;
    
    /**
     * 平台支付回调接口返回的成功支付状态。
     */
    public static final String CALLBACK_PAY_STATUS_SUCCESS = "success";
    
    /**
     * 平台支付回调接口返回的失败支付状态。
     */
    public static final String CALLBACK_PAY_STATUS_FAIL = "fail";
    
    /**
     * 支付平台返回的支付状态 SUCCESS:支付成功。
     */
    public static final String PAY_STATUS_SUCCESS = "SUCCESS";
    
    /**
     * 支付平台返回的支付状态 FINISHED:支付成功，订单完成、不能退款。
     */
    public static final String PAY_STATUS_FINISHED = "FINISHED";
    
    /**
     * 支付流水表支付状态， 1-支付成功。
     */
    public static final int FLOWING_PAY_STATUS_SUCCESS = 1;

    /**
     * 支付流水表支付状态， 0-待支付。
     */
    public static final Integer FLOWING_PAY_STATUS_PREPARE_TO_PAY = 0;

    /**
     * 支付流水表支付状态，3-代支付
     */
    public static final Integer FLOWING_PAY_STATUS_REPLACE_PAY = 3;

    /**
     * 支付流水表支付状态， 2-支付失败。
     */
    public static final Integer FLOWING_PAY_STATUS_FAIL = 2;

}
