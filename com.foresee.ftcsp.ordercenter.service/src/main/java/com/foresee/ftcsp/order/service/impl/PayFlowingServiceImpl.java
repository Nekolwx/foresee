/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.order.manual.dao.OrdPayFlowingExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.service.IPayFlowingService;


/**
 * <pre>
 * 支付流水实现类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月19日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional
public class PayFlowingServiceImpl implements IPayFlowingService {
    
    @Resource
    OrdPayFlowingExtMapper payFlowingExtMapper;

    @Override
    public OrdPayFlowingDTO queryPayFlowingInfoByPayNo(OrdPayFlowingDTO payFlowingDTO) {
        
        return payFlowingExtMapper.queryPayFlowingInfoByPayNo(payFlowingDTO);
    }

    @Override
    public List<OrdPayFlowingDTO> queryOrderIdByPayNo(OrdPayFlowingDTO payFlowingDTO) {
        
        return payFlowingExtMapper.queryOrderIdByPayNo(payFlowingDTO);
    }

    @Override
    public OrdPayFlowingDTO queryPayNoByOrderCode(OrdPayFlowingDTO payFlowingDTO) {
        
        return payFlowingExtMapper.queryPayNoByOrderCode(payFlowingDTO);
    }

    @Override
    public int updateByPayNoSelective(OrdPayFlowingDTO payFlowingDTO) {
        
        return payFlowingExtMapper.updateByPayNoSelective(payFlowingDTO);
    }

}
