package com.foresee.ftcsp.order.constant;

/**
 * <pre>
 * 加密签名钥匙常量
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/10/12
 */
public interface SignEncryptConstant {

    String RewardOrderPaySalt = "vWMR9gycw0GgMC3a";

    String RechargeCoinPaySalt = "907d1bdaa34c4e22a56bbe2066b60819";

    /**
     * 手机端发起支付参数验证签名
     */
    String MoblilePaymentValidateSalt = "e76ef62ff9c511e7910936a7a33c449d";
}
