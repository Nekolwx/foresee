package com.foresee.ftcsp.order.bill.service.impl;

import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.order.auto.dao.OrdBillMapper;
import com.foresee.ftcsp.order.auto.model.OrdBill;
import com.foresee.ftcsp.order.bill.service.IBillAssistService;
import com.foresee.ftcsp.order.bill.service.IBillBaseService;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *             修改后版本:     修改人：  修改日期:     修改内容:
 *                   </pre>
 * @date 2018-02-08
 */
@Service
public class BillBaseServiceImpl implements IBillBaseService {

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private IBillAssistService billAssistService;

    @Resource
    private OrdBillMapper ordBillMapper;

    @Override
    public OrdBillDTO createBill(OrdBillDTO billDTO) {
        billDTO.setBillCode(billAssistService.generateBillCode());
        OrdBill bill = new OrdBill();
        try {
            BeanUtils.copyProperties(bill, billDTO);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (bill.getBillId() == null || bill.getBillId() == 0L) {
            bill.setBillId(idGenerator.getLong());
            billDTO.setBillId(bill.getBillId());
        }
        ordBillMapper.insertSelective(bill);

        return billDTO;
    }
}
