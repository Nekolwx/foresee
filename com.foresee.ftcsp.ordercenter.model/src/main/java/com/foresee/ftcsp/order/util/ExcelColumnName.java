package com.foresee.ftcsp.order.util;

import java.lang.annotation.*;

/**
 * 注明对应excel字段的注解
 *
 * @author liweixin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumnName {
    String value(); //对应模板文件的字段说明

    int index() default  0; //输出列数下标;
}
