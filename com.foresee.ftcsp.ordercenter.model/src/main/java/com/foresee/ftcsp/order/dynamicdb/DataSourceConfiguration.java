/*
* Copyright（c） Foresee Science & Technology Ltd.
*/

package com.foresee.ftcsp.order.dynamicdb;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <pre>
 * 数据库配置
 * </pre>
 *
 * @author chenwenlong chenwenlong@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月04日
 * <p>
 * <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 * </pre>
 */
@Configuration
public class DataSourceConfiguration {

    @Bean(name = DataSourceConstants.REPORT)
    @Qualifier(DataSourceConstants.REPORT)
    @ConfigurationProperties(prefix = "report.druid.datasource")
    public DataSource reportDataSource() {
        return new DruidDataSource();
    }


}
