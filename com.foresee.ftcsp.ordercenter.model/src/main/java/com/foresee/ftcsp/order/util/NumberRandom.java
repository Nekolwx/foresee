/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.util;

/**
 * <pre>
 * 随机数工具类
 * </pre>
 * 
 * @author zhengyiyuan@foresee.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
import java.util.Random;
import com.foresee.ftcsp.util.Caesar;

public class NumberRandom {

    private static final Random RANDOM        = new Random();

    private static final Caesar NUMBER_CAESAR = new Caesar("0123456789", RANDOM.nextInt(1000));


    public static String getRandom(int n) {// 逐位生成随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('0' + random.nextInt(10)));
        }
        return sb.toString();
    }

    public static String getRandomRecursion(int n) {// 递归调用示例(所产生的随机数一定要包含91)
        String random = getRandom(n);
        if (n > 2 && !random.contains("91")) {
            return getRandomRecursion(n);
        } else {
            return random;
        }
    }

    /**
     * 产生指定长度的随机数
     **/
    public static String randomNumber(int id, int len) {
        int key = RANDOM.nextInt(100000);
        String num = String.format("%0" + len + "d", new Object[] { Integer.valueOf(id) });
        return NUMBER_CAESAR.encode(key, num);
    }

    /**
     * 获取系统编码
     **/
    public static String getSystemCode(String bt) {
        String currentTimeMillis=String.valueOf(System.currentTimeMillis());
        String code = currentTimeMillis.substring(7, 13) + bt + randomNumber(0, 6);// 微秒时间后6位+8位序列+6位随机数=20位数字
        return code;
    }

    /**
     * 循环调用 获取系统编码
     **/
    public static String getSystemCodeRecursion(String bt) {
        String code = getSystemCode(bt);
        if ("0".equals(code.substring(0, 1))) {//如果首位数字包含0，首次替换
            code = getRandom(1).concat(code.substring(1, code.length()));
        }
        if ("0".equals(code.substring(0, 1))) {//如果首次替换失败，则递归调用
            getSystemCodeRecursion(bt);
        }
        return code;
    }

}
