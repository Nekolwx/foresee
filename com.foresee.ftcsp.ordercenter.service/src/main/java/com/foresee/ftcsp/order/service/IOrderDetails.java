/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service;

import java.util.List;

import com.foresee.ftcsp.order.manual.dto.OrdBillItemDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderDetailsDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderParentAndChildDTO;

/**
 * <pre>
 * 支付接口。
 * </pre>
 *
 * @author weichunlei@foresee.com.cn
 * @date 2017年8月31日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IOrderDetails {
	/**
     * 根据子订单Id查询订单详情
     * @author weichunlei@foresee.com.cn
     * @date 2017年8月21日
     * @param queryOrderDetailsDTO
     * @return 	Object
     */
	Object searchOrderDetails(QueryOrderDetailsDTO queryOrderDetailsDTO);
	
    /**
     * 根据子订单Id查询订单
     * @author weichunlei@foresee.com.cn
     * @date 2017年8月21日
     * @param orderId
     * @return OrdOrderDTO
     */
	OrdOrderDTO queryOrderByOrderId(Long orderId);
	
	/**
	 * 子母订单聚合查询
	 * @param queryOrderParentOrChildDTO
	 * @return Object
	 */
	Object queryOrderParentAndChild(QueryOrderParentAndChildDTO queryOrderParentAndChildDTO);

	/**
     * 根据parentId查询子订单id
     * @param parentId
     * @return List<Long>
     */
	List<Long> queryOrderByParentId(Long parentId);
	
	/**
     * 查账单项目表，获取订单商品列表
     * @param ordOrderDTO
     * @return List<OrdBillItemDTO>
     */
	List<OrdBillItemDTO> queryOrdProduct(OrdOrderDTO ordOrderDTO);
	
	/**
     * 根据订单id查询账单项目表，获取商品详情
     * @param orderId
     * @return List<OrdBillItemDTO>
     */
	List<OrdBillItemDTO> queryOrdBillItem(Long orderId);
		
}
