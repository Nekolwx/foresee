/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.impl;

import javax.annotation.Resource;

import com.foresee.ftcsp.common.core.rest.RestRequestParam;
import com.foresee.ftcsp.common.core.rest.client.FtcspInnerRestClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.customer.api.inner.CustomerInnerApi;
import com.foresee.ftcsp.customer.manual.reponsedto.MainContactResDTO;
import com.foresee.ftcsp.customer.manual.restdto.ContactReqDTO;
import com.foresee.ftcsp.order.service.ICustomerInnerService;

import java.util.Map;


/**
 * <pre>
 * 内部请求customer接口处理实现类。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2018年1月15日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
public class CustomerInnerServiceImpl implements ICustomerInnerService {
    
    private Logger LOGGER = Logger.getLogger(this.getClass());
    
    @Resource
    private CustomerInnerApi   customerInnerApi;

    @Autowired
    private FtcspInnerRestClient restClient;

    @Override
    public Boolean queryIfExistByCusId(String cusId) {
        ContactReqDTO contactReqDTO = new ContactReqDTO();
        contactReqDTO.setCusId(cusId);
        RestResponse<MainContactResDTO> response=customerInnerApi.queryMainContactByCusId(contactReqDTO);
        if(response.isSuccess()){
            if(response.getBody()==null){
                return false;
            }
            if(response.getBody()!=null){
                return true;
            }
        }else{
            throw new FtcspRuntimeException("09030034",(Object)("调用客户inner接口失败："+response.getHead().getErrorMsg()));
        }
        return false;
    }

    /**
     * 根据客户id获取客户区域
     * @param cusId
     * @return
     */
    @Override
    public String queryCusAreaCodeByCusId(String cusId){
        RestRequestParam queryParam = new RestRequestParam();
        queryParam.put("cusId", cusId);
        String url = String.format("http://gateway/customer%s/inner/areaServiceExt/queryCustomerArea",
                restClient.getDeveloperName());
        RestResponse restResponse = restClient.post(url, queryParam);
        if (null==restResponse.getBody()||!"0".equals(restResponse.getHead().getErrorCode())){
            LOGGER.info("根据客户id："+cusId+"查询接口出错，返回null值");
            return null;
        }
        @SuppressWarnings("unchecked") Map<String, String> map = (Map<String, String>) restResponse.getBody();
        String areaCode = map.get("areaCode");
        return areaCode;
    }

    @Override
    public Boolean queryCustomerIfExistByCusId(String cusId) {
        RestRequestParam queryParam = new RestRequestParam();
        queryParam.put("cusId", cusId);
        String url = String.format(
                "http://gateway/customer/inner/customerServiceExt/queryCustomerIsExist",
                restClient.getDeveloperName());
        RestResponse restResponse = restClient.post(url, queryParam);
        if ("0".equals(restResponse.getHead().getErrorCode())) {
            Map<String, String> map = (Map<String, String>) restResponse.getBody();
            if (map == null) {
                return false;
            }else{
                return true;
            }
        }else{
            throw new FtcspRuntimeException("09030034",(Object)("调用客户动态查询客户是否存在接口失败："+restResponse.getHead().getErrorMsg()));
        }

    }

}
