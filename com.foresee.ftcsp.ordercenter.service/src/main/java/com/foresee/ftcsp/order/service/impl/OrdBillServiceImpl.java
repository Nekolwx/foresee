/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.order.auto.dao.OrdBillMapper;
import com.foresee.ftcsp.order.auto.model.OrdBill;
import com.foresee.ftcsp.order.manual.dao.OrdBillExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryBillDetailDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryBillPageDTO;
import com.foresee.ftcsp.order.service.IOrdBillService;

/**
 * <pre>
 * 账单接口实现。
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
public class OrdBillServiceImpl implements IOrdBillService {

	@Resource
	private OrdBillMapper ordBillMapper;

	@Resource
	private IdGenerator idGenerator;

	@Resource
	private OrdBillExtMapper ordBillExtMapper;

	@Resource
	private IDUtil idUtil;

	@Override
	public Map<String, Object> createBill(OrdBillDTO billDTO) {
		String billDateStr = LocalDateUtil.getDateStr(LocalDateUtil.YMD, new Date());
		String billCodeKey = String.format(idUtil.createSequenceId("bill_code_key"), "1");
		billDTO.setBillCode(billDateStr + billCodeKey);
		OrdBill bill = new OrdBill();

		try {
			BeanUtils.copyProperties(bill, billDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if (bill.getBillId() == null || bill.getBillId() == 0L) {
			bill.setBillId(idGenerator.getLong());
		}
		ordBillMapper.insertSelective(bill);
		return null;
	}

	@Override
	public Object queryBillList(PageQueryParam param) {
		PageQueryResult<QueryBillPageDTO> pageQueryResult = ordBillExtMapper.selectByPageQueryParam(param);
		return pageQueryResult;
	}

	@Override
	public Object queryBillDetail(Long billId) {
		QueryBillDetailDTO queryBillDetailDTO = ordBillExtMapper.queryBillDetailByBillId(billId);
		return queryBillDetailDTO;
	}
	
	@Override
	public List<OrdBillDTO> queryBillByOrderId(Long orderId) {
		
		return ordBillExtMapper.queryBillByOrderId(orderId);
	}
}
