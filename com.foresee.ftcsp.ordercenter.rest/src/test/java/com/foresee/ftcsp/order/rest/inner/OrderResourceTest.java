package com.foresee.ftcsp.order.rest.inner;

import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResultDefaultImpl;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.customer.api.inner.CustomerInnerApi;
import com.foresee.ftcsp.customer.manual.restdto.QueryUserDepCusParamDTO;
import com.foresee.ftcsp.customer.manual.restdto.QueryUserDepCusRequestDTO;
import com.foresee.ftcsp.ordercenter.api.dto.*;
import com.foresee.ftcsp.order.rest.BaseTest;
import com.foresee.ftcsp.order.rest.BaseTest;
import com.foresee.ftcsp.order.util.PaySign;
import com.foresee.ftcsp.ordercenter.api.dto.*;
import com.foresee.ftcsp.ordercenter.api.inner.OrdercenterApi;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class OrderResourceTest extends BaseTest {
    @Autowired
    private OrdercenterApi ordercenterApi;

    @Autowired
    private CustomerInnerApi customerInnerApi;

    @Test
    public void changOrderStatusByCusIdAndChannel(){
        ChangOrderStatusByCusIdAndChannelDTO changOrderStatusByCusIdAndChannelDTO = new ChangOrderStatusByCusIdAndChannelDTO();
        changOrderStatusByCusIdAndChannelDTO.setChannel(50);
        changOrderStatusByCusIdAndChannelDTO.setCusIds("5314fe9d56aa49f8b6c194461c66f317,804501a534624762ae07cbebcf037e6d");
        RestResponse<?> restResponse = ordercenterApi.changOrderStatusByCusIdAndChannel(changOrderStatusByCusIdAndChannelDTO);
        System.out.println("result:"+restResponse.isSuccess()+",date:"+restResponse.getData());
    }
    
    @Test
    public void updateOrderStatusByTask(){
        UpdateOrderStatusByTaskDTO updateOrderStatusByTaskDTO = new UpdateOrderStatusByTaskDTO();
        updateOrderStatusByTaskDTO.setOrderId(6L);
        RestResponse<?> restResponse = ordercenterApi.updateOrderStatusByTask(updateOrderStatusByTaskDTO);
        System.out.println("result:"+restResponse.isSuccess());
    }


    @Test
    public void queryCustomer(){

        QueryUserDepCusParamDTO dto1 = new QueryUserDepCusParamDTO();
        dto1.setCusName("培训管理test");
        List list = Lists.newArrayList(dto1);
        QueryUserDepCusRequestDTO dto2 = new QueryUserDepCusRequestDTO();
        dto2.setServiceCode("7");
        dto2.setQueryCustomerParamDTOList(list);
        System.out.println("-----------------------------------------------------------");
        System.out.println(customerInnerApi.queryUserDepCusList(dto2));
    }
    
    
    @Test
    public void queryHavingOrderByCusId(){
        QueryOrderByCusIdDTO queryOrderByCusIdDTO = new QueryOrderByCusIdDTO();
        queryOrderByCusIdDTO.setChannel(50);
        List<String> cusIds = new ArrayList<String>();
        cusIds.add("782ad0e1d69a47abb810ef2024537bb9");
        cusIds.add("b3feae6f124c4d95a1e854e3251fc0d3");
        queryOrderByCusIdDTO.setCusIds(cusIds);
        RestResponse<?> restResponse = ordercenterApi.queryHavingOrderByCusId(queryOrderByCusIdDTO);
        //System.out.println("result:"+restResponse.isSuccess());
    }

    @Test
    public void queryChargeOrderList() throws ParseException {
        PageQueryParam pageQueryParam =new PageQueryParam();
        QueryChargingListDTO queryChargingListDTO = new QueryChargingListDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = simpleDateFormat.parse("2019-01-18");
        queryChargingListDTO.setServiceEndDate(d.getTime());
        queryChargingListDTO.setChannel("12");
        pageQueryParam.setPageIndex(2);
        RestResponse<PageQueryResultDefaultImpl> restResponse = ordercenterApi.queryChargingOrderList(queryChargingListDTO);
        System.out.println("-------------------------------------");
        List<Map> list = restResponse.getBody().getData();
        list.forEach(x->System.out.println(x.get("orderId")));
    }


    @Test
    public void updateOrderInfo(){
        UpdateOrderByDeductionsSuccess updateOrder = new UpdateOrderByDeductionsSuccess();
        BigDecimal payAmount = new BigDecimal(20.00);
        int channel = 12;
        updateOrder.setChannel(channel);
        updateOrder.setBusinessOrderNo("2018010826501379");
        updateOrder.setCurrentPayAmount(payAmount);
        updateOrder.setUpdateUser("test");
        updateOrder.setPayTime(String.valueOf(new Date().getTime()));
        updateOrder.setPayWay(3);
        updateOrder.setPayAppId("aaaaaaa");
        updateOrder.setPayStatus(6);
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("payTime",updateOrder.getPayTime());
        param.put("updateUser",updateOrder.getUpdateUser());
        String sign = PaySign.genSign(param, "306ae7489164941106bbbd733f37e72a");
        updateOrder.setSign(sign);
        RestResponse<?> restResponse = ordercenterApi.updateOrderInfo(updateOrder);
        //System.out.println("result:"+restResponse.isSuccess());
    }

    @Test
    public void updateTestGoodsOrderToEnd(){
        UpdateTestGoodsOrderToEndDTO updateTestGoodsOrderToEndDTO = new UpdateTestGoodsOrderToEndDTO();
        updateTestGoodsOrderToEndDTO.setChannel(12);
//        Calendar calendar = Calendar.getInstance();
        updateTestGoodsOrderToEndDTO.setServiceStartDate(new Date());
        System.out.println(updateTestGoodsOrderToEndDTO.getChannel()+"****"+updateTestGoodsOrderToEndDTO.getServiceStartDate());
        RestResponse<?> restResponse = ordercenterApi.updateTestGoodsOrderToEnd(updateTestGoodsOrderToEndDTO);
        if (restResponse.isSuccess()){
            System.out.println(restResponse.getHead().getErrorMsg());
        }
    }

    @Test
    public void test(){
        String firstDay;
        String lastDay;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        /*Calendar cal_1=Calendar.getInstance();
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);
        firstDay = format.format(cal_1.getTime());
        System.out.println("-----1------firstDay:"+firstDay);*/
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);
        lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);
    }
}
