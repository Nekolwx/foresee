package com.foresee.ftcsp.order.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author jiangrunqiao@foresee.com.cn
 * @version 1.00.00
 * @date 2017/8/15
 * <p>
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
public class ListStringValueValidator implements ConstraintValidator<ListValueLength, List<String>> {
    private int minIngeter;
    private int maxInteger;

    @Override
    public void initialize(ListValueLength constraintAnnotation) {
        this.minIngeter = constraintAnnotation.minIngeter();
        this.maxInteger = constraintAnnotation.maxIngeter();
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        return value.stream()
                .allMatch(v -> v.length() >= minIngeter
                        && v.length() <= maxInteger);
    }
}
