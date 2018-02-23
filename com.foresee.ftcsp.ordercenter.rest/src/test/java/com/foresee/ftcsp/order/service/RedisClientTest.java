package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.common.core.redis.IRedisClient;
import com.foresee.ftcsp.common.core.util.Jackson;
import com.foresee.ftcsp.common.core.util.JsonUtils;
import com.foresee.ftcsp.order.rest.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/9/28
 */
public class RedisClientTest extends BaseTest {

    @Autowired
    IRedisClient redisClient;

    @Test
    public void setNxTest(){
        redisClient.setNx("wait", "wait", 5);
        boolean result = redisClient.setNx("wait","wait",5);
        System.out.println("------------------->"+result);
        Assert.assertFalse(result);

    }

    public static void main(String[] args) {
        BigDecimal dec = new BigDecimal(666.01);
        Map<String,Object> teMap = new HashMap();
        teMap.put("bigDecimal", dec);
        String s = Jackson .toJson(teMap);
        System.out.println(s);
        Map d = Jackson.fromJson(s,Map.class);
        System.out.println(d);
    }
}
