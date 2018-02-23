/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */

package com.foresee.ftcsp.order.rest;
import com.foresee.ftcsp.common.core.dict.annotation.EnableFtcspDictClient;
import com.foresee.ftcsp.ftcspmvc.config.annotation.EnableFtcspMVC;
import com.foresee.ftcsp.order.rest.mq.QueueInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * <pre>
 * 订单中心模块应用程序启动类。。
 * </pre>
 *
 * @author linxj@foresee.com.cn
 * @date 2017年08月03日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@SpringBootApplication(scanBasePackages = { "com.foresee.ftcsp.*.*" })
@EnableEurekaClient
@EnableFtcspMVC
@EnableFtcspDictClient
public class OrderApplication implements CommandLineRunner{
	
    @Autowired
    private QueueInitializer queueInitializer;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        queueInitializer.init();
    }


}
