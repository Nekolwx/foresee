/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.outter;

import com.foresee.ftcsp.common.core.rest.client.FtcspRestClient;
import com.foresee.ftcsp.common.core.util.Jackson;
import com.foresee.ftcsp.common.core.util.JsonUtils;
import com.foresee.ftcsp.order.util.PaySign;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsAttributeDTO;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.*;


/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author foresee@foresee.com.cn
 * @date 2017年8月24日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class OrderResourceTest{

    @Test
    public void test() {
        String url = "http://10.10.7.13:9009/gateway/ordercenter/outter/orderService/createOrder";

        OrderDTO order = new OrderDTO();
        order.setCusId("988887737371");
        order.setChannel("1");
        order.setCusType("0");
        order.setCusName("黄昭");
        order.setCusIdentificationNumber("430722198909204772");
        order.setUserId("000000000000");
        order.setDividePay("0");
        order.setNeedInvoice("0");
        order.setBusinessOrderNo("99983883830");
        order.setBuyerType("0");
        order.setBuyerTaxpayerNo("430722219880920");
        order.setBuyerName("黄昭");
        order.setBuyerAddressPhone("1398777777");
        order.setFreight("1111");
        order.setReceiver("吴杰聪");
        order.setReceiverPhone("1380000000");
        order.setReceiverAddress("3345566");
        List<GoodsDTO> goods = new ArrayList<GoodsDTO>();
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setGoodsSku("123123123");
        goodsDTO.setGoodsQuantiy("2");
        List<GoodsAttributeDTO> attributeList=new ArrayList<GoodsAttributeDTO>();
        GoodsAttributeDTO attribute=new GoodsAttributeDTO();
        attribute.setAttributeId("000000001");
        attribute.setAttributeValueId("000000001");
        attribute.setAttributeValue("红色");
        attributeList.add(attribute);
        goodsDTO.setGoodsAttributeGroup(attributeList);
        goods.add(goodsDTO);
        GoodsDTO goodsDTO1 = new GoodsDTO();
        goodsDTO1.setGoodsSku("123123123");
        goodsDTO1.setGoodsQuantiy("1");
        goods.add(goodsDTO1);
        order.setGoodsGroup(goods);
        String data = JsonUtils.toJson(order);
        System.out.println(data);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data))
            System.out.println("返回信息列表===" + client.getResponseContent());
    }

    @Test
    public void setAccountantInfo() {
        String url = "http://prpt.jchl.com/gateway/ordercenter/outter/orderService/setAccountantInfo";
        HashMap param = new HashMap();
        param.put("businessOrderNo","jcdz_201708210909991");
        param.put("channel", "2");
        param.put("accountingSupervisorId", null);
        param.put("accountingAssistantId", "009ee844a3d24991846c3b17c73068c1");
        param.put("factoryId", "009ee844a3d24991846c3b17c73068c1");
        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }
    }

    @Test
    public void createRewardOrder(){
        //String url = "http://tpt.jchl.com/gateway/ordercenter/outter/orderService/createRewardOrder";
        String url = "http://183.62.253.122:9001/gateway/ordercenter-huangzekun/outter/orderService/createRewardOrder";
       HashMap param = new HashMap();
       param.put("cusType","1");
       param.put("cusName", "红领巾五道杠");
       param.put("userId", "2cb42659a75b43689995b3ce678ca256");
       param.put("taxIdentification", "15818431256");
       param.put("businessOrderNo", "31222114544181217");
       param.put("channel", "4");
       param.put("payAmount", "6000");
       //param.put("goodsSkuId", "25205542045684835");
       param.put("goodsSkuId", "25205542045684835");

       param.put("payRetUrl", "http://www.hao123.com");
       param.put("payNotifyUrl", "http://www.hao123.com");
       String[] supportPayWays = {};


       param.put("supportPayWays", supportPayWays);

        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }


    }


    @Test
    public void createInvoiceOrder(){
        //String url = "http://tpt.jchl.com/gateway/ordercenter-huangzekun/outter/orderService/createRewardOrder";
        String url = "http://183.62.253.122:9001/gateway/ordercenter/outter/orderService/createRewardOrder";
       HashMap param = new HashMap();
       param.put("cusType","1");
       param.put("cusName", "幼儿园扛把子");
       param.put("userId", "2cb42659a75b43689995b3ce678ca256");
       param.put("taxIdentification", "15818431256");
       param.put("businessOrderNo", "InvoiceTest11271724");
       param.put("businessOrderNo", "InvoiceTest11271748");
       param.put("businessOrderNo", "InvoiceTest11281017");
       param.put("businessOrderNo", "InvoiceTest11290922");
       param.put("channel", "4");
       param.put("payAmount", "3000");
       param.put("goodsSkuId", "116209334136709128");
       //param.put("goodsSkuId", "25205542045684840");
       param.put("payRetUrl", "http://www.hao123.com");
       param.put("payNotifyUrl", "http://www.hao123.com");

        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }


    }

    @Test
    public void mobilePayEntrance(){
        //String url = "http://tpt.jchl.com/gateway/ordercenter/outter/payService/mobilePayEntrance";
        String url = "http://183.62.253.122:9001/gateway/ordercenter-liweixin/outter/payService/mobilePayEntrance";
       HashMap param = new HashMap();
       param.put("cusType","1");
       param.put("cusName", "红领巾五道杠");
       param.put("userId", "2cb42659a75b43689995b3ce678ca256");
       param.put("taxIdentification", "15818431256");
      // param.put("cusId","0000023cb03b4346b5d947f7d00bces");
       param.put("businessOrderNo", "662122018011711257");
       param.put("channel", "0");
       param.put("payAmount", "1.00");
       //param.put("goodsSkuId", "25205542045684835");
       param.put("goodsSkuId","25205542045684840");
       param.put("payAppId", "95102728510398467");
       String[] supportPayWays = {"2","4","6"};
       param.put("supportPayWays", supportPayWays);
       //param.put("goodsSkuId", "25205542045684840");
       param.put("payRetUrl", "http://tpt.jchl.com");
       param.put("payNotifyUrl", "http://www.hao123.com");
       param.put("payClientIp","183.62.253.122");
       param.put("payOpenId","ovsSawxk73B_jASkVxtxnVJk8Fmo");
       param.put("payFailureUrl","http://183.62.253.122:9001/admin/main/index.html");
        param.put("goodsQuantity", "1");

        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }


    }
    
    @Test
    public void queryInvoiceStatus(){
        //String url = "http://tpt.jchl.com/gateway/ordercenter/outter/orderService/createRewardOrder";
        String url = "http://183.62.253.122:9001/gateway/ordercenter-huangzekun/outter/invoiceService/queryInvoiceStatus";
       HashMap param = new HashMap();
       //  9b158c899a77449f93e495898cb47b48
      /* param.put("businessOrderNo","20170928153715016030495");
       param.put("channel", "4");*/


       /*param.put("businessOrderNo","2013310017720133100177");
       param.put("channel", "1");
       param.put("billCode", "1");*/
       param.put("businessOrderNo","InvoiceTest11291524");
       param.put("channel", "4");


        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }


    }



    @Test
    public void testGetPayParam(){
        String url = "http://183.62.253.122:9001/gateway/ordercenter-huangzekun/outter/payService/getPayParam";
        HashMap param = new HashMap();
        
        
        
        param.put("pay_body","微信代开申请税款");
        param.put("pay_amount","6.55");
        param.put("pay_account_id","18598574191280128");
        param.put("pay_notify_url","http://ordercenter/inner/payService/payCallBack");
        param.put("pay_timestamp",String.valueOf(new Date().getTime()));
        param.put("pay_invalid_time",String.valueOf(new Date().getTime()));
        param.put("pay_subject","代征税额");
        param.put("pay_order_time",String.valueOf(new Date().getTime()));
        param.put("pay_biz_order_id","ZK2017082400000056");
        param.put("pay_app_id","18517550706982912"); 
        param.put("pay_ret_url","http://183.62.253.122:6879/dkdz/?dqdm=14419");
        param.put("pay_client_ip","183.62.253.122");
        param.put("pay_open_id","ovsSawxk73B_jASkVxtxnVJk8Fmo");
        param.put("pay_api_version","1.0");
        param.put("pay_show_url","http://test.dzfpyun.com/dkdz-dgdzp/");
        param.put("pay_channel_id","18516951559045120");
        String sign = PaySign.genSign(param, "tHeGIJmLcaBiK6QC");
        param.put("pay_sign",sign);
        
        
        param.put("is_Enabled_order","1");
        param.put("pay_project","XXXX");
        param.put("cus_name", "Luffy");
        param.put("cus_type", 1);
        param.put("tax_identification", "20133100100");
        param.put("mobile_phone", "15813585143");
        param.put("cus_address", "中国广东");
        param.put("source","4");
        param.put("goods_sku_id", 92620769347526660L);
        
        
        
        
        
        
        
        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {

            System.out.println(client.getResponseCode()+client.getResponseContent());
        }
    }

    @Test
    public void queryOrder(){
        String url = "http://183.62.253.122:9001/gateway/ordercenter-huangzekun/outter/orderService/queryOrder";
        HashMap param = new HashMap();
        param.put("businessOrderNo","66666663");
        param.put("channel", "4");

        String data = Jackson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }


    }
    
    @Test
    public void testCreateSynchronizedOrder(){
        
        String url ="http://tpt.jchl.com/gateway/ordercenter/outter/orderService/createSynchronizedOrder";
        //String url = "http://183.62.253.122:9001/gateway/ordercenter-huangzekun/outter/orderService/createSynchronizedOrder";
        Map<String,Object> order1 = new HashMap<String,Object>();
        Map<String,Object> order2 = new HashMap<String,Object>();
        order1.put("customerId","0000202a359a490abb23b0bef7b1ce941");
        order1.put("orderCode", "scnu20133100001");
        order1.put("goodsId","111222333456421");
        order1.put("skuId","111222333456421");
        order1.put("areaCode","000000");
        order1.put("payAmount", "46.55");
        order1.put("serviceStartDate",new Date());
        order1.put("serviceEndDate",new Date());
        order1.put("payWay","4");
        order1.put("payTime", new Date());
        order1.put("orderStatus","0");
        order1.put("payStatus", "0");
        order1.put("refundStatus","0");
        order1.put("serviceStatus", "0");
        order1.put("channel","6");
        order1.put("contactPerson", "huangzekun");
        order1.put("channelOption","0");
        
        System.out.println(new Date().getTime());
        
        
        
        
        order2.put("customerId","937bd4eb8c65443b95817e6ae68bc3d01");
        order2.put("orderCode", "scnu20133100002");
        order2.put("goodsId","111222333456421");
        order2.put("skuId","111222333456421");
        order2.put("areaCode","000000");
        order2.put("payAmount", "46.55");
        order2.put("serviceStartDate",new Date());
        order2.put("serviceEndDate",new Date());
        order2.put("payWay","4");
        order2.put("payTime", new Date());
        order2.put("orderStatus","0");
        order2.put("payStatus", "0");
        order2.put("refundStatus","0");
        order2.put("serviceStatus", "0");
        order2.put("channel","6");
        order2.put("contactPerson", "huangzekun");
        order2.put("channelOption","0");
       
        List<Map<String,Object>> synchronizedOrderList = new ArrayList<Map<String,Object>>();
        synchronizedOrderList.add(order1);
        synchronizedOrderList.add(order2);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("synchronizedOrderList",synchronizedOrderList);
        String data = Jackson.toJson(param);
        System.out.println(data);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            System.out.println(client.getResponseContent());
        }
    }



    @Test
    public void testQueryOrder(){

        String url ="http://pt.jchl.com/gateway/pay/outter/payService/query";
        //String url = "http://183.62.253.122:9001/gateway/ordercenter-huangzekun/outter/orderService/createSynchronizedOrder";


        HashMap param = new HashMap();
        Date now = new Date();

        param.put("pay_timestamp",String.valueOf(now.getTime()));
        param.put("pay_api_version","1.0");
        param.put("pay_app_id","18517550706982912");
        param.put("pay_biz_order_id","00050120171030175710755288");
        String sign = PaySign.genSign(param, "tHeGIJmLcaBiK6QC");
        param.put("pay_sign",sign);
        Gson gson = new Gson();
        String data = gson.toJson(param);
        FtcspRestClient client = new FtcspRestClient("000106", "yu1qoj8kyu1qoj8k", "6ECD141F8B991FB2616214018D9BA32F");
        if (client.post(url, data)) {
            String responseContent = client.getResponseContent();
            Map<String, Object> map = new HashMap<String, Object>();
            map = gson.fromJson(responseContent, map.getClass());

            System.out.println(map.get("body"));
        }
    }
}
