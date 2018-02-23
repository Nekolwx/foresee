package com.foresee.ftcsp.order.dynamicdb;

import com.foresee.ftcsp.common.database.conf.CustomSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  表字段的下换线自动转为Java的字段
 * </pre>
 *
 * @author chenwenlong@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月05日
 * <p>
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
@Component
public class OrderCustomSqlSessionFactory implements CustomSqlSessionFactory {
    @Override
    public void accept(SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
            sqlSessionFactoryBean
                    .getObject()
                    .getConfiguration()
                    .setMapUnderscoreToCamelCase(true);
    }
}
