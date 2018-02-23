/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.ui.front;

import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

import com.foresee.ftcsp.order.manual.restdto.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.service.IOrderService;
import com.foresee.ftcsp.order.service.IPayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <pre>
 * 支付前端接口控制层。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@RestController("/front/payService")
@RequestMapping("/front/payService")
public class PayResource {

    private Logger        LOGGER = Logger.getLogger(this.getClass());
    
    private Gson          gson   = new GsonBuilder().disableHtmlEscaping().create();
    
    @Resource 
    private IPayService payService;
    
    @Resource 
    private IOrderService orderService;

    /**
     * 前端扫码支付。
     * @param payDTO
     * @return Object
     */
    @RequestMapping(value = "pay", method = RequestMethod.POST)
    public Object pay(@Var @Valid PayDTO payDTO){  
        Map<String, Object> resultMap = payService.payByScanCode(payDTO);
        if((boolean) resultMap.get("successFlag")){
            return resultMap.get("restResponse");
        }else{
            return new RestResponse("1", resultMap.get("errorMessage").toString());
        }
        
    }
    
    /**
     * 打赏支付页面初始化。
     * @param rewardPayInitDTO
     * @return Object
     */
    @RequestMapping(value = "rewardPayInit",method = RequestMethod.POST)
    public Object rewardPayInit(@Var @Valid RewardPayInitDTO rewardPayInitDTO){
        Map<String, Object> resultMap = payService.rewardPayInit(rewardPayInitDTO);
        if((boolean) resultMap.get("successFlag")){
            return new RestResponse ("0","请求支付页面初始化成功",resultMap.get("showParam"));
        }else{
            return new RestResponse("1", resultMap.get("errorMessage").toString());
        }
    }
    
    /**
     * 打赏支付。
     * @param rewardPayDTO
     * @return Object
     */
    @RequestMapping(value = "rewardPay", method = RequestMethod.POST)
    public Object pay(@Var @Valid RewardPayDTO rewardPayDTO){  

        Map<String, Object> resultMap = payService.rewardPay(rewardPayDTO);
        if((boolean) resultMap.get("successFlag")){
            return resultMap.get("restResponse");
        }else{
            return new RestResponse("1", resultMap.get("errorMessage").toString());
        }
    }

    /**
     * 手机端支付页面初始化接口
     * @param MobilePaymentInitDTO
     * @return Object
     */
    @RequestMapping(value = "mobilePaymentInit", method = RequestMethod.POST)
    public Object mobilePaymentInit(@Var @Valid MobilePaymentInitDTO mobilePaymentInitDTO) {
        Map resultMap = payService.mobilePaymentInit(mobilePaymentInitDTO);
        if ((boolean) resultMap.get("flag")) {
            return RestResponse.successData(resultMap.get("data"));
        }
        return new RestResponse<>("1",(String)resultMap.get("msg"));
    }

    /**
     * 手机端支付页面调用支付接口
     * @param mobilePaymentDTO
     * @return Object
     */
    @RequestMapping(value = "mobilePayment", method = RequestMethod.POST)
    public Object mobilePayment(@Var @Valid MobilePaymentDTO mobilePaymentDTO){
        Map resultMap = payService.mobilePayment(mobilePaymentDTO);
        if ((boolean) resultMap.get("flag")) {
            //return RestResponse.successData(resultMap.get("data"));
            return resultMap.get("data");
        }
        return new RestResponse<>("1",(String)resultMap.get("msg"));
    }

}
