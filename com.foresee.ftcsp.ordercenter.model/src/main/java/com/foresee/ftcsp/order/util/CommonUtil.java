/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.util;

import com.foresee.ftcsp.common.core.util.Loggers;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

public final class CommonUtil {

    private static Logger logger = Loggers.make();

    /**
     * 位数不够前面补0 TODO。
     * @param code
     * @param num
     * @return String
     */
    public static String autoGenericCode(String code, int num) {
        String result = String.format("%0" + num + "d", Integer.parseInt(code) + 1);
        return result;

    }

    /**
     * 判断传入时间是否在离当前的有效时间内
     * @param timestamp 当前时间戳
     * @param time 有效时间 单位秒
     * @return boolean
     */
    public static boolean ifValidTimestamp(Long timestamp,Long time){
        Long nowTime = new Date().getTime();
        return timestamp+time*1000 > nowTime;
    };

    /**
     * 判断入参的DTO签名验证后是否为有效数据,校验除signFieldName外的所有字段签名是否跟该字段值一致
     * @param obj 校验dto
     * @Param signKey 签名秘钥
     * @param signFieldName 默认字段名为sign
     * @return
     */
    public static boolean ifValidParam(Object obj,String signKey,String signFieldName,String...excludeFieldName){
        signFieldName = signFieldName == null ? "sign" : signFieldName;
        Asserts.notBlank(signKey,"签名密钥key");
        String signValue = null;
        try {
            Field signField = obj.getClass().getDeclaredField(signFieldName);
            signField.setAccessible(true);
            signValue = (String) signField.get(obj);
        } catch (NoSuchFieldException e) {
            logger.error("校验失败,没有找到签名参数!");
            return false;
        }catch (IllegalAccessException e){
            e.printStackTrace();
            return false;
        }
        Map paramMap = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        intoMap:for (Field field : fields) {
            if (signFieldName.equals(field.getName())) {
                continue;
            }else{
                if (excludeFieldName != null && excludeFieldName.length > 0) {
                    for (String name : excludeFieldName) {
                        if (field.getName().equals(name)) {
                            continue intoMap;
                        }
                    }
                }
            }
            try {
                field.setAccessible(true);
                paramMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return signValue.equals(PaySign.genSign(paramMap, signKey));
    }

    /**
     * 同上方法默认签名字段名为sign
     * @param obj
     * @param signKey
     * @return boolean
     */
    public static boolean ifValidParam(Object obj,String signKey,String...excludeFieldName){
        return ifValidParam(obj, signKey, null,excludeFieldName);
    }

}
