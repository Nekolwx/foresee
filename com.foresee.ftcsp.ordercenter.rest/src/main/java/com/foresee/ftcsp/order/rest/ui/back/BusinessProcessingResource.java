package com.foresee.ftcsp.order.rest.ui.back;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.SessionUserId;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.manual.restdto.BusinessHandle;
import com.foresee.ftcsp.order.manual.restdto.TryBusinessHandle;
import com.foresee.ftcsp.order.service.IBusinessProcessingService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * <pre>
 * 解析excel导入数据请求控制器
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/21
 */
@RestController
@RequestMapping("/back/businessProcessingService")
public class BusinessProcessingResource {
    private Logger LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IBusinessProcessingService businessProcessingService;

    /**
     * 详情业务办理添加订单接口
     * @param businessHandle
     * @return
     */
    @RequestMapping(value="/addDetailedBusinessHandle",method= RequestMethod.POST)
    public Object addDetailedBusinessHandle(@Var @Valid BusinessHandle businessHandle,@SessionUserId String userId){
        OrdOrder ordOrder = (OrdOrder) businessProcessingService.addDetailedBusinessHandle(businessHandle,userId);
        if (null==ordOrder){
            RestResponse response=new RestResponse("1","新增订单失败");
            return  response;
        }
        RestResponse response=new RestResponse("0","新增订单成功");
        return  response;
    }

    /**
     * 试用商品业务办理添加订单接口
     * @param tryBusinessHandle
     * @return
     */
    @RequestMapping(value="/addTryOutBusinessHandle",method= RequestMethod.POST)
    public Object addTryOutBusinessHandle(@Var @Valid TryBusinessHandle tryBusinessHandle, @SessionUserId String userId){
        OrdOrder ordOrder = (OrdOrder) businessProcessingService.addTryOutBusinessHandle(tryBusinessHandle,userId);
        if (null==ordOrder){
            RestResponse response=new RestResponse("1","新增订单失败");
            return  response;
        }
        RestResponse response=new RestResponse("0","新增订单成功");
        return  response;
    }

    /**
     * 查询是否互斥商品规则。
     * @param tryBusinessHandle
     * @return
     */
    @RequestMapping(value="/queryGoodsIsMutuallyExclusive",method= RequestMethod.POST)
    public Object queryGoodsIsMutuallyExclusive(@Var @Valid TryBusinessHandle tryBusinessHandle){
        Map<String,Object> resultMap = (Map<String, Object>) businessProcessingService.queryGoodsIsMutuallyExclusive(tryBusinessHandle);
        if ((boolean)resultMap.get("result")){
            RestResponse response=new RestResponse("1","该客户已办理过试用商品，不能再次办理！");
            return  response;
        }
        RestResponse response=new RestResponse("0","查询成功");
        response.setBody(resultMap);
        return  response;
    }

}
