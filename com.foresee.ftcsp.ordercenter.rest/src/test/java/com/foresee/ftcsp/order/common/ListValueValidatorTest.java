package com.foresee.ftcsp.order.common;

import com.foresee.ftcsp.ftcspmvc.validation.Validators;
import com.foresee.ftcsp.order.common.ListValueLength;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.lang.reflect.Method;
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
public class ListValueValidatorTest {
    public void function(@ListValueLength(minIngeter = 3, maxIngeter = 4, minFraction = 2, maxFraction = 3)List<Double> values) {

    }
    @Test
    public void test() {
        try {
            Method function = ListValueValidatorTest.class.getMethod("function", List.class);
            List<Double> values = Lists.newArrayList();
            values.add(1115.12);
            values.add(121.123);
            Validators.validateParameters(new ListValueValidatorTest(), function, values);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
