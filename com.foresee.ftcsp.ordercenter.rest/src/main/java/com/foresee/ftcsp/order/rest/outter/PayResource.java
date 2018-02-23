/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.rest.outter;

import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.response.StringResponse;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.manual.dto.request.MobilePayEntranceDTO;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamDTO;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamExtDTO;
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
@RestController("/outter/payService")
@RequestMapping("/outter/payService")
public class PayResource {

    @Resource
    private IPayService payService;


    /**
     * 移动端支付（包含h5支付以及公众号）。
     * @param payParamDTO
     * @return Object
     */
    @RequestMapping(value = "getPayParam", method = RequestMethod.POST)
    public Object getPayParam(@Var @Valid GetPayParamDTO payParamDTO) {
        Map<String, Object> resultMap = payService.payByMobileTerminal(payParamDTO);
        if (resultMap == null) {
            return new RestResponse("1", "获取支付参数失败");
        } else if ("1".equals(resultMap.get("errorCode"))) {
            return new RestResponse("1", resultMap.get("errorMsg").toString());
        } else {
            return new RestResponse("0", "成功", resultMap);
        }
    }
    
    /**
     * 移动端支付扩展（包含h5支付以及公众号）。
     * @param GetPayParamExtDTO
     * @return Object
     */
    @RequestMapping(value = "getPayParamExt", method = RequestMethod.POST)
    public Object getPayParamExt(@Var @Valid GetPayParamExtDTO payParamExtDTO) {
        Map<String, Object> resultMap = payService.payByMobileTerminalExt(payParamExtDTO);
        if (resultMap == null) {
            return new RestResponse("1", "获取支付参数失败");
        } else if ("1".equals(resultMap.get("errorCode"))) {
            return new RestResponse("1", resultMap.get("errorMsg").toString());
        } else {
            return new RestResponse("0", "成功", resultMap);
        }
    }


    /**
     * 统一移动端支付接入接口。
     * @param mobilePayEntranceDTO
     * @return Object
     */
    @RequestMapping(value = "mobilePayEntrance", method = RequestMethod.POST)
    public RestResponse<StringResponse> mobilePayEntrance(@Var @Valid MobilePayEntranceDTO mobilePayEntranceDTO) {
        String payPageUrl = payService.mobilePayEntrance(mobilePayEntranceDTO);
        return RestResponse.create("操作成功", "payPageUrl", payPageUrl);
        //return RestResponse.create("payPageUrl", payPageUrl);
    }
    
}
