/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service.impl;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.manual.dto.PdtBillingInformationDTO;
import com.foresee.ftcsp.goods.manual.restdto.QueryInvoiceInfoDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.GoodsInformationDTO;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dto.*;
import com.foresee.ftcsp.order.service.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foresee.ftcsp.order.manual.dao.OrderDetailsExtMapper;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderDetailsDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderParentAndChildDTO;
import com.foresee.ftcsp.order.service.IOrdBillService;
import com.foresee.ftcsp.order.service.IOrderDetails;

/**
 * <pre>
 * 订单详情实现类。
 * </pre>
 *
 * @author weichunlei@foresee.com.cn
 * @date 2017年9月1日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional
public class OrderDetailsServiceImpl implements IOrderDetails {
	
	@Resource
	private OrderDetailsExtMapper orderDetailsExtMapper;
	
	@Resource
    private IOrdBillService ordBillService;

	@Autowired
	private GoodsApi goodsApi;

	@Autowired
	private IContractService contractService;

	@Override
	public OrdOrderDTO queryOrderByOrderId(Long orderId) {

		return orderDetailsExtMapper.queryOrderByOrderId(orderId);
	}
	
	@Override
	public Object searchOrderDetails(QueryOrderDetailsDTO queryOrderDetailsDTO) {
		//根据订单id查找本身订单
    	OrdOrderDTO ordOrderSelf = this.queryOrderByOrderId(queryOrderDetailsDTO.getOrderId());
		if (Objects.isNull(ordOrderSelf)) {
			return null;
		}
    	if(OrderConstant.IS_PARENT_FALSE.equals(ordOrderSelf.getIsParent())){  //存在父订单,本身为子订单
			//TODO 暂时无子订单需求
    	}
    	//获取订单的合同信息
		OrdOrderContractDTO contractDTO = contractService.queryContractByOrderId(ordOrderSelf.getOrderId());

    	//根据订单id查询账单详情
    	List<OrdBillDTO> ordBillDTOList=ordBillService.queryBillByOrderId(queryOrderDetailsDTO.getOrderId());    	
    	   	
    	//根据订单中skuId查询商品详情,订单中可能并没有存储有效的商品信息
		Long goodsSkuId = ordOrderSelf.getSkuId();
		QueryInvoiceInfoDTO queryInvoiceInfoDTO = new QueryInvoiceInfoDTO();
		queryInvoiceInfoDTO.setGoodsSkuId(goodsSkuId);
		RestResponse<PdtBillingInformationDTO> restResponse = goodsApi.queryInvoiceInfoBySkuId(queryInvoiceInfoDTO);
		BigDecimal taxRate = restResponse.getBody() == null ? null : restResponse.getBody().getTaxRate(); //商品税率

		RestResponse<GoodsInformationDTO> goodsInfo = goodsApi.queryGoodsInfoBySkuId(goodsSkuId);
		String goodsName = goodsInfo.getBody() == null ? null : goodsInfo.getBody().getGoodsName();
		BigDecimal goodsPrice = goodsInfo.getBody() == null ? null : goodsInfo.getBody().getPrice();

		//TODO 一个接口已经返回信息了
		ordOrderSelf.setTaxRate(taxRate);  //从商品中查询到的税率
		ordOrderSelf.setGoodsName(goodsName); //商品的名称
		ordOrderSelf.setGoodsAmount(goodsPrice); //商品的价格

		List<OrdOrderDTO> orderList = new ArrayList<>();
		orderList.add(ordOrderSelf); //将自身订单加入用于集合遍历展示
		//TODO 子订单展示需求

    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("ordOrderSelf", ordOrderSelf);   //自身订单
    	map.put("ordBillList", ordBillDTOList);
		map.put("contract", contractDTO);
		map.put("orderList", orderList);  //订单列表展示数据
        return map;
	}
	
	@Override
	public Object queryOrderParentAndChild(QueryOrderParentAndChildDTO queryOrderParentAndChildDTO) {

		Map<String, Object> map = new HashMap<String, Object>();

		/* 根据订单id查询订单基本 */
		OrdOrderDTO ordOrderDTO = this.queryOrderByOrderId(queryOrderParentAndChildDTO.getOrderId());
		List<OrdOrderDTO> OrdOrderDTOParentList = new ArrayList<OrdOrderDTO>();
		OrdOrderDTOParentList.add(ordOrderDTO);
		map.put("OrdOrderDTOParentList", OrdOrderDTOParentList);
				
		List<OrdChildDTO> ordChildDTOList = new ArrayList<OrdChildDTO>();		
		// 根据父订单Id查出子订单号
		List<Long> orderIdList = this.queryOrderByParentId(queryOrderParentAndChildDTO.getOrderId());
		for (Long orderId : orderIdList) {
			OrdChildDTO  ordChildDTO=new OrdChildDTO();
			//查子订单信息
			OrdOrderDTO ordOrderDTOChild = this.queryOrderByOrderId(orderId);
			ordChildDTO.setOrdOrderChildTDO(ordOrderDTOChild);
			//查商品信息
			List<OrdBillItemDTO> ordBillItemDTOList=this.queryOrdBillItem(orderId);			
			ordChildDTO.setOrdBillItemDTOList(ordBillItemDTOList);
			ordChildDTOList.add(ordChildDTO);
		}
		map.put("ordChildDTOList",ordChildDTOList);				
		return map;
	}

	@Override
	public List<Long> queryOrderByParentId(Long parentId) {

		return orderDetailsExtMapper.queryOrderByParentId(parentId);
	}

	@Override
	public List<OrdBillItemDTO> queryOrdProduct(OrdOrderDTO ordOrderDTO) {
							
		List<OrdBillItemDTO> ordBillItemDTOList=this.queryOrdBillItem(ordOrderDTO.getOrderId());
		return ordBillItemDTOList;
	}

	@Override
	public List<OrdBillItemDTO> queryOrdBillItem(Long orderId) {
		
		return orderDetailsExtMapper.queryOrdBillItem(orderId);
	}
		    
}
