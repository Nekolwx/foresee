package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.util.Loggers;
import com.foresee.ftcsp.order.manual.dao.OrdOrderContractExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdOrderContractDTO;
import com.foresee.ftcsp.order.service.IContractService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <pre>
 * TODO
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
@Service
public class ContractServiceImpl implements IContractService{

    Logger logger = Loggers.make();

    @Autowired
    private OrdOrderContractExtMapper ordOrderContractExtMapper;


    @Override
    public OrdOrderContractDTO queryContractByOrderId(Long orderId) {
        return ordOrderContractExtMapper.queryContractByOrderId(orderId);
    }
}
