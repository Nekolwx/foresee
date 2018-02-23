/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.outter;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.foresee.ftcsp.common.core.rest.client.FtcspRestClient;
import com.foresee.ftcsp.common.core.util.Jackson;

/**
 * <pre>
 * 发票外部接口测试类
 * </pre>
 *
 * @author yangchengjun@foresee.com.cn
 * @date 2017年11月27日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class InvoiceManageResourceTest {

    @Test
    public void testIssueElectronicInvoice(){
        String url = "http://183.62.253.122:9001/gateway/ordercenter-yangchengjun/outter/invoiceService/issueElectronicInvoice";
        Map<String, String> param = new HashMap<>();
        param.put("businessOrderNo","InvoiceTest11291444");
        param.put("billCode","");
        param.put("buyerName","测试开票");
        param.put("buyerTaxpayerNo","441120171127test");
        param.put("buyerType","1");
        param.put("buyerPhone","");
        param.put("buyerAddress","1111111");
        param.put("buyerMobile","");
        param.put("buyerBankAccount","");
        param.put("buyerBankName","");
        param.put("billingType","1");
        param.put("originalInvoiceCode","044016715148");
        param.put("originalInvoiceNo","84095149");
        param.put("channel","4");
        String data = Jackson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }
    }
    
    @Test
    public void testIssueElectronicInvoice2(){
        String url = "http://183.62.253.122:9001/gateway/ordercenter-yangchengjun/outter/invoiceService/issueElectronicInvoice";
        Map<String, String> param = new HashMap<>();
        param.put("businessOrderNo","InvoiceTest11300937");
        param.put("billCode","");
        param.put("buyerName","测试开票");
        param.put("buyerTaxpayerNo","441120171127test");
        param.put("buyerType","1");
        param.put("buyerPhone","");
        param.put("buyerAddress","1111111");
        param.put("buyerMobile","");
        param.put("buyerBankAccount","");
        param.put("buyerBankName","");
        param.put("billingType","1");
        param.put("originalInvoiceCode","044016715148");
        param.put("originalInvoiceNo","83052111");
        param.put("channel","4");
        String data = Jackson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }
    }
    
    @Test
    public void testQueryInvoiceStatus(){
        String url = "http://183.62.253.122:9001/gateway/ordercenter/outter/invoiceService/queryInvoiceStatus";
        Map<String, String> param = new HashMap<>();
        param.put("businessOrderNo","InvoiceTest11291444");
        param.put("channel","4");
        String data = Jackson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }
    }
    
}
