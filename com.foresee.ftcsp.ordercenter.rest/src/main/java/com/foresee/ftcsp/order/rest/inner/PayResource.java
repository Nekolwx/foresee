/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.inner;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.constant.PayConstant;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamDTO;
import com.foresee.ftcsp.order.manual.restdto.ReceivePayCallBackDTO;
import com.foresee.ftcsp.order.service.IPayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <pre>
 * 支付外部接口控制层。
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
@RestController("/inner/payService")
@RequestMapping("/inner/payService")
public class PayResource {

    @Resource
    private IPayService payService;


    /**
     * 接收支付回调。
     * @param param
     * @return Object
     */
    @RequestMapping(value = "/payCallBack", method = RequestMethod.POST)
    public Object payCallBack(@Var @Valid ReceivePayCallBackDTO receivePayCallBackDTO) {
        Map<String, Object> result = payService.payCallBack(receivePayCallBackDTO);
        if (result != null) {
            if((boolean) result.get("successFlag")){
                return PayConstant.CALLBACK_PAY_STATUS_SUCCESS;
            }else{
                return result.get("errorMessage").toString(); 
            }
        }else{
            return PayConstant.CALLBACK_PAY_STATUS_FAIL;
        }
    }

}
