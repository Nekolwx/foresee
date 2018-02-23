package com.foresee.ftcsp.order.mapper;

import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.order.auto.dao.OrdOrderMapper;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdOrderListDTO;
import com.foresee.ftcsp.order.rest.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

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
 * @date 2017/9/27
 */
public class OrderMapperTest extends BaseTest {

    @Autowired
    private OrdOrderMapper ordOrderMapper;
    @Autowired
    private OrdOrderExtMapper ordOrderExtMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    public void testQuery() throws Exception {
        doQuery();
    }

    private void doQuery() {
        PageQueryParam param = new PageQueryParam();
        param.setPageSize(1);
        param.setPageSize(10);
        PageQueryResult<OrdOrderListDTO> ordOrderListDTOPageQueryResult = ordOrderExtMapper.queryOrderList(param);
        System.out.println(ordOrderListDTOPageQueryResult);
    }

    @Test
    public void testDuplicate(){

        OrdOrder ordOrder = new OrdOrder();
        Long orderId = idGenerator.getLong();
       // String orderCode = generateOrderCode();
        Date date = new Date();
        ordOrder.setOrderId(orderId);
        ordOrder.setBusinessOrderNo("666666655");
        ordOrder.setPayAmount(new BigDecimal(66.0d));
        ordOrder.setChannel(4);
        ordOrder.setGoodsId(11l);
        ordOrder.setSkuId(25205542045684835l);
       // ordOrder.setCustomerId(customerId);
       // ordOrder.setOrderUser(createRewardOrderDTO.getUserId());
        ordOrder.setCreateTime(date);
        ordOrder.setOrderTime(date);
        //ordOrder.setIsParent(1);
        ordOrder.setOpeningMode(3);  //付款开通
        ordOrder.setPayStatus(1);  //待付款
        ordOrder.setOrderType(0); //类型为新增订单
        ordOrder.setDividePay(0);  //全额
        ordOrder.setServiceMode(1);  //按次服务
        ordOrder.setOrderStatus(0);  //设置为待付款
        int count = ordOrderMapper.insertSelective(ordOrder);
        System.out.println("------>"+count);
    }


}