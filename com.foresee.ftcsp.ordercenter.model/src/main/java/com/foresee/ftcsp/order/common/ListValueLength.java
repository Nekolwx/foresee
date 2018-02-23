package com.foresee.ftcsp.order.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * </pre>
 *
 * @author jiangrunqiao@foresee.com.cn
 * @version 1.00.00
 * @date 2017/8/14
 * <p>
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
        ListNumberValueValidator.class,
        ListStringValueValidator.class
})
public @interface ListValueLength {

    String message() default "{ListValueLength.message}";

    int minIngeter() default 0;

    int maxIngeter() default 0;

    int minFraction() default 0;

    int maxFraction() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

//    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
}
