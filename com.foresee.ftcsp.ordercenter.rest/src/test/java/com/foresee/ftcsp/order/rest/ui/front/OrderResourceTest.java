package com.foresee.ftcsp.order.rest.ui.front;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.JsonUtils;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsAttributeDTO;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import com.foresee.ftcsp.ordercenter.api.dto.OrderDTO;
import com.foresee.ftcsp.order.rest.BaseTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author huangzhao@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月14日
 *       <p>
 * 
 *       <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 *       </pre>
 */
public class OrderResourceTest extends BaseTest {

    @LocalServerPort
    private int port;


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
        List<GoodsAttributeDTO> attributeList=new ArrayList<GoodsAttributeDTO>();
        GoodsAttributeDTO attribute=new GoodsAttributeDTO();
        attribute.setAttributeId("000000001");
        attribute.setAttributeValueId("000000001");
        attribute.setAttributeValue("红色");
        attributeList.add(attribute);
        goodsDTO.setGoodsAttributeGroup(attributeList);
        goods.add(goodsDTO);
        order.setGoodsGroup(goods);
        String data = JsonUtils.toJson(order);
        System.out.println(data);
        RestResponse post = testRestTemplate.postForEntity("/front/orderService/createOrder", order, RestResponse.class).getBody();
        System.out.println(post);
    }
    
    @Test
    public void queryPersonalOrderPage() throws Exception {
        Map<String,Object> testMap=new HashMap<>();
        testMap.put("userId", "000000000000");
        RestResponse post = testRestTemplate.postForEntity("/front/orderService/queryPersonalOrderPage", testMap, RestResponse.class).getBody();
    }
        
}