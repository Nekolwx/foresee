/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service;

/**
 * <pre>
 * 内部请求customer接口。
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

public interface ICustomerInnerService {
    
    /**
     * 通过cusId查询客户是否存在(关联了主联系人)。
     * @param cusId
     * @return Boolean true代表存在，false代表不存在
     */
     Boolean queryIfExistByCusId(String cusId);

    /**
     * 根据客户id获取客户区域
     * @param cusId
     * @return
     */
     String queryCusAreaCodeByCusId(String cusId);


    /**
     * 根据客户ID查询客户是否存在（网关动态查询，单表查询）
     * @param cusId
     * @return
     */
     Boolean queryCustomerIfExistByCusId(String cusId);

}
