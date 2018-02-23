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
 * @date 2017/8/14
 * <p>
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
public class ListNumberValueValidator implements ConstraintValidator<ListValueLength, List<? extends Number>> {
    private int minInteger;
    private int maxInteger;
    private int minFraction;
    private int maxFraction;
    private boolean isIntegerMin = true;
    private boolean isIntegerMax = true;
    private boolean isFractionMin = true;
    private boolean isFractionMax = true;

    @Override
    public void initialize(ListValueLength constraintAnnotation) {
        this.minInteger = constraintAnnotation.minIngeter();
        this.maxInteger = constraintAnnotation.maxIngeter();
        this.minFraction = constraintAnnotation.minFraction();
        this.maxFraction = constraintAnnotation.maxFraction();
    }

    @Override
    public boolean isValid(List<? extends Number> value, ConstraintValidatorContext context) {
        if (minInteger > 0) {
            isIntegerMin = value.stream().allMatch(v -> {
                String s = String.valueOf(v).split("\\.")[0];
                return s.length() >= minInteger;
            });
        }
        if (maxInteger > 0) {
            isIntegerMax = value.stream().allMatch(v -> {
                String s = String.valueOf(v).split("\\.")[0];
                return s.length() <= maxInteger;
            });
        }
        if (minFraction > 0) {
            isFractionMin = value.stream().allMatch(v -> {
                if (!String.valueOf(v).contains(".")) {
                    return false;
                }
                String s = String.valueOf(v).split("\\.")[1];
                return s.length() >= minFraction;
            });
        }
        if (maxFraction > 0) {
            isFractionMax = value.stream().allMatch(v -> {
                if (!String.valueOf(v).contains(".")) {
                    return true;
                }
                String s = String.valueOf(v).split("\\.")[1];
                return  s.length() <= maxFraction;
            });
        }
        return isIntegerMin && isIntegerMax && isFractionMin && isFractionMax;
    }

}
