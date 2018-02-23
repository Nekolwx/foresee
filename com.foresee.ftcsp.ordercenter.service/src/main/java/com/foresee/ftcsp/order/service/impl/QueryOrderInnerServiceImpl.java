package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.util.Loggers;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.service.IGoodsInnerService;
import com.foresee.ftcsp.order.service.IQueryOrderInnerService;
import com.foresee.ftcsp.ordercenter.api.dto.UpdateTestGoodsOrderToEndDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class QueryOrderInnerServiceImpl implements IQueryOrderInnerService {
    private Logger logger = Loggers.make();

    @Resource
    private IGoodsInnerService goodsInnerService;

    @Resource
    private OrdOrderExtMapper ordOrderExtMapper;


    /**
     * 根据渠道和时间，终止其相关联的试用商品的订单。
     * @param updateTestGoodsOrderToEndDTO
     * @return
     */
    @Override
    public Integer updateTestGoodsOrderToEnd(UpdateTestGoodsOrderToEndDTO updateTestGoodsOrderToEndDTO){
        //根据标签查询正式商品code和试用商品code
        String testTagCode = "OnTrial";//试用商品标签code
        String formalTagCode = "jcgjdzb";//正式商品标签code
        //查询正式商品的ListCode
        List<String> formalTagCodeList = goodsInnerService.queryGoodsCodeByTagCode(formalTagCode);
        //查询试用商品的ListCode
        List<String> testTagCodeList = goodsInnerService.queryGoodsCodeByTagCode(testTagCode);
        //将时间截取为00点00分
        updateTestGoodsOrderToEndDTO.setServiceStartDate(changDateToZero(updateTestGoodsOrderToEndDTO.getServiceStartDate()));
        //为时间增加一天
        updateTestGoodsOrderToEndDTO.setServiceStartDateAddOneDay(dateAddOneDay(updateTestGoodsOrderToEndDTO.getServiceStartDate()));
        //根据来源，服务开始时间的范围，查询出订单ID，客户ID
        List<OrdOrder> ordOrderList = ordOrderExtMapper.queryOrderByChannelAndServiceDate(updateTestGoodsOrderToEndDTO.getChannel(),updateTestGoodsOrderToEndDTO.getServiceStartDate(),updateTestGoodsOrderToEndDTO.getServiceStartDateAddOneDay(),formalTagCodeList);
        if (null==ordOrderList||OrderConstant.ZERO>=ordOrderList.size()){
            logger.info("根据业务办理的正式商品查询此时间："+updateTestGoodsOrderToEndDTO.getServiceStartDate()+"的订单为空");
            return OrderConstant.ZERO;
        }
        //获取所有客户ID
        List<String> customerIdList = new ArrayList<>();
        ordOrderList.forEach(ordOrder -> customerIdList.add(ordOrder.getCustomerId()));
        //将根据客户id终止试用商品的订单
        if (OrderConstant.ZERO>=customerIdList.size()){
            throw new FtcspRuntimeException("09030034", (Object) ("根据业务办理的正式商品查询此时间："+updateTestGoodsOrderToEndDTO.getServiceStartDate()+"的customerId为空"));
        }
        Integer result = ordOrderExtMapper.updateTestGoodsOrderToEnd(updateTestGoodsOrderToEndDTO.getChannel(),customerIdList,testTagCodeList);
        return result;
    }

    /**
     * 将时间转为年月日，时分秒为00：00：00
     * @param dateTime
     * @return
     */
    public Date changDateToZero(Date dateTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 为时间增加一天
     * @param dateTime
     * @return
     */
    public Date dateAddOneDay(Date dateTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        return calendar.getTime();
    }
}
