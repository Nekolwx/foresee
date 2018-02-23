package com.foresee.ftcsp.order.rest.ui.back;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.JsonUtils;
import com.foresee.ftcsp.order.manual.restdto.AddOrdOrdeReceivablesrDTO;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderDetailsDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderParentAndChildDTO;
import com.foresee.ftcsp.order.rest.BaseTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * @date 2017年08月23日
 *       <p>
 * 
 *       <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 *       </pre>
 */
public class OrderBackResourceTest extends BaseTest {

    @LocalServerPort
    private int port;
   
    /**
     * 订单分页查询接口测试类
     * @author chenzexin@foresee.com.cn
     */
    @Test
    public void queryOrderList() throws Exception {
        //QueryOrderListDTO q=new QueryOrderListDTO(); 
        //q.setOrderId(new Long("85393481128218631"));
        //q.setBillingStatus(0);
        //q.setPayStatus(1);
        //q.setAreaCode("  ");
        //q.setAreaCode("440100");
        //q.setOrderId(new Long("85332187863846917"));
        Map<String,String> map = new HashMap<>();
        map.put("orderId", "84329819324026882");
        
        ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity("/back/orderService/queryOrderList", map, RestResponse.class);
        Object body = responseEntity.getBody().getBody();
        System.out.println(body);
    }
    
    @Test
    public void queryOrderDetails(){
    	QueryOrderDetailsDTO queryOrderDetailsDTO=new QueryOrderDetailsDTO();
    	queryOrderDetailsDTO.setOrderId(Long.valueOf(7777));
    	ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity("/back/orderService/queryOrderDetails", queryOrderDetailsDTO, RestResponse.class);
        Object body = responseEntity.getBody().getBody();       
        System.out.println(body);
    }
    
    @Test
    public void queryOrderParentAndChild(){
    	QueryOrderParentAndChildDTO queryOrderParentAndChild=new QueryOrderParentAndChildDTO();
    	queryOrderParentAndChild.setOrderId(Long.valueOf(6666));
    	ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity("/back/orderService/queryOrderParentAndChild", queryOrderParentAndChild, RestResponse.class);
        Object body = responseEntity.getBody().getBody();       
        System.out.println(body);
    }
    
    /**
     * 订单状态修改接口测试类
     * @author chenzexin@foresee.com.cn
     */
    @Test
    public void updateOrderStatus() throws Exception {
      /*  UpdateOrderStatusDTO data=new UpdateOrderStatusDTO();
        data.setOrderId(new Long("84314884619636745"));
        data.setOrderStatus(5);
        data.setServiceStatus(5);*/
        
        Map<String,String> data = new HashMap<>();
        data.put("orderId", "84314884619636745");
        data.put("orderStatus", " 2");
        data.put("serviceStatus", "4");
        ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity("/back/orderService/updateOrderStatus",data, RestResponse.class);
        Object body = responseEntity.getBody().getBody();
        System.out.println(body);
    }
    
    /**
     * 订单状态修改接口测试类
     * @author chenzexin@foresee.com.cn
     */
    @Test
    public void AddOrdOrdeReceivablesr() throws Exception {
        AddOrdOrdeReceivablesrDTO data=new AddOrdOrdeReceivablesrDTO();
        data.setOrderId(new Long("84329819324026882"));
        data.setPayWay(1);
        data.setPayAmount(new BigDecimal("10000"));
        data.setPayer("周");
        data.setPayerAccount("111222333");
        data.setPayerPhone("1234567");
        data.setPayTime(new Date());
        data.setReceiptAccount("555777888");
        data.setReceiptSide("李");
      /*  Map<String,String> data = new HashMap<>();
        data.put("orderId", "84314884619636745");
        data.put("orderStatus", " 2");*/
        //data.put("serviceStatus", "");
        ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity("/back/orderService/AddOrdOrdeReceivablesr",data, RestResponse.class);
        Object body = responseEntity.getBody().getBody();
        System.out.println(body);
    }
    
    @Test
    public void createOrder() throws Exception {
        OrderDTO order = new OrderDTO();
        order.setCusId("988887737371");
        order.setChannel("1");
        order.setCusType("0");
        order.setCusName("黄昭");
        order.setCusIdentificationNumber("430722198909204772");
        order.setUserId("000000000000");
        order.setDividePay("0");
        order.setNeedInvoice("0");
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
        goodsDTO.setGoodsSku("sp0000000000000000000062");
        goodsDTO.setGoodsQuantiy("2");
       /* List<GoodsAttributeDTO> attributeList=new ArrayList<GoodsAttributeDTO>();
        GoodsAttributeDTO attribute=new GoodsAttributeDTO();
        attribute.setAttributeId("000000001");
        attribute.setAttributeValueId("000000001");
        attribute.setAttributeValue("红色");
        attributeList.add(attribute);
        goodsDTO.setGoodsAttributeGroup(attributeList);*/
        goods.add(goodsDTO);
        order.setGoodsGroup(goods);
        String data = JsonUtils.toJson(order);
        System.out.println(data);
        RestResponse post = testRestTemplate.postForEntity("/back/orderService/addOrder", order, RestResponse.class).getBody();
        System.out.println(post);
    }
   
}