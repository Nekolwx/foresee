/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.util;

import java.util.*;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * <pre>
 * 支付参数签名、验签。
 * </pre>
 *
 * @author zhouyi@foresee.com.cn
 * @version 1.00.00
 *          <p>
 * 
 *          <pre>
 *          修改记录
 *             修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 * 
 * @date 2017年03月17日
 */

public final class PaySign {

    /**
     * 生成签名
     * @param params
     * @param salt
     * @return
     */
    public static String genSign(Map<String, String> params, String salt) {
        Map<String, String> perData = paramsFilter(params);
        String perSign = createLinkString(perData);
        return DigestUtils.md5Hex(perSign + salt);
    }

    /**
     * 校验签名
     * @param params
     * @param salt
     * @return
     */
    public static boolean checkSign(Map<String, String> params, String salt) {
        if (params.containsKey("pay_sign")) {
            String sign = params.get("pay_sign");
            params.remove("pay_sign");
            String genSign = genSign(params, salt);
        
            return sign.equals(genSign);
        }
        return false;
    }

    /**
     * 生成当前时间戳
     * @return 当前时间戳
     */
    private static String genTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 把所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params
     * @return
     */
    private static String createLinkString(Map<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            buffer.append(key);
            buffer.append("=");
            buffer.append(params.get(key));
            if (iterator.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * 除去数组中的空值和签名参数
     * @param params 签名参数
     * @return 去掉空值与签名参数后的新签名参数
     */
    public static Map<String, String> paramsFilter(Map<String, String> params) {
        Map<String, String> result = new HashMap<>();
        if (params == null || params.size() <= 0) {
            return result;
        }
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null || "".equals(key) || key.equalsIgnoreCase("pay_sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

}
