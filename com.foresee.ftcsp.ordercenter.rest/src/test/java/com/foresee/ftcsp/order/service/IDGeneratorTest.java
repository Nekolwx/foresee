package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.order.rest.OrderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by foresee on 2017-08-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=OrderApplication.class)
public class IDGeneratorTest {

   @Resource
   IdGenerator idGenerator;


   @Test
   public void testIdGenerator(){
      System.out.println(System.currentTimeMillis());
      System.out.println("XXXXXXXXXXXXXXXXXXXXx");
      System.out.println(idGenerator.getLong());
   }
}
