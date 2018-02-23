/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service;

import java.util.List;
import java.util.Map;

import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryBillDetailDTO;

/**
 * <pre>
 * 账单接口。
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

public interface IOrdBillService {
    
    /**
     * 创建订账单。
     * @param billDTO
     * @return Map<String,Object>
     */
    public Map<String, Object> createBill(OrdBillDTO billDTO);
    
    /**
     * 账单分页查询。
     * @param param
     * @return Object
     */
    public Object queryBillList(PageQueryParam param);
    
    /**
     * 账单详情查询。
     * @param ordBillDTO
     * @return Object
     */
    public Object queryBillDetail(Long billId);

    /**
     * 通过订单Id查询账单
     * @param orderId
     * @return OrdBillDTO
     */
	public List<OrdBillDTO> queryBillByOrderId(Long orderId);

}
