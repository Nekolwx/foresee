package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.order.manual.dto.OrdOrderContractDTO;

/**
 * <pre>
 * 合同业务服务类
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 * @date 2017/12/20
 */
public interface IContractService {

    OrdOrderContractDTO queryContractByOrderId(Long orderId);
}
