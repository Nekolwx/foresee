package com.foresee.ftcsp.order.rest.ui.back;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.order.rest.BaseTest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.ResponseEntity;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月31日
 *       <p>
 * 
 *       <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 *       </pre>
 */
public class InvoiceBackRecourceTest extends BaseTest {

    @LocalServerPort
    private int port;
   
    /**
     * 开票分页查询接口测试类
     * @author chenzexin@foresee.com.cn
     */
    @Test
    public void queryOrdInvoiceList() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("orderCode", "1");
        map.put("cusName", "新会");
        map.put("invoiceCode", "dai");
        map.put("invoiceNumber", "1");
        map.put("invoiceFormality", "1");
        map.put("billingType", "1");
        
        map.put("isRed", "Y");
        ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity("/back/orderService/queryOrdInvoiceList", map, RestResponse.class);
        Object body = responseEntity.getBody().getBody();
        System.out.println(body);
    }
}