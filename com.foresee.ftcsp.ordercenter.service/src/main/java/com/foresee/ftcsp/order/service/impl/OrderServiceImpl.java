/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.account.api.dto.AccountQueryBalanceDTO;
import com.foresee.ftcsp.account.api.dto.AccountQueryBalanceResultDTO;
import com.foresee.ftcsp.account.api.inner.AccountInnerApi;
import com.foresee.ftcsp.common.core.dict.FtcspDictHolder;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.common.core.redis.IRedisClient;
import com.foresee.ftcsp.common.core.rest.RestRequestParam;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.client.FtcspInnerRestClient;
import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.common.core.util.Jackson;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.customer.api.inner.CustomerInnerApi;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.auto.model.PdtGoodsSku;
import com.foresee.ftcsp.goods.manual.dto.QueryGoodsBySkuToOrderDTO;
import com.foresee.ftcsp.goods.manual.dto.QuerySelectedCategoryRequestDto;
import com.foresee.ftcsp.goods.manual.dto.QuerySelectedCategoryResponseDto;
import com.foresee.ftcsp.goods.manual.dto.QuerySkuContentBySkuListResultDTO;
import com.foresee.ftcsp.goods.manual.restdto.QueryGoodInfoBySkuDTO;
import com.foresee.ftcsp.goods.manual.restdto.QueryGoodsByCategoryDTO;
import com.foresee.ftcsp.goods.manual.restdto.QuerySkuContentBySkuListDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryGoodsByCategoryResponseDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryIsOrderAreaDeployBySkuId;
import com.foresee.ftcsp.order.auto.dao.*;
import com.foresee.ftcsp.order.auto.model.*;
import com.foresee.ftcsp.order.constant.AccountConstant;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.constant.PayConstant;
import com.foresee.ftcsp.order.constant.SignEncryptConstant;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtendExtMapper;
import com.foresee.ftcsp.order.manual.dto.*;
import com.foresee.ftcsp.order.manual.dto.response.QueryOrderByCusIdResPonseDTO;
import com.foresee.ftcsp.order.manual.restdto.*;
import com.foresee.ftcsp.order.service.*;
import com.foresee.ftcsp.order.service.config.PayChannelConfig;
import com.foresee.ftcsp.order.service.config.PayRewardConfig;
import com.foresee.ftcsp.order.util.PaySign;
import com.foresee.ftcsp.ordercenter.api.dto.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <pre>
 * 支付实现类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 *                                              修改记录
 *                                                 修改后版本:     修改人：  修改日期:     修改内容:
 *                                                       </pre>
 * @date 2017年8月17日
 */
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    private Logger LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private OrdOrderExtMapper ordOrderExtMapper;

    @Resource
    private OrdOrderMapper ordOrderMapper;

    @Resource
    private OrdBillingInfoMapper ordBillingInfoMapper;

    @Resource
    private OrdDeliveryMapper ordDelinveryMapper;

    @Resource
    private OrdPayFlowingMapper ordPayFlowingMapper;

    @Resource
    private OrdPayFlowingRefMapper ordPayFlowingRefMapper;

    @Resource
    private OrdOrderAttributeMapper ordOrderAttributeMapper;

    @Resource
    private OrdOrderExtendExtMapper ordOrderExtendExtMapper;

    @Resource
    private OrdOrderExtendMapper ordOrderExtendMapper;

    @Resource
    private OrdBillMapper ordBillMapper;

    @Resource
    private OrdBillPayRefMapper ordBillPayRefMapper;

    @Resource
    private IDUtil idUtil;

    @Autowired
    private FtcspInnerRestClient restClient;

    @Resource
    private IPayFlowingService payFlowingService;

    @Resource
    private OrdBillItemMapper ordBillItemMapper;

    @Autowired
    private FtcspDictHolder dictHolder;

    @Autowired
    private PayRewardConfig payRewardConfig;

    @Autowired
    private AccountInnerApi accountApi;

    @Autowired
    PayChannelConfig PayChannelConfig;

    @Autowired
    IRedisClient redisClient;

    @Autowired
    private GoodsApi goodsApi;

    @Autowired
    private IGoodsInnerService goodsInnerService;

    @Autowired
    private ICustomerInnerService customerInnerService;

    @Autowired
    private IObjectAssemblyService objectAssemblyService;

    @Override
    public OrdOrderDTO queryOrderPayInfoByCode(OrdOrderDTO ordOrderDTO) {

        return ordOrderExtMapper.queryOrderPayInfoByCode(ordOrderDTO);
    }

    @Override
    public OrdOrderDTO queryOrderPayInfoByPayNo(OrdPayFlowingDTO ordPayFlowingDTO) {

        return ordOrderExtMapper.queryOrderPayInfoByPayNo(ordPayFlowingDTO);
    }

	@Override
	public Map<String, Object> createOrder(OrderDTO orderDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		String customerId = orderDTO.getCusId();

		if (!StringUtils.isEmpty(orderDTO.getBusinessOrderNo())) {
			OrdOrder ordOrder = this.ordOrderExtMapper.selectByBusinessOrderNoAndChannel(orderDTO.getBusinessOrderNo(),
					Integer.valueOf(orderDTO.getChannel()));
			if (ordOrder != null) {
				map.put("errorCode", "1");
				map.put("errorMsg", "业务订单号已存在");
				return map;
			}
		}

		if (orderDTO.getGoodsGroup() == null || orderDTO.getGoodsGroup().size() <= 0) {
			map.put("errorCode", "1");
			map.put("errorMsg", "商品信息不能为空！");
			return map;
		}
		if (StringUtils.isEmpty(customerId)) {
		    if(!StringUtils.isEmpty(orderDTO.getCusType())){
		        if (StringUtils.isEmpty(orderDTO.getCusName())) {
	                map.put("errorCode", "1");
	                map.put("errorMsg", "选择了客户类型，客户名称不能为空！");
	                return map;
	            }
	            if (StringUtils.isEmpty(orderDTO.getCusIdentificationNumber())) {
	                map.put("errorCode", "1");
	                map.put("errorMsg", "选择了客户类型，纳税人识别号/身份证号码不能为空！");
	                return map;
	            }
		    }else{
				map.put("errorCode", "1");
				map.put("errorMsg", "选择了客户类型不能为空！");
			}

			Map<String, String> param = new HashMap<String, String>();
			param.put("cusType", orderDTO.getCusType());
			param.put("cusName", orderDTO.getCusName());
			param.put("taxIdentificationNumber", orderDTO.getCusIdentificationNumber());
			String channel = conversionCoding(orderDTO.getChannel());
			if(StringUtils.isEmpty(channel)){
				map.put("errorCode", "1");
				map.put("errorMsg", "渠道标识错误！");
				return map;
			}
			param.put("inputChannel",channel);
			Map<String, Object> resultMap = this.generateCusId(param);
			if ("1".equals(resultMap.get("errorCode"))) {
				return resultMap;
			}
			customerId = (String) resultMap.get("customerId");
		}else{
			//获取是否可以重复创建订单标识
			if("2".equals(orderDTO.getNeedRepeat())){
				//把组装的sku进行分解,商品sku进行查询获取skuId
				List<Long> skuIdList = new ArrayList<>();
				for (GoodsDTO goods:orderDTO.getGoodsGroup()){
					Map<String, Object>result = queryGoods(goods.getGoodsSku());
					 if (result.containsKey("goodsDTO")){
						 GoodsDTO gdto = (GoodsDTO)result.get("goodsDTO");
						 skuIdList.add(gdto.getGoodsSkuId());
					 }else{
					 	return result;
					 }
				}
				if(skuIdList.size()<=0){
					map.put("errorCode", "1");
					map.put("errorMsg", "商品sku为空");
					return map;
				}
				//不可以重复，需判断是否有存在的订单。有就不返回不创建。以客户id，渠道，商品sku来查询。
				//重复创单是否关注订单状态
				if(OrderConstant.CARE_ABOUT_ORDER_STATUS_NO.equals(orderDTO.getCareAboutOrderStatus())){
                    //不关注订单状态
				    if (ordOrderExtMapper.selectByCusIdAndSkuAndChannle(customerId,orderDTO.getChannel(),skuIdList)>0){
                        map.put("errorCode", "1");
                        map.put("errorMsg", "订单已经有重复信息，不能购买");
                        return map;
                    }
                } else{
                    //关注订单状态
                    if (ordOrderExtMapper.selectByCusIdAndSkuAndChannleExt(customerId,orderDTO.getChannel(),skuIdList)>0){
                        map.put("errorCode", "1");
                        map.put("errorMsg", "订单已经有重复信息，不能购买");
                        return map;
                    }
                }

                if (skuIdList.size() <= 0) {
                    map.put("errorCode", "1");
                    map.put("errorMsg", "商品sku为空");
                    return map;
                }
                //不可以重复，需判断是否有存在的订单。有就不返回不创建。以客户id，渠道，商品sku来查询。
                //重复创单是否关注订单状态
                if(OrderConstant.CARE_ABOUT_ORDER_STATUS_NO.equals(orderDTO.getCareAboutOrderStatus())){
                    //不关注订单状态
                    if (ordOrderExtMapper.selectByCusIdAndSkuAndChannle(customerId,orderDTO.getChannel(),skuIdList)>0){
                        map.put("errorCode", "1");
                        map.put("errorMsg", "订单已经有重复信息，不能购买");
                        return map;
                    }
                } else{
                    //关注订单状态
                    if (ordOrderExtMapper.selectByCusIdAndSkuAndChannleExt(customerId,orderDTO.getChannel(),skuIdList)>0){
                        map.put("errorCode", "1");
                        map.put("errorMsg", "订单已经有重复信息，不能购买");
                        return map;
                    }
                }
            }
        }

		OrdBillingInfo billingInfo = null;
		OrdDelivery deliver = null;
		// 生成订单基本信息
		Map<String, Object> returnMap = basicGenerateOrdOrder(orderDTO);
		if ("1".equals(returnMap.get("errorCode"))) {
			return returnMap;
		}
		// 需要开票设置开票信息
		if ("0".equals(orderDTO.getNeedInvoice())) {
			billingInfo = new OrdBillingInfo();
			billingInfo.setBuyerType(Integer.valueOf(orderDTO.getBuyerType()));
			billingInfo.setBuyerName(orderDTO.getBuyerName());
			billingInfo.setBuyerTaxpayerNo(orderDTO.getBuyerTaxpayerNo());
			billingInfo.setBuyerAddressPhone(orderDTO.getBuyerAddressPhone());
			billingInfo.setCreateUser(orderDTO.getUserId());
			billingInfo.setUpdateUser(orderDTO.getUserId());
		}
		// 需要配送设置配送信息
		if ("0".equals(orderDTO.getDeliveryMode()) || "1".equals(orderDTO.getDeliveryMode())) {
			deliver = new OrdDelivery();
			deliver.setDeliveryMode(Integer.valueOf(orderDTO.getDeliveryMode()));
			deliver.setReceiver(orderDTO.getReceiver());
			deliver.setReceiverPhone(orderDTO.getReceiverPhone());
			deliver.setReceiverAddress(orderDTO.getReceiverAddress());
			deliver.setInviteAddress(orderDTO.getInviteAddress());
			deliver.setCreateUser(orderDTO.getUserId());
			deliver.setUpdateUser(orderDTO.getUserId());
		}
		OrdOrder basicOrder = (OrdOrder) returnMap.get("basicOrder");
		basicOrder.setCustomerId(customerId);
		basicOrder.setChannel(Integer.valueOf(orderDTO.getChannel()));
		basicOrder.setBusinessOrderNo(orderDTO.getBusinessOrderNo());
		@SuppressWarnings("unchecked")
		List<GoodsDTO> goodsList = (List<GoodsDTO>) returnMap.get("goodsList");
		OrdPayFlowing payFlowing = new OrdPayFlowing();
		payFlowing.setPayState(OrderConstant.ZERO);// 默认待支付
		payFlowing.setPayWay(OrderConstant.ZERO);// 默认支付宝
		payFlowing.setPayNo(generatePayNo());
		payFlowing.setPayAmount(basicOrder.getPayAmount());
		payFlowing.setCreateUser(orderDTO.getUserId());
		payFlowing.setUpdateUser(orderDTO.getUserId());

		try {
			this.createOrders(goodsList, basicOrder, billingInfo, deliver, payFlowing);
		} catch (Exception e) {
			map.put("errorCode", "1");
			map.put("errorMsg", "创建失败:" + e);
			LOGGER.error(e);
			return map;
		}

		map.put("errorCode", "0");
		map.put("orderCode", basicOrder.getOrderCode());
		map.put("payNo", payFlowing.getPayNo());
		return map;
	}

	/**
	 * 生成订单基本信息 TODO。
	 *
	 * @param orderDTO
	 * @return OrdOrderDTO
	 */
	private Map<String, Object> basicGenerateOrdOrder(OrderDTO orderDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		OrdOrder ordOrder = new OrdOrder();
		List<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();
		ordOrder.setOrderType(OrderConstant.ORDER_TYPE_ADD);
		ordOrder.setOrderTime(new Date());
		ordOrder.setOrderUser(orderDTO.getUserId());
		Integer qtySum = 0;
		BigDecimal amountSum = new BigDecimal(0.00);
		for (GoodsDTO goods : orderDTO.getGoodsGroup()) {
			// 设置商品信息
			Map<String, Object> resultMap = this.queryGoods(goods.getGoodsSku());
			if ("1".equals(resultMap.get("errorCode"))) {
				return resultMap;
			}
			GoodsDTO queryGoods = (GoodsDTO) resultMap.get("goodsDTO");
			Integer qty = Integer.valueOf(goods.getGoodsQuantiy());
			queryGoods.setGoodsQuantity(qty);
			goodsList.add(queryGoods);
			qtySum = qtySum + qty;
			BigDecimal amount = queryGoods.getGoodsDiscountPrice().multiply(new BigDecimal(qty));
			amountSum = amountSum.add(amount);
		}
		ordOrder.setGoodsAmount(amountSum);
		ordOrder.setGoodsQuantity(qtySum);
		ordOrder.setFreight(new BigDecimal(0.00));
		ordOrder.setDiscountAmount(new BigDecimal(0.00));
		// 支付金额=商品金额+运费-优惠金额
		ordOrder.setPayAmount((amountSum.add(ordOrder.getFreight())).subtract(ordOrder.getDiscountAmount()));
		ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_PENDING_PAY);
		ordOrder.setDividePay(OrderConstant.DIVIDE_PAY_NO);
		ordOrder.setFeedback(orderDTO.getFeedback());
		// 设置开票状态默认未开票
		if ("1".equals(orderDTO.getNeedInvoice())) {
			ordOrder.setBillingStatus(OrderConstant.BILLING_STATUS_NO_NEED);
		} else {
			ordOrder.setBillingStatus(OrderConstant.BILLING_STATUS_NO);
		}
		// 设置配送方式默认不需要配送
		if ("0".equals(orderDTO.getDeliveryMode()) || "1".equals(orderDTO.getDeliveryMode())) {
			ordOrder.setIsDelivery(OrderConstant.NEED_DELIVERY_YES);
		} else {
			ordOrder.setIsDelivery(OrderConstant.NEED_DELIVERY_NO);
		}
		ordOrder.setPayStatus(OrderConstant.ONE);
		ordOrder.setRefundStatus(OrderConstant.ZERO);
		ordOrder.setServiceStatus(OrderConstant.ZERO);
		ordOrder.setLogisticsStatus(OrderConstant.ZERO);
		ordOrder.setDeleted(OrderConstant.ZERO);
		ordOrder.setCreateUser(orderDTO.getUserId());
		ordOrder.setUpdateUser(orderDTO.getUserId());
		map.put("basicOrder", ordOrder);
		map.put("goodsList", goodsList);
		return map;
	}

    /**
     * 创建订单相关信息
     * @param goodsList
     * @param basicOrder
     * @param billingInfo
     * @param deliver
     * @param payFlowing
     */
    private void createOrders(List<GoodsDTO> goodsList, OrdOrder basicOrder, OrdBillingInfo billingInfo,
                              OrdDelivery deliver, OrdPayFlowing payFlowing) {
        // 默认不生成父订单
        boolean flag = false;
        //业务订单号默认与订单编号不同
        boolean businessOrderNo = false;
        Long parentOrderId = null;
        if (goodsList.size() > 1) {
            flag = true;// 购买多个商品需要生成父订单
            parentOrderId = idGenerator.getLong();
            basicOrder.setIsParent(OrderConstant.ZERO);
            basicOrder.setOrderId(parentOrderId);
            basicOrder.setCreateTime(new Date());
            basicOrder.setUpdateTime(new Date());
            basicOrder.setOrderCode(generateOrderCode());
            if (StringUtils.isEmpty(basicOrder.getBusinessOrderNo())) {
                basicOrder.setBusinessOrderNo(basicOrder.getOrderCode());
                //接口传过来的业务订单号为空，则业务订单号与订单编号相同
                businessOrderNo = true;

            }
            this.ordOrderMapper.insertSelective(basicOrder);
        }
     // 保存开票信息表
        if (billingInfo != null) {
            billingInfo.setBillingInfoId(idGenerator.getLong());
            billingInfo.setCreateTime(new Date());
            billingInfo.setUpdateTime(new Date());
            this.ordBillingInfoMapper.insertSelective(billingInfo);
        }
        // 保存支付流水表
        if (payFlowing != null) {
            payFlowing.setPayId(idGenerator.getLong());
            payFlowing.setPayAmount(basicOrder.getPayAmount());
            payFlowing.setCreateTime(new Date());
            payFlowing.setUpdateTime(new Date());
            this.ordPayFlowingMapper.insertSelective(payFlowing);
        }
        for (GoodsDTO goods : goodsList) {
            OrdOrder childrenOrder = new OrdOrder();
            childrenOrder = basicOrder;
            childrenOrder.setOrderId(this.idGenerator.getLong());
            childrenOrder.setParentId(parentOrderId);
            childrenOrder.setOrderCode(generateOrderCode());
            if (businessOrderNo) {
                childrenOrder.setBusinessOrderNo(childrenOrder.getOrderCode());
            } else {
                //对于办税助手的创单，若业务订单号为空，则取订单编号作为业务订单号
                if(StringUtils.isEmpty(basicOrder.getBusinessOrderNo())){
                    childrenOrder.setBusinessOrderNo(childrenOrder.getOrderCode());
                } else{
                    childrenOrder.setBusinessOrderNo(basicOrder.getBusinessOrderNo());
                }
            }
            if (billingInfo != null)
                childrenOrder.setBillingInfoId(billingInfo.getBillingInfoId());
            childrenOrder.setSkuId(goods.getGoodsSkuId());
            childrenOrder.setGoodsId(goods.getGoodsId());
            childrenOrder.setProductId(goods.getProductId());

            Integer qty = goods.getGoodsQuantity();
            childrenOrder.setGoodsQuantity(qty);
            childrenOrder.setGoodsQuantityUnit(goods.getGoodsQuantityUnit());
            childrenOrder.setGoodsOriginalPrice(goods.getGoodsOriginalPrice().multiply(new BigDecimal(qty)));
            childrenOrder.setGoodsAmount(goods.getGoodsDiscountPrice().multiply(new BigDecimal(qty)));
            childrenOrder.setFreight(goods.getFreight());
            childrenOrder.setDiscountAmount(goods.getDiscountAmount());
            childrenOrder.setPayAmount((childrenOrder.getGoodsAmount().add(childrenOrder.getFreight()))
                    .subtract(childrenOrder.getDiscountAmount()));
            if (flag) {
                childrenOrder.setIsParent(OrderConstant.ONE);
            } else {
                childrenOrder.setIsParent(OrderConstant.ZERO);
            }
            childrenOrder.setOpeningMode(goods.getOpeningMode());
            childrenOrder.setServiceMode(goods.getServiceMode());
            childrenOrder.setServiceTermValue(goods.getServiceTermValue());
            childrenOrder.setServiceTermUnit(goods.getServiceTermUnit());
            childrenOrder.setAreaCode(goods.getAreaCode());
            childrenOrder.setCreateTime(new Date());
            childrenOrder.setUpdateTime(new Date());
            if (goods.getOpeningMode() == OrderConstant.ONE) {
                // 及时开通
                childrenOrder.setServiceStatus(OrderConstant.TWO);// 服务中
                childrenOrder.setServiceStartDate(new Date());
                Date endDate = null;
                if (goods.getServiceTermUnit() == OrderConstant.ZERO) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.DATE, goods.getServiceTermValue());
                    endDate = calendar.getTime();
                } else if (goods.getServiceTermUnit() == OrderConstant.ONE) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.MONTH, goods.getServiceTermValue());
                    endDate = calendar.getTime();
                } else if (goods.getServiceTermUnit() == OrderConstant.TWO) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR, goods.getServiceTermValue());
                    endDate = calendar.getTime();
                }
                childrenOrder.setServiceEndDate(endDate);
            }
            this.ordOrderMapper.insertSelective(childrenOrder);
            // 订单属性值处理
            if (goods.getGoodsAttributeGroup() != null && goods.getGoodsAttributeGroup().size() > 0) {
                for (GoodsAttributeDTO goodsAttribute : goods.getGoodsAttributeGroup()) {
                    OrdOrderAttribute orderAttribute = new OrdOrderAttribute();
                    orderAttribute.setOrderAttributeId(this.idGenerator.getLong());
                    orderAttribute.setOrderId(childrenOrder.getOrderId());
                    orderAttribute.setAttributeId(Long.valueOf(goodsAttribute.getAttributeId()));
                    orderAttribute.setAttributeValueId(Long.valueOf(goodsAttribute.getAttributeValueId()));
                    orderAttribute.setAttributeValue(goodsAttribute.getAttributeValue());
                    orderAttribute.setCreateUser(basicOrder.getCreateUser());
                    orderAttribute.setCreateTime(new Date());
                    orderAttribute.setUpdateUser(basicOrder.getUpdateUser());
                    orderAttribute.setUpdateTime(new Date());
                    this.ordOrderAttributeMapper.insertSelective(orderAttribute);
                }
            }

            OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
            ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
            ordPayFlowingRef.setPayId(payFlowing.getPayId());
            ordPayFlowingRef.setOrderId(childrenOrder.getOrderId());
            ordPayFlowingRef.setCreateUser(basicOrder.getCreateUser());
            ordPayFlowingRef.setCreateTime(new Date());
            ordPayFlowingRef.setUpdateTime(new Date());
            ordPayFlowingRef.setUpdateUser(basicOrder.getUpdateUser());
            this.ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);

            // 保存配送表
            if (deliver != null) {
                deliver.setDeliveryId(this.idGenerator.getLong());
                deliver.setOrderId(childrenOrder.getOrderId());
                deliver.setCreateTime(new Date());
                deliver.setUpdateTime(new Date());
                this.ordDelinveryMapper.insertSelective(deliver);
            }

        }
    }

    /**
     * 获取客户id(接口) TODO。
     *
     * @param param
     * @return String
     */
    private Map<String, Object> generateCusId(Map<String, String> param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String customerUrl = String.format("http://customer%s/inner/customerService/insertCustomerOnly",
                "");
        RestResponse restResponse = restClient.post(customerUrl, param);
        if ("0".equals(restResponse.getHead().getErrorCode())) {
            // 获取返回的客户id
            Map<String, String> map = (Map<String, String>) restResponse.getBody();
            if (null == map) {
                resultMap.put("errorCode", "1");
                resultMap.put("errorMsg", "客户信息没有");
            }
            String customerId = map.get("cusId");
            resultMap.put("errorCode", "0");
            resultMap.put("customerId", customerId);
            return resultMap;
        } else {
            // 调用客户接口出错返回
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg", "处理客户信息异常" + restResponse.getHead().getErrorMsg());
            return resultMap;
        }
    }

    /**
     * 查询商品信息(接口)
     * @param goodsSku
     * @return
     */
    private Map<String, Object> queryGoods(String goodsSku) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        QueryGoodInfoBySkuDTO queryGoods = new QueryGoodInfoBySkuDTO();
        queryGoods.setGoodsSku(goodsSku);
        RestResponse<QueryGoodsBySkuToOrderDTO> restResponse = this.goodsApi.queryGoodInfoBySku(queryGoods);
        if (restResponse != null && restResponse.getBody() != null) {
            GoodsDTO goodsDTO = new GoodsDTO();
            goodsDTO.setGoodsOriginalPrice(restResponse.getBody().getPrice());
            goodsDTO.setDiscountAmount(new BigDecimal(0));
            goodsDTO.setGoodsDiscountPrice(restResponse.getBody().getPrice());
            goodsDTO.setFreight(new BigDecimal(0));// 运费默认0
            goodsDTO.setGoodsSkuId(restResponse.getBody().getGoodsSkuId());
            goodsDTO.setGoodsId(restResponse.getBody().getGoodsId());
            goodsDTO.setProductId(restResponse.getBody().getProductId());
            goodsDTO.setGoodsQuantityUnit(restResponse.getBody().getProductUnit());
            goodsDTO.setAreaCode(restResponse.getBody().getAreaCode());
            String serviceTerm = restResponse.getBody().getServiceTerm();
            if (StringUtils.isEmpty(serviceTerm)) {
                // 按字数服务
                goodsDTO.setServiceMode(OrderConstant.ONE);
            } else {
                // 按时间服务
                goodsDTO.setServiceMode(OrderConstant.ZERO);
                String termValue = serviceTerm.substring(0, serviceTerm.length() - 1);
                goodsDTO.setServiceTermValue(Integer.valueOf(termValue));
                String termUnit = serviceTerm.substring(serviceTerm.length() - 1, serviceTerm.length());
                if ("D".equals(termUnit)) {
                    goodsDTO.setServiceTermUnit(OrderConstant.ZERO);
                } else if ("M".equals(termUnit)) {
                    goodsDTO.setServiceTermUnit(OrderConstant.ONE);
                } else if ("Y".equals(termUnit)) {
                    goodsDTO.setServiceTermUnit(OrderConstant.TWO);
                }
            }
            goodsDTO.setOpeningMode(restResponse.getBody().getOpeningWay());
            resultMap.put("goodsDTO", goodsDTO);
            return resultMap;
        } else {
            // 调用商品接口出错返回
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg", "根据sku查询商品信息异常" + restResponse.getHead().getErrorMsg());
            return resultMap;
        }

    }

    /**
     * 生成订单编号 TODO。
     *
     * @return String
     */
    private String generateOrderCode() {
        String prefixStr = LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = this.idUtil.createSequenceId(OrderConstant.ORDER_CODE_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    /**
     * 生成支付流水 TODO。
     *
     * @return String
     */
    private String generatePayNo() {
        String prefixStr = OrderConstant.ORDER_PAY_NO_PREFIX + LocalDateUtil.getTodayStr(LocalDateUtil.YMD);
        String suffix = this.idUtil.createSequenceId(OrderConstant.ORDER_PAY_NO_SEQUENCE_KEY);
        return prefixStr + suffix;
    }

    @Override
    public Long selectOrderIdByOrderCode(String orderCode) {
        return ordOrderExtMapper.selectOrderIdByOrderCode(orderCode);
    }

    @Override
    public int cancelOrderAndChildOrderByOrderId(Long orderId) {
        return ordOrderExtMapper.cancelOrderAndChildOrderByOrderId(orderId);
    }

    @Override
    public boolean isCancelOrder(String orderCode) {
        Long orderId = selectOrderIdByOrderCode(orderCode);
        if (null == orderId) {
            // 返回此orderCoder无效
            return false;
        }
        // 更新订单状态，获取是否更新成功
        if (cancelOrderAndChildOrderByOrderId(orderId) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int bathUpdateOrderPayStatus(int payStatus, int orderStatus, List<OrdPayFlowingDTO> payFlowingList) {
        return ordOrderExtMapper.bathUpdateOrderPayStatus(payStatus, orderStatus, payFlowingList);
    }

    public Map<String, Object> updateOrderForProxy(UpdateOrderInfoDTO updateOrderInfoDTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断订单是否存在
        OrdOrder orderInfo = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(
                updateOrderInfoDTO.getBusinessOrderNo(), updateOrderInfoDTO.getChannel());
        if (null == orderInfo) {
            map.put("errorCode", "1");
            map.put("errorMsg", "订单不存在！");
            return map;
        }
        // 根据开票信息id进行更改或者新增信息
        Long billingInfoId = orderInfo.getBillingInfoId();
        Long newBillingInfoId = null;
        // 更新订单
        OrdOrder newOrderInfo = new OrdOrder();
        OrdBillingInfo ordBillingInfo = new OrdBillingInfo();
        BeanUtils.copyProperties(updateOrderInfoDTO, newOrderInfo);
        BeanUtils.copyProperties(updateOrderInfoDTO, ordBillingInfo);
        // 放入orderid主键
        newOrderInfo.setOrderId(orderInfo.getOrderId());
        if (null == billingInfoId) {
            newBillingInfoId = idGenerator.getLong();
            newOrderInfo.setBillingInfoId(newBillingInfoId);
        }
        if (OrderConstant.ZERO == updateOrderInfoDTO.getNeedInvoice()) {
            newOrderInfo.setBillingStatus(OrderConstant.BILLING_STATUS_NO);
        } else if (OrderConstant.ONE == updateOrderInfoDTO.getNeedInvoice()) {
            newOrderInfo.setBillingStatus(OrderConstant.BILLING_STATUS_NO_NEED);
        }
        int result = ordOrderMapper.updateByPrimaryKeySelective(newOrderInfo);
        if (result > 0) {
            if (null != billingInfoId) {
                // 更新
                ordBillingInfo.setBillingInfoId(billingInfoId);
                ordBillingInfoMapper.updateByPrimaryKeySelective(ordBillingInfo);
            } else {
                // 新增
                ordBillingInfo.setBillingInfoId(newBillingInfoId);
                ordBillingInfoMapper.insertSelective(ordBillingInfo);
            }
            map.put("errorCode", "0");
            map.put("errorMsg", "订单更新成功！");
        } else {
            map.put("errorCode", "1");
            map.put("errorMsg", "订单更新不成功！");
        }
        return map;
    }

    @Override
    public Map<String, Object> setAccountantInfo(SetAccountantInfoDTO setAccountantInfoDTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断会计主管、会计助理两个是否存在一个
        if (StringUtils.isEmpty(setAccountantInfoDTO.getAccountingAssistantId())
                && StringUtils.isEmpty(setAccountantInfoDTO.getAccountingSupervisorId())) {
            throw new FtcspRuntimeException("00000002", (Object) "会计主管或者会计助理");
        }
        // 验证订单是否存在
        OrdOrder orderInfo = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(
                setAccountantInfoDTO.getBusinessOrderNo(), setAccountantInfoDTO.getChannel());
        if (null == orderInfo) {
            throw new FtcspRuntimeException("00000002", (Object) "订单");
        }
        // 判断depId是否存在
        String selectDepId = getDepIdIsExist(setAccountantInfoDTO.getFactoryId());
        if (!setAccountantInfoDTO.getFactoryId().equals(selectDepId)) {
            throw new FtcspRuntimeException("00000002", (Object) "工厂ID");
        }
        // 查询订单扩展表是否存在数据
        OrdOrderExtendDTO ordOrderExtendDTO = ordOrderExtendExtMapper.selectByOrderId(orderInfo.getOrderId());
        OrdOrderExtend ordOrderExtend = new OrdOrderExtend();
        if (null != ordOrderExtendDTO) {
            // 更新数据
            ordOrderExtend.setOrderExtendId(ordOrderExtendDTO.getOrderExtendId());
            ordOrderExtend.setAccountingAssistantId(setAccountantInfoDTO.getAccountingAssistantId());
            ordOrderExtend.setAccountingSupervisorId(setAccountantInfoDTO.getAccountingSupervisorId());
            ordOrderExtend.setDepId(setAccountantInfoDTO.getFactoryId());
            ordOrderExtendMapper.updateByPrimaryKeySelective(ordOrderExtend);
        } else {
            // 新增数据
            ordOrderExtend.setOrderExtendId(idGenerator.getLong());
            ordOrderExtend.setOrderId(orderInfo.getOrderId());
            ordOrderExtend.setAccountingAssistantId(setAccountantInfoDTO.getAccountingAssistantId());
            ordOrderExtend.setAccountingSupervisorId(setAccountantInfoDTO.getAccountingSupervisorId());
            ordOrderExtend.setDepId(setAccountantInfoDTO.getFactoryId());
            ordOrderExtendMapper.insertSelective(ordOrderExtend);
        }
        map.put("errorCode", "0");
        map.put("errorMsg", "修改成功");
        return map;
    }

    @Override
    public Object queryPersonalOrderPage(PageQueryParam param) {
        String userId = param.getString("userId");
        if (null == userId || "".equals(userId)) {
            return new RestResponse("1", "查询条件出错：userId不能为空");
        }
        // 验证用户id是否存在
        // if(!getUserIdIsExist(userId)){
        // throw new FtcspRuntimeException("00000002",(Object)"用户的userId");
        // }
        // 根据用户查询分页查询父订单的情况。
        PageQueryResult<OrdOrderDTO> pageQueryResult = ordOrderExtMapper.queryPersonalOrderPage(param);
        // 把sku放到set里面。去重
        HashSet<Long> skuSet = new HashSet<>();
        // 父Map<String,OrdOrderDTO>,key为父订单id，value为其对象
        Map<Long, OrdOrderDTO> OrdOrderDTOParentMap = new HashMap<>();
        // 放父订单id
        List<Long> parentOrdList = new ArrayList<>();
        // 放存map的
        for (OrdOrderDTO ordOrderDTO : pageQueryResult.getData()) {
            parentOrdList.add(ordOrderDTO.getOrderId());
            OrdOrderDTOParentMap.put(ordOrderDTO.getOrderId(), ordOrderDTO);
            skuSet.add(ordOrderDTO.getSkuId());
        }
        // 查子订单
        if (parentOrdList.isEmpty()) {
            return pageQueryResult;
        }
        List<OrdOrderDTO> childOrderList = ordOrderExtMapper.queryChildOrder(userId, parentOrdList);
        // 创建MAP<父订单key，子订单类>
        Map<Long, List<OrdOrderDTO>> childOrdMap = new HashMap<>();
        for (OrdOrderDTO ordOrderDTO : childOrderList) {
            Long parentOrdId = ordOrderDTO.getParentId();
            if (childOrdMap.containsKey(parentOrdId)) {
                childOrdMap.get(parentOrdId).add(ordOrderDTO);
            } else {
                List<OrdOrderDTO> childOrdList = new ArrayList<>();
                childOrdList.add(ordOrderDTO);
                childOrdMap.put(parentOrdId, childOrdList);
            }
            // 将所有订单sku进行组合。去重。
            skuSet.add(ordOrderDTO.getSkuId());
        }
        // 内部接口查询sku的属性组和sku基础信息
        List<Long> skuList = new ArrayList<Long>(skuSet);
        Map<Long, GetOrederGoodSkuDTO> skuIdMap = querySkuContent(skuList);
        // 将sku根据组合组装到订单里面。
        for (Long parentOrdId : parentOrdList) {
            OrdOrderDTO parentOrd = OrdOrderDTOParentMap.get(parentOrdId);
            Long skuId = parentOrd.getSkuId();
            if (!skuIdMap.containsKey(skuId)) {
                continue;
            }
            // 复制商品对象放入父对象里面
            GetOrederGoodSkuDTO parentDto = new GetOrederGoodSkuDTO();
            BeanUtils.copyProperties(skuIdMap.get(skuId), parentDto);
            parentDto.setQuantity(parentOrd.getGoodsQuantity());
            parentDto.setOriginalPrice(parentOrd.getGoodsOriginalPrice());
            parentDto.setDiscountPrice(parentOrd.getGoodsAmount());
            parentDto.setCustomerId(parentOrd.getCustomerId());
            parentDto.setServiceStartDate(parentOrd.getServiceStartDate());
            parentDto.setServiceEndDate(parentOrd.getServiceEndDate());
            List<GetOrederGoodSkuDTO> goodsGroup = new ArrayList<>();
            goodsGroup.add(parentDto);
            parentOrd.setGoodsGroup(goodsGroup);
            if (childOrdMap.containsKey(parentOrdId)) {
                for (OrdOrderDTO childOrd : childOrdMap.get(parentOrdId)) {
                    GetOrederGoodSkuDTO childDto = new GetOrederGoodSkuDTO();
                    Long skuIdByChild = childOrd.getSkuId();
                    if (!skuIdMap.containsKey(skuIdByChild)) {
                        continue;
                    }
                    BeanUtils.copyProperties(skuIdMap.get(skuIdByChild), childDto);
                    childDto.setQuantity(childOrd.getGoodsQuantity());
                    childDto.setOriginalPrice(childOrd.getGoodsOriginalPrice());
                    childDto.setDiscountPrice(childOrd.getGoodsAmount());
                    childDto.setCustomerId(childOrd.getCustomerId());
                    childDto.setServiceStartDate(childOrd.getServiceStartDate());
                    childDto.setServiceEndDate(childOrd.getServiceEndDate());
                    parentOrd.getGoodsGroup().add(childDto);
                }
            }
        }
        // 返回订单
        return pageQueryResult;
    }

    private Map<Long, GetOrederGoodSkuDTO> querySkuContent(List<Long> skuList) {
        QuerySkuContentBySkuListDTO querySkuContentBySkuListDTO = new QuerySkuContentBySkuListDTO();
        querySkuContentBySkuListDTO.setSkuList(skuList);
        QuerySkuContentBySkuListResultDTO resultDTO = goodsApi.querySkuContentBySkuList(querySkuContentBySkuListDTO)
                .getBody();
        List<GetOrederGoodSkuDTO> skuContentlist = new ArrayList<GetOrederGoodSkuDTO>();
        for (com.foresee.ftcsp.goods.manual.dto.GetOrederGoodSkuDTO getDto : resultDTO.getGetOrederGoodSkuDTO()) {
            GetOrederGoodSkuDTO setDto = new GetOrederGoodSkuDTO();
            BeanUtils.copyProperties(getDto, setDto);
            skuContentlist.add(setDto);
        }
        Map<Long, GetOrederGoodSkuDTO> skuMap = new HashMap<>();
        for (GetOrederGoodSkuDTO getOrederGoodSkuDTO : skuContentlist) {
            skuMap.put(getOrederGoodSkuDTO.getGoodsSkuId(), getOrederGoodSkuDTO);
        }
        return skuMap;
    }

    @Autowired
    private CustomerInnerApi customerInnerApi;

    @Override
    public PageQueryResult<OrdOrderListDTO> queryOrderList(PageQueryParam param) {
        // 对地区编码进行判断，对应哪个节点
        String areaCode = param.getString("areaCode");

        if (areaCode == null) {
            return ordOrderExtMapper.queryOrderList(param);
        } else {
            areaCode = areaCode.trim();
            if ("".equals(areaCode)) {
                return ordOrderExtMapper.queryOrderList(param);
            }
            if ("00".equals(areaCode)) {
                // 总节点，不用进行筛选
                param.put("areaCode", null);
                return ordOrderExtMapper.queryOrderList(param);
            } else if ("0000".equals(areaCode.substring(2, 6))) {
                // 省节点
                param.put("isPre", "yes");
                return ordOrderExtMapper.queryOrderList(param);
            } else if ("00".equals(areaCode.substring(4, 6))) {
                // 市节点
                param.put("isCity", "yes");
                return ordOrderExtMapper.queryOrderList(param);
            } else {
                // 县或区节点
                param.put("isXian", "yes");
            }
            return ordOrderExtMapper.queryOrderList(param);
        }
    }

    // 未完成，提交代码需要
    @Override
    public long addOrdOrder(AddOrdOrderDTO addOrdOrderDTO) {
        // 添加订单表
        long orderId = idGenerator.getLong();
        OrdOrder record = new OrdOrder();
        BeanUtils.copyProperties(addOrdOrderDTO, record);
        record.setOrderId(orderId);
        record.setOrderCode(this.generateOrderCode());
        ordOrderMapper.insertSelective(record);
        return orderId;
    }

    @Override
    public boolean updateOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO) {
        OrdOrder ordOrder = new OrdOrder();
        BeanUtils.copyProperties(updateOrderStatusDTO, ordOrder);
        int i = ordOrderMapper.updateByPrimaryKeySelective(ordOrder);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断depId是否存在
     *
     * @param depId
     * @return Object
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月25日 TODO。
     */
    public String getDepIdIsExist(String depId) {
        // 判断depId是否存在
        RestRequestParam queryParam = new RestRequestParam();
        queryParam.put("depId", depId);
        String url = String.format("http://foresee-gateway-server/security%s/inner/departmentService/queryDepIdIsExist",
                restClient.getDeveloperName());
        RestResponse restResponse = restClient.post(url, queryParam);
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) restResponse.getBody();
        if (null == map) {
            return null;
        }
        String selectDepId = map.get("depId").toString();
        return selectDepId;
    }

    @Override
    public boolean addOrdOrdeReceivablesr(AddOrdOrdeReceivablesrDTO addOrdOrdeReceivablesrDTO) {
        try {

            long orderId = addOrdOrdeReceivablesrDTO.getOrderId();// 订单id
            // 新增支付流水表，生成payId
            long payId = idGenerator.getLong();
            OrdPayFlowing ordPayFlowing = new OrdPayFlowing();
            BeanUtils.copyProperties(addOrdOrdeReceivablesrDTO, ordPayFlowing);
            ordPayFlowing.setPayId(payId);
            ordPayFlowing.setPayNo(this.generatePayNo());// 支付流水
            ordPayFlowingMapper.insertSelective(ordPayFlowing);

            // 新增订单流水表
            long orderPayId = idGenerator.getLong();
            OrdPayFlowingRef OrdPayFlowingRef = new OrdPayFlowingRef();
            OrdPayFlowingRef.setOrderId(orderId);
            OrdPayFlowingRef.setPayId(payId);
            OrdPayFlowingRef.setOrderPayId(orderPayId);
            ordPayFlowingRefMapper.insertSelective(OrdPayFlowingRef);

            // 修改订单表
            // 根据id取出该条数据的付款方式openingMode,
            // 如果是1-及时开通,2-人工开通，不需计算服务时间
            // 如果是3-付款开通，则需计算服务时间
            OrdOrder ordOrder = ordOrderMapper.selectByPrimaryKey(orderId);
            Integer openingMode = ordOrder.getOpeningMode();
            if (openingMode.intValue() == OrderConstant.THREE) {
                // 计算服务时间
                Integer serviceTermValue = ordOrder.getServiceTermValue();// 服务期限的值
                Integer serviceTermUnit = ordOrder.getServiceTermUnit();// 服务期限的单位，0-天，1-月，2,-年
                Date serviceStartDate = new Date();// 服务开始时间,默认当前时间
                Date serviceEndDate = null;// 服务结束时间
                if (serviceTermUnit.intValue() == OrderConstant.ZERO) {
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.DATE, serviceTermValue);
                    serviceEndDate = ca.getTime();
                } else if (serviceTermUnit.intValue() == OrderConstant.ONE) {
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.MONTH, serviceTermValue);
                    serviceEndDate = ca.getTime();
                } else if (serviceTermUnit.intValue() == OrderConstant.TWO) {
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.YEAR, serviceTermValue);
                    serviceEndDate = ca.getTime();
                }
                ordOrder.setServiceStartDate(serviceStartDate);
                ordOrder.setServiceEndDate(serviceEndDate);
            }
            ordOrder.setOrderStatus(OrderConstant.TWO);// 服务中
            ordOrder.setPayStatus(OrderConstant.FOUR);// 已付款
            ordOrder.setServiceStatus(OrderConstant.TWO);// 服务中
            ordOrderMapper.updateByPrimaryKeySelective(ordOrder);

            // 新增账单表，生成billId
            OrdBill ordBill = new OrdBill();
            long billId = idGenerator.getLong();
            ordBill.setOrderId(orderId);
            ordBill.setBillId(billId);
            String billDateStr = LocalDateUtil.getDateStr(LocalDateUtil.YMD, new Date());
            String billCodeKey = String.format(idUtil.createSequenceId("bill_code_key"), "1");
            ordBill.setBillCode(billDateStr + billCodeKey);
            ordBill.setPayId(payId);
            ordBill.setBillType(OrderConstant.ZERO);// 0-全额
            ordBill.setBillAmount(addOrdOrdeReceivablesrDTO.getPayAmount());// 金额
            ordBill.setPayStatus(OrderConstant.ZERO);// 已付
            ordBill.setPayTime(addOrdOrdeReceivablesrDTO.getPayTime());// 支付时间
            ordBill.setBillStartTime(ordOrder.getServiceStartDate());
            ordBill.setBillEndTime(ordOrder.getServiceEndDate());
            ordBillMapper.insertSelective(ordBill);

            // 新增账单与流水关联表
            OrdBillPayRef ordBillPayRef = new OrdBillPayRef();
            long billPayRefId = idGenerator.getLong();
            ordBillPayRef.setBillPayRefId(billPayRefId);
            ordBillPayRef.setBillId(billId);
            ordBillPayRef.setPayId(payId);
            ordBillPayRefMapper.insertSelective(ordBillPayRef);

            //新增账单项目表
            OrdBillItem ordBillItem = new OrdBillItem();
            long billItemId = idGenerator.getLong();
            ordBillItem.setBillItemId(billItemId);
            ordBillItem.setBillId(billId);
            ordBillItem.setItemType(OrderConstant.ZERO);//默认正常行-0

            String itemName = "项目名称";
            ordBillItem.setItemName(itemName);//接口未提供，假数据

            ordBillItem.setUnit(ordOrder.getGoodsQuantityUnit().toString());//计量单位
            ordBillItem.setSpec("0");//规格型号
            ordBillItem.setQty(ordOrder.getGoodsQuantity());//项目数量

            BigDecimal amount = ordOrder.getGoodsAmount();
            BigDecimal price = amount.divide(new BigDecimal(ordOrder.getGoodsQuantity()));

            ordBillItem.setPrice(price);//单价
            ordBillItem.setAmount(amount);//项目金额
            ordBillItem.setTaxRate(new BigDecimal("0.12"));//税率,由于调用的接口没有提供，先固定一个数据
            ordBillItemMapper.insertSelective(ordBillItem);
        } catch (Exception e) {
            LOGGER.error(e);
            return false;
        }
        return true;
    }

    /**
     * 判断depId是否存在
     *
     * @param userId
     * @return String
     * @author linliangwei@foresee.com.cn
     * @date 2017年8月26日 TODO。
     */
    public boolean getUserIdIsExist(String userId) {
        boolean result = false;
        // 判断depId是否存在
        RestRequestParam queryParam = new RestRequestParam();
        queryParam.put("userId", userId);
        String url = String.format(
                "http://foresee-gateway-server/security%s/inner/departmentService/queryUserIdIsExist",
                restClient.getDeveloperName());
        RestResponse restResponse = restClient.post(url, queryParam);
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) restResponse.getBody();
        if (null == map || map.isEmpty()) {
            return result;
        }
        String selectUserId = map.get("userId").toString();
        if (userId != null && userId.equals(selectUserId)) {
            result = true;
        }
        return result;
    }

    private String conversionCoding(String channel) {
        String resultStr = "";
        switch (channel) {
            case "0":
                resultStr = "platform";
                break;
            case "1":
                resultStr = "website";
                break;
            case "2":
                resultStr = "jcdz";
                break;
        }
        return resultStr;
    }

	@Override
	public Map<String,Object> insertRewardOrder(CreateRewardOrderDTO createRewardOrderDTO) {
		Map<String, Object> map = new HashMap<String, Object>();

		if("null".equals(createRewardOrderDTO.getTaxIdentification().trim())){
		    map.put("errorCode", "1");
		    map.put("errorMsg","参数[tax_identification]不能为null");
            return map;
		}

		String[] supportPayWays = createRewardOrderDTO.getSupportPayWays();
		if( supportPayWays!=null && supportPayWays.length > 0){
		    for(String payWay:supportPayWays){
		        if( !payWay.equals(PayConstant.PAYWAY_ALIPAY+"") &&
		            !payWay.equals(PayConstant.PAYWAY_WECHAT+"") &&
		            !payWay.equals(PayConstant.PAYWAY_UNION+"" ) &&
		            !payWay.equals(PayConstant.PAYWAY_CAIBI+"")){

		            map.put("errorCode", "1");
		            map.put("errorMsg", "请输入支持的支付方式");
		            return map;
		        }
		    }
		} else{
		    //默认支持（0:支付宝,1:微信,2:银联,4:财币）这四种支付方式
		    supportPayWays = new String[]{
		            PayConstant.PAYWAY_ALIPAY+"",
		            PayConstant.PAYWAY_WECHAT+"",
		            PayConstant.PAYWAY_UNION+"",
		            PayConstant.PAYWAY_CAIBI+""
		            };
		}






		BigDecimal payAmount = null;
		try {
			payAmount = new BigDecimal(createRewardOrderDTO.getPayAmount());
			if(payAmount.doubleValue()==0D){
				map.put("errorCode", "1");
				map.put("errorMsg", "打赏金额不能为零");
				return map;
			}
		}catch (NumberFormatException e){
			map.put("errorCode", "1");
			map.put("errorMsg", "请输入正确的金额");
			return map;
		}
		String payRetUrl = createRewardOrderDTO.getPayRetUrl();
		String payNotifyUrl = createRewardOrderDTO.getPayNotifyUrl();

		RestResponse<PdtGoodsSku> response = goodsApi.queryIfExistByGoodsSkuId(createRewardOrderDTO.getGoodsSkuId());
		if(!"0".equals(response.getHead().getErrorCode())){
		    map.put("errorCode", "1");
		    map.put("errorMsg",response.getHead().getErrorMsg());
            return map;
		}
		if(response.getBody() == null || StringUtils.isEmpty(response.getBody().getGoodsId())){
			map.put("errorCode", "1");
			map.put("errorMsg", "该商品sku不存在");
			return map;
		}
		Long goodsId = response.getBody().getGoodsId();
		//支付来源获取
		String payAppId = null;
		try {
			payAppId = dictHolder.getDictItem("pay_appId_channel",createRewardOrderDTO.getChannel()).get("itemValue");
		} catch (NullPointerException e) {
			map.put("errorCode", "1");
			map.put("errorMsg", "没有该来源对应支付配置");
			return map;
		}
		//客户获取
		Map<String, String> cusParam = new HashMap<String, String>();
		cusParam.put("cusType", createRewardOrderDTO.getCusType());
		cusParam.put("cusName", createRewardOrderDTO.getCusName());
		cusParam.put("taxIdentificationNumber", createRewardOrderDTO.getTaxIdentification());
		cusParam.put("inputChannel",createRewardOrderDTO.getChannel());
		cusParam.put("userId", createRewardOrderDTO.getUserId());
		Map<String, Object> resultMap = this.generateCusId(cusParam);
		if ("1".equals(resultMap.get("errorCode"))) {
			return resultMap;
		}
		String customerId = (String) resultMap.get("customerId");

		//订单是否存在
		OrdOrder ordOrder = this.ordOrderExtMapper.selectByBusinessOrderNoAndChannel(createRewardOrderDTO.getBusinessOrderNo(),
					Integer.valueOf(createRewardOrderDTO.getChannel()));
		if (ordOrder != null) {
			if(!OrderConstant.PAY_STATUS_PREPARE_TO_PAY.equals(ordOrder.getPayStatus())){
				//若该订单为待付款状态则不用创建订单直接跳转支付；
				map.put("errorCode", "1");
				map.put("errorMsg", "业务订单号已存在");
				return map;
			}
		}else {
			boolean flag = redisClient.setNx("ordercenter_inserReward_wait" + createRewardOrderDTO.getBusinessOrderNo() + createRewardOrderDTO.getChannel(), "wait", 3l);
			if (!flag) {
				map.put("errorCode", "1");
				map.put("errorMsg", "此业务订单号正在创建中，请不要重复提交");
				return map;
			}

			ordOrder = new OrdOrder();
			Long orderId = idGenerator.getLong();
			String orderCode = generateOrderCode();
			Date date = new Date();
			ordOrder.setOrderId(orderId);
			ordOrder.setOrderCode(orderCode);
			ordOrder.setBusinessOrderNo(createRewardOrderDTO.getBusinessOrderNo());
			ordOrder.setPayAmount(payAmount);
			ordOrder.setChannel(Integer.valueOf(createRewardOrderDTO.getChannel()));
			ordOrder.setGoodsId(goodsId);
			ordOrder.setSkuId(createRewardOrderDTO.getGoodsSkuId());
			ordOrder.setCustomerId(customerId);
			ordOrder.setOrderUser(createRewardOrderDTO.getUserId());
			ordOrder.setCreateTime(date);
			ordOrder.setOrderTime(date);
			//应开票要求，要有商品单价默认位商品价，数量暂时只允许为1
			ordOrder.setGoodsOriginalPrice(payAmount);
			ordOrder.setGoodsAmount(payAmount);
			ordOrder.setGoodsQuantity(1);
			//ordOrder.setIsParent(1);
			ordOrder.setOpeningMode(OrderConstant.OPENING_MODE_PAY);  //付款开通
			ordOrder.setPayStatus(OrderConstant.PAY_STATUS_PREPARE_TO_PAY);  //待付款
			ordOrder.setOrderType(OrderConstant.ORDER_TYPE_ADD); //类型为新增订单
			ordOrder.setDividePay(OrderConstant.NEED_DELIVERY_NO);  //全额
			ordOrder.setServiceMode(OrderConstant.SERVICE_MODE_TIMES);  //按次服务
			ordOrder.setOrderStatus(OrderConstant.ORDER_STATUS_PENDING_PAY);  //设置为待付款
			ordOrderMapper.insertSelective(ordOrder);

			OrdPayFlowing ordPayFlowing = new OrdPayFlowing();
			Long payId = idGenerator.getLong();
			ordPayFlowing.setPayId(payId);
			ordPayFlowing.setPayAmount(payAmount);
			ordPayFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_PREPARE_TO_PAY);  //待支付状态
			ordPayFlowing.setPayNo(createRewardOrderDTO.getBusinessOrderNo());
			ordPayFlowing.setPayAppId(payAppId);
			ordPayFlowingMapper.insertSelective(ordPayFlowing);

			OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
			ordPayFlowingRef.setOrderId(orderId);
			ordPayFlowingRef.setPayId(payId);
			ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
			ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);
		}
		//调用inner接口查询財币余额
		AccountQueryBalanceDTO balanceDTO = new AccountQueryBalanceDTO();
		balanceDTO.setUserId(createRewardOrderDTO.getUserId());
        balanceDTO.setAppId(AccountConstant.APPID);
        balanceDTO.setVersion("1.0");
        RestResponse<AccountQueryBalanceResultDTO> balanceDTORestResponse = null;
        try {
            balanceDTORestResponse = accountApi.queryBalance(balanceDTO);
        } catch (Exception e) {
            LOGGER.info("查询財币余额调用失败\n" + e.getMessage());
        }
        BigDecimal balance = new BigDecimal(0);
        if (balanceDTORestResponse != null && balanceDTORestResponse.getBody() != null) {
            balance = balanceDTORestResponse.getBody().getBalance();
        }
		//将信息转成map并存储进redis
		Map<String,Object> redisMap = new HashMap<String,Object>();
		redisMap.put("skuId",createRewardOrderDTO.getGoodsSkuId()+"");
		redisMap.put("goodsId",goodsId+"");
		redisMap.put("userId",createRewardOrderDTO.getUserId());
		redisMap.put("payRetUrl",payRetUrl);
		redisMap.put("payNotifyUrl",payNotifyUrl);
		redisMap.put("payAmount",ordOrder.getPayAmount().toString());
		redisMap.put("date", LocalDateUtil.getDateStr(LocalDateUtil.Y_M_D_HH_MM_SS, ordOrder.getOrderTime()));
		redisMap.put("payNo", ordOrder.getBusinessOrderNo());
		redisMap.put("payAppId", payAppId);
		redisMap.put("goodsDescribe", createRewardOrderDTO.getGoodsDescribe());
		redisMap.put("supportPayWays", supportPayWays);

		//redisMap.put("balance", balance.toString());
		redisClient.set("RewardOrderPayInfo" + createRewardOrderDTO.getBusinessOrderNo() + createRewardOrderDTO.getChannel(),
				Jackson.toJson(redisMap), 3 * 60 * 60 + 30);  //设置有效时间为三个小时，6秒为返回延时预留

		Map<String, String> signParam = new HashMap<>();
		String payTimestamp = String.valueOf(System.currentTimeMillis());
		signParam.put("businessOrderNo", "RewardOrderPayInfo" + createRewardOrderDTO.getBusinessOrderNo() + createRewardOrderDTO.getChannel());
		signParam.put("payTimestamp", payTimestamp);
		String sign = PaySign.genSign(signParam, SignEncryptConstant.RewardOrderPaySalt);
		String payPageUrl = String
				.format(payRewardConfig.getPayPageUrl() + "?businessOrderNo=%s&payTimestamp=%s&sign=%s"
						, "RewardOrderPayInfo" + createRewardOrderDTO.getBusinessOrderNo() + createRewardOrderDTO.getChannel()
						, payTimestamp, sign);
		map.put("errorCode", "0");
		map.put("errorMsg", "创建订单成功");
		map.put("payPageUrl", payPageUrl);
		map.put("orderCode", ordOrder.getOrderCode());

		return map;
	}

    @Override
    public boolean updateOrderByCusIdAndChannle(ChangOrderStatusByCusIdAndChannelDTO changOrderStatusByCusIdAndChannelDTO) {
        //对cusIds进行分割组装
        boolean toResult = false;
        String[] cusIds = changOrderStatusByCusIdAndChannelDTO.getCusIds().split(",");
        List<String> cusIdList = Arrays.asList(cusIds);
        //订单的终止状态
        int result = ordOrderExtMapper.updateOrderByCusIdAndChannle(cusIdList, changOrderStatusByCusIdAndChannelDTO.getChannel(), OrderConstant.ORDER_STATUS_TERMINATION,OrderConstant.SERVICE_STATUS_TERMINATION);
        if (result > 0) {
            toResult = true;
        }
        return toResult;
    }

    @Override
    public void createOrderAsynchronism(Map<String, String> map) {
        boolean success = redisClient.setNx(map.get("pay_biz_order_id") + "createOrderAsynchronism_wait" + map.get("source"),
                "wait", 3l);
        if (success) {
            OrdOrder order = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(map.get("pay_biz_order_id"),
                    Integer.parseInt(map.get("source")));
            //再次查询订单是否已经被创建过
            if (order == null) {

                Map<String, String> cusParam = new HashMap<String, String>();
                cusParam.put("cusType", map.get("cus_type"));
                cusParam.put("cusName", map.get("cus_name"));
                cusParam.put("taxIdentificationNumber", map.get("tax_identification"));
                cusParam.put("inputChannel", map.get("source"));
                Map<String, Object> resultMap = this.generateCusId(cusParam);
                if ("0".equals(resultMap.get("errorCode"))) {
                    String customerId = (String) resultMap.get("customerId");
                    order = new OrdOrder();
                    Long orderId = idGenerator.getLong();
                    String orderCode = generateOrderCode();
                    order.setOrderId(orderId);
                    order.setOrderCode(orderCode);
                    order.setBusinessOrderNo(map.get("pay_biz_order_id"));
                    order.setPayAmount(new BigDecimal(map.get("pay_amount")));
                    order.setChannel(Integer.parseInt(map.get("source")));
                    order.setGoodsId((Long) (Object) map.get("goodsId"));
                    order.setSkuId((Long) (Object) map.get("goods_sku_id"));
                    order.setCustomerId(customerId);
                    order.setCreateTime(new Date());
                    order.setOrderUser(map.get("order_user"));
                    //ordOrder.setIsParent(1);
                    order.setOpeningMode(OrderConstant.OPENING_MODE_PAY);  //付款开通
                    order.setPayStatus(OrderConstant.PAY_STATUS_PREPARE_TO_PAY);  //待付款
                    order.setOrderType(OrderConstant.ORDER_TYPE_ADD); //类型为新增订单
                    order.setDividePay(OrderConstant.NEED_DELIVERY_NO);  //全额
                    order.setServiceMode(OrderConstant.SERVICE_MODE_TIMES);  //按次服务
                    order.setOrderStatus(OrderConstant.ORDER_STATUS_PENDING_PAY);  //设置为待付款
                    ordOrderMapper.insertSelective(order);
                    OrdPayFlowing ordPayFlowing = new OrdPayFlowing();
                    Long payId = idGenerator.getLong();
                    String appId = map.get("pay_app_id");
                    if (!StringUtils.isEmpty(appId)) {

                    } else {
                        appId = dictHolder.getDictItemValue("pay_appId_channel", map.get("source"));
                    }
                    Integer payWay = PayConstant.PAYWAY_ALIPAY;
                    if (PayChannelConfig.getWeChatHtmlChannel().equals(map.get("pay_channel_id"))) {
                        payWay = PayConstant.PAYWAY_WECHAT;
                    } else if (PayChannelConfig.getUnionPayChannel().equals(map.get("pay_channel_id"))) {
                        payWay = PayConstant.PAYWAY_UNION;
                    } else if (PayChannelConfig.getAlipayHtmlChannel().equals(map.get("pay_channel_id"))) {
                        payWay = PayConstant.PAYWAY_ALIPAY;
                    }
                    ordPayFlowing.setPayId(payId);
                    ordPayFlowing.setPayAmount(new BigDecimal(map.get("pay_amount")));
                    ordPayFlowing.setPayState(0);  //待支付状态
                    ordPayFlowing.setPayNo((String) map.get("pay_biz_order_id"));
                    ordPayFlowing.setPayAppId(appId);
                    ordPayFlowing.setPayWay(payWay);
                    ordPayFlowingMapper.insertSelective(ordPayFlowing);

                    OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
                    ordPayFlowingRef.setOrderId(orderId);
                    ordPayFlowingRef.setPayId(payId);
                    ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
                    ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);
                    LOGGER.info("异步创建订单成功");
                } else {
                    LOGGER.info("创建订单失败，处理客户信息异常");
                }

            } else {
                LOGGER.info("订单已存在，不能进行重复创建");
            }
        } else {
            LOGGER.info("正在创建该订单，不能进行重复创建");
        }


    }

    @Override
    public OrdOrderDTO queryOrderByBusinessNoAndChannel(QueryOrderDTO queryOrderDTO) {
        return ordOrderExtMapper.queryOrderByBusinessNoAndChannel(queryOrderDTO.getBusinessOrderNo(), queryOrderDTO.getChannel());
    }

    @Override
    public Map<String, Object> createSynchronizedOrder(List<SynchronizedOrderDTO> synchronizedOrderList) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> orderCodeErrorMap = new HashMap<>();
        List<String> orderCodeSuccessList = new ArrayList<>();

        for (SynchronizedOrderDTO synchronizedOrderDTO : synchronizedOrderList) {
            RestRequestParam queryParam = new RestRequestParam();
            queryParam.put("taxId", synchronizedOrderDTO.getCustomerId());
            String url = String.format(
                    "http://gateway/customer/inner/customerServiceExt/queryCustomer",
                    restClient.getDeveloperName());
            RestResponse restResponse = restClient.post(url, queryParam);
            if ("0".equals(restResponse.getHead().getErrorCode())) {
                Map<String, String> map = (Map<String, String>) restResponse.getBody();
                if (map == null) {
                    orderCodeErrorMap.put(synchronizedOrderDTO.getOrderCode(), String.format("查询订单orderCode为%s的客户信息为空", synchronizedOrderDTO.getOrderCode()));
                    LOGGER.info(String.format("查询订单orderCode为%s的客户信息为空", synchronizedOrderDTO.getOrderCode()));
                } else {
                    synchronizedOrderDTO.setCustomerId(map.get("cusId"));
                    try {
                        insertSynchronizedOrder(synchronizedOrderDTO);
                        orderCodeSuccessList.add(synchronizedOrderDTO.getOrderCode());

                    } catch (Exception e) {
                        orderCodeErrorMap.put(synchronizedOrderDTO.getOrderCode(), e.toString());
                        LOGGER.error(e);
                    }
                }
            } else {
                orderCodeErrorMap.put(synchronizedOrderDTO.getOrderCode(), restResponse.getHead().getErrorMsg());
                LOGGER.info(String.format("查询订单orderCode为%s的客户信息异常:%s", synchronizedOrderDTO.getOrderCode(),
                        restResponse.getHead().getErrorMsg()));
            }

        }
        if (!orderCodeErrorMap.isEmpty()) {
            resultMap.put("errorCode", "1");
            resultMap.put("errorMsg", "部分订单插入/更新操作失败");
            resultMap.put("orderCodeErrorMap", orderCodeErrorMap);
            resultMap.put("orderCodeSuccessList", orderCodeSuccessList);
        } else {
            resultMap.put("errorCode", "0");
            resultMap.put("errorMsg", "保存成功");
            //resultMap.put("orderCodeSuccessList", orderCodeSuccessList);
        }
        return resultMap;
    }

    public void insertSynchronizedOrder(SynchronizedOrderDTO synchronizedOrderDTO) {

        //填充订单表数据
        OrdOrder order = new OrdOrder();
        Long orderId = idGenerator.getLong();
        order.setOrderId(orderId);
        order.setCustomerId(synchronizedOrderDTO.getCustomerId());
        order.setOrderCode(synchronizedOrderDTO.getOrderCode());
        order.setBusinessOrderNo(synchronizedOrderDTO.getOrderCode());
        order.setGoodsId(synchronizedOrderDTO.getGoodsId());
        order.setSkuId(synchronizedOrderDTO.getSkuId());
        order.setAreaCode(synchronizedOrderDTO.getAreaCode());
        order.setPayAmount(synchronizedOrderDTO.getPayAmount());
        order.setServiceStartDate(synchronizedOrderDTO.getServiceStartDate());
        order.setServiceEndDate(synchronizedOrderDTO.getServiceEndDate());
        order.setOrderTime(synchronizedOrderDTO.getServiceStartDate());
        order.setOrderStatus(synchronizedOrderDTO.getOrderStatus());
        order.setPayStatus(synchronizedOrderDTO.getPayStatus());
        order.setRefundStatus(synchronizedOrderDTO.getRefundStatus());
        order.setServiceStatus(synchronizedOrderDTO.getServiceStatus());
        order.setChannel(synchronizedOrderDTO.getChannel());
        order.setContactPerson(synchronizedOrderDTO.getContactPerson());
        order.setContactPhone(synchronizedOrderDTO.getContactPhone());
        order.setChannelOption(synchronizedOrderDTO.getChannelOption());
        //ordOrderMapper.insertSelective(order);


        //填充流水表数据
        OrdPayFlowing ordPayFlowing = new OrdPayFlowing();
        Long payId = idGenerator.getLong();
        ordPayFlowing.setPayId(payId);
        ordPayFlowing.setPayAmount(synchronizedOrderDTO.getPayAmount());
        if (synchronizedOrderDTO.getPayStatus() == 4) {
            ordPayFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_SUCCESS);  //待支付状态
        } else {
            ordPayFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_PREPARE_TO_PAY);  //已支付状态
        }
        ordPayFlowing.setPayNo(synchronizedOrderDTO.getOrderCode());
        ordPayFlowing.setPayWay(synchronizedOrderDTO.getPayWay());
        ordPayFlowing.setPayTime(synchronizedOrderDTO.getPayTime());
        ordPayFlowing.setPayAppId("95102728510398467");//运营平台2.0的APPID
        //ordPayFlowingMapper.insertSelective(ordPayFlowing);


        //填充流水关联表数据
        OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
        ordPayFlowingRef.setOrderId(orderId);
        ordPayFlowingRef.setPayId(payId);
        ordPayFlowingRef.setOrderPayId(idGenerator.getLong());
        //ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);

        OrdOrder orderExist = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(synchronizedOrderDTO.getOrderCode(), synchronizedOrderDTO.getChannel());
        //若已存在订单，则进行更新
        if (orderExist != null) {
            order.setOrderId(orderExist.getOrderId());
            order.setUpdateTime(new Date());
            order.setOrderCode(orderExist.getOrderCode());
            ordOrderMapper.updateByPrimaryKeySelective(order);

            OrdPayFlowingDTO payFlowing = new OrdPayFlowingDTO();
            payFlowing.setPayNo(synchronizedOrderDTO.getOrderCode());
            payFlowing.setAppId("95102728510398467");

            OrdPayFlowingDTO resultPayFlowing = payFlowingService.queryPayFlowingInfoByPayNo(payFlowing);
            if (resultPayFlowing != null) {
                ordPayFlowing.setPayId(resultPayFlowing.getPayId());
                ordPayFlowingMapper.updateByPrimaryKeySelective(ordPayFlowing);
            } else {
                ordPayFlowingMapper.insertSelective(ordPayFlowing);
                ordPayFlowingRef.setOrderId(orderExist.getOrderId());
                ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);
            }

        } else {
            ordOrderMapper.insertSelective(order);
            ordPayFlowingMapper.insertSelective(ordPayFlowing);
            ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);
        }


    }

    @Override
    public int updateOrderStatusTimeOut() {
        Date now = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        ca.add(Calendar.DATE, -2); // 减少2天，防止这次的当前时间比上一次的当前时间的秒晚了
        Date resultDate = ca.getTime();
        return ordOrderExtMapper.updateOrderStatusTimeOut(resultDate, now);
    }


    @Override
    public boolean terminateOrderStatus(TerminateOrderDTO terminateOrderDTO) {
        int i = ordOrderExtMapper.updateOrderStatusByOrderId(terminateOrderDTO.getOrderId());
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public PageQueryResult<OrdOrderListDTO> queryOrderByServiceProvider(PageQueryParam param) {
        String areaCode = param.getString("areaCode");

        // 界面没有选择具体商品时需要过滤所能看的商品权限
        if (param.getString("goodsId") == null || "".equals(param.getString("goodsId"))) {
            // 获取所拥有的类目权限
            QuerySelectedCategoryRequestDto categoryRequestDto = new QuerySelectedCategoryRequestDto();
            categoryRequestDto.setDepId(param.getString("depId"));
            categoryRequestDto.setUserId(param.getString("SESSION_USER_ID"));
            RestResponse<QuerySelectedCategoryResponseDto> categoryResponseDTO = goodsApi.querySelectedCategory(categoryRequestDto);
            if (categoryResponseDTO != null && categoryResponseDTO.getHead().getErrorCode() != null && "0".equals(categoryResponseDTO.getHead().getErrorCode())) {
                List<String> categorys = categoryResponseDTO.getBody().getCategorys();
                // 获取类目下所有商品id
                QueryGoodsByCategoryDTO categoryDTO = new QueryGoodsByCategoryDTO();
                categoryDTO.setCategorys(categorys);
                RestResponse<QueryGoodsByCategoryResponseDTO> goodsDTO = goodsApi.queryGoodsByCategory(categoryDTO);
                if (goodsDTO != null && goodsDTO.getHead().getErrorCode() != null && "0".equals(goodsDTO.getHead().getErrorCode())) {
                    param.put("goodsIdList", goodsDTO.getBody().getGoodsIds());

                    if (areaCode == null) {
                        return ordOrderExtMapper.queryOrderByServiceProvider(param);
                    } else {
                        areaCode = areaCode.trim();
                        if ("".equals(areaCode)) {
                            return ordOrderExtMapper.queryOrderByServiceProvider(param);
                        }
                        if ("00".equals(areaCode)) {
                            // 总节点，不用进行筛选
                            param.put("areaCode", null);
                            return ordOrderExtMapper.queryOrderByServiceProvider(param);
                        } else if ("0000".equals(areaCode.substring(2, 6))) {
                            // 省节点
                            param.put("isPre", "yes");
                            return ordOrderExtMapper.queryOrderByServiceProvider(param);
                        } else if ("00".equals(areaCode.substring(4, 6))) {
                            // 市节点
                            param.put("isCity", "yes");
                            return ordOrderExtMapper.queryOrderByServiceProvider(param);
                        } else {
                            // 县或区节点
                            param.put("isXian", "yes");
                        }
                        return ordOrderExtMapper.queryOrderByServiceProvider(param);
                    }

                } else {
                    throw new FtcspRuntimeException("00000002", (Object) "用户没有商品权限，商品权限");
                }
            } else {
                throw new FtcspRuntimeException("00000002", (Object) "用户没有商品权限，商品权限");
            }
        } else {
            if (areaCode == null) {
                return ordOrderExtMapper.queryOrderByServiceProvider(param);
            } else {
                areaCode = areaCode.trim();
                if ("".equals(areaCode)) {
                    return ordOrderExtMapper.queryOrderByServiceProvider(param);
                }
                if ("00".equals(areaCode)) {
                    // 总节点，不用进行筛选
                    param.put("areaCode", null);
                    return ordOrderExtMapper.queryOrderByServiceProvider(param);
                } else if ("0000".equals(areaCode.substring(2, 6))) {
                    // 省节点
                    param.put("isPre", "yes");
                    return ordOrderExtMapper.queryOrderByServiceProvider(param);
                } else if ("00".equals(areaCode.substring(4, 6))) {
                    // 市节点
                    param.put("isCity", "yes");
                    return ordOrderExtMapper.queryOrderByServiceProvider(param);
                } else {
                    // 县或区节点
                    param.put("isXian", "yes");
                }
                return ordOrderExtMapper.queryOrderByServiceProvider(param);
            }
        }


    }

    @Override
    public boolean updateOrderStatusByTask(UpdateOrderStatusByTaskDTO updateOrderStatusByTaskDTO) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(updateOrderStatusByTaskDTO.getOrderId());
        if (order != null) {
            // 只更新服务有效时间为空的订单
            if (order.getServiceEndDate() == null || "".equals(order.getServiceEndDate())) {
                ordOrderExtMapper.updateOrderStatusByTask(updateOrderStatusByTaskDTO.getOrderId());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据skuId，areaCode，cusId获取到商品是否配置了订单需要获取下单区域。
     * 没有配置的，将返回null的areaCode。
     * 需要配置的，如果参数里面areaCode有将直接返回，参数没有，将调用客户模块去获取客户的areaCode
     * @param goodsSkuId
     * @param areaCode
     * @param cusId
     * @return
     */
    public String changeOrderAreaBySkuId(Long goodsSkuId,String areaCode,String cusId){
        if (null == goodsSkuId){
            LOGGER.info("goodsSkuId为空，返回空的areaCode");
            return null;
        }
        //根据goodsSkuId获取是否需要下单配置区域，true为需要，false为免配
        RestResponse<QueryIsOrderAreaDeployBySkuId> querySkuInfoAttrBySkuId = goodsApi.queryIsOrderAreaDeployBySkuId(goodsSkuId);
        boolean result = querySkuInfoAttrBySkuId.getBody().getOrderAreaDeployBySkuId();
        //免配的，将返回null的areaCode
        if (!result){
            return null;
        }
        //需配置的，将根据areaCode是否为空，空需执行客户区域查询
        if (null!=areaCode && !areaCode.isEmpty()){
            return areaCode;
        }
        //根据客户id获取客户区域
        String cusAreaCode = queryCusAreaCodeByCusId(cusId);
        return cusAreaCode;
    }

    /**
     * 根据客户id获取客户区域
     * @param cusId
     * @return
     */
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
            public List<QueryOrderByCusIdResPonseDTO> queryHavingOrderByCusId(QueryOrderByCusIdDTO queryOrderByCusIdDTO) {
                //String[] cusIds = queryOrderByCusIdDTO.getCusIds().split(",");
                //List<String> cusIdList = Arrays.asList(cusIds);

                return ordOrderExtMapper.queryHavingOrderByCusId(queryOrderByCusIdDTO.getCusIds(), queryOrderByCusIdDTO.getChannel(), queryOrderByCusIdDTO.getGoodsId());
            }

    @Override
    public boolean openService(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：所传的orderId对应的订单不存在");
        }
        //判断订单状态是否为已中止
        if (order.getOrderStatus() != OrderConstant.ORDER_STATUS_STOP||
                order.getServiceStatus()!=OrderConstant.SERVICE_STATUS_STOP) {
            throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：只有订单状态和服务状态都为“已中止”的订单才能进行开通操作");
        }
        order.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING);
        order.setServiceStatus(OrderConstant.SERVICE_STATUS_SERVICING);//服务状态服务中
        int i = ordOrderMapper.updateByPrimaryKeySelective(order);
        if (i > 0) {
            return true;
        } else {
            throw new FtcspRuntimeException("09030034", (Object) "开通服务失败：所操作的订单orderId已被改变");
        }
    }

    @Override
    public boolean closeService(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "关闭服务失败：所传的orderId对应的订单不存在");
        }
        if(order.getOrderStatus()!=OrderConstant.ORDER_STATUS_SERVICING||
            order.getServiceStatus()!=OrderConstant.SERVICE_STATUS_SERVICING){throw new FtcspRuntimeException("09030034", (Object) "关闭服务失败：只有订单状态和服务状态都为“服务中”的订单才能进行关闭操作");
        }
        order.setOrderStatus(OrderConstant.ORDER_STATUS_STOP);//订单状态已中止
        order.setServiceStatus(OrderConstant.SERVICE_STATUS_STOP);//服务状态已中止
        int i = ordOrderMapper.updateByPrimaryKeySelective(order);
        if (i > 0) {
            return true;
        } else {
            throw new FtcspRuntimeException("09030034", (Object) "关闭服务失败：所操作的订单orderId已被改变");
        }
    }

    @Override
    public boolean terminateOrder(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：所传的orderId对应的订单不存在");
        }
        //订单状态和服务状态是否同为“服务中”
        boolean servicingFlag =(order.getOrderStatus()==OrderConstant.ORDER_STATUS_SERVICING&&
                       order.getServiceStatus()==OrderConstant.SERVICE_STATUS_SERVICING);
        //订单状态和服务状态是否同为“已中止”
        boolean stopFlag = (order.getOrderStatus()==OrderConstant.ORDER_STATUS_STOP&&
                order.getServiceStatus()==OrderConstant.SERVICE_STATUS_STOP);

        if(!(servicingFlag||stopFlag)){
            throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：只有订单状态和服务状态都为“服务中”或者“已中止”的订单才能进行终止操作");
        }
        order.setOrderStatus(OrderConstant.ORDER_STATUS_TERMINATION);//订单状态已终止
        order.setServiceStatus(OrderConstant.SERVICE_STATUS_TERMINATION);//服务状态已终止
        int i = ordOrderMapper.updateByPrimaryKeySelective(order);
        if (i > 0) {
            return true;
        } else {
            throw new FtcspRuntimeException("09030034", (Object) "终止订单失败：所操作的订单orderId已被改变");
        }
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        OrdOrder order = ordOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：所传的orderId对应的订单不存在");
        }
        if(!(order.getOrderStatus() == OrderConstant.ORDER_STATUS_PENDING_PAY &&
                order.getServiceStatus() == OrderConstant.SERVICE_STATUS_PREPARE_TO_START &&
                order.getPayStatus() == OrderConstant.PAY_STATUS_PREPARE_TO_PAY )){
            throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：只有订单状态和付款状态为“待付款”，服务状态为“未启动”的订单才能进行取消操作");
        }
        order.setOrderStatus(OrderConstant.ORDER_STATUS_CANCELED);//订单状态已取消
        order.setServiceStatus(OrderConstant.SERVICE_STATUS_CANCELED);//服务状态已取消
        order.setPayStatus(OrderConstant.PAY_STATUS_NOT_PAY);//付款状态不需要付款
        int i = ordOrderMapper.updateByPrimaryKeySelective(order);
        if (i > 0) {
            return true;
        } else {
            throw new FtcspRuntimeException("09030034", (Object) "取消订单失败：所操作的订单orderId已被改变");
        }
    }

    /**
     * 基础创单接口。
     * @param orderDTO
     * @return
     */
    @Override
    public RepCreateOrderDTO createBasisOrder(OrderDTO orderDTO) {
        //业务验证方法
        checkOrderDTOByBusiness(orderDTO);
        //根据商品信息组查询商品
        List<GoodsDTO> goodsDTOList = new ArrayList<>();
        orderDTO.getGoodsGroup().forEach(goodsDTO -> goodsDTOList.add(goodsInnerService.queryGoodsDTO(goodsDTO)));
        if (null==goodsDTOList||0>=goodsDTOList.size()){
            throw new FtcspRuntimeException("09030034", (Object) "查询商品组返回信息为空");
        }
        RepCreateOrderDTO repCreateOrderDTO = null;
        //根据商品是唯一还是多个，进行不同业务处理。（现在流程是唯一的）
        if (1==goodsDTOList.size()){
            OrdOrder ordOrder = new OrdOrder();
            repCreateOrderDTO = handleOrderByGoods(orderDTO,null,goodsDTOList.get(0),ordOrder);
        }else{
            throw new FtcspRuntimeException("09030034", (Object) "现在未有多个商品生成订单规则出现。");
        }
        return repCreateOrderDTO;
    }


    /**
     * 创建订单的业务验证方法
     * 1、验证商品组
     * 2、验证业务订单号
     * 3、客户查询与校验
     *
     * @param orderDTO
     */
    public void checkOrderDTOByBusiness(OrderDTO orderDTO) {
        //验证商品组是否存在
        if (null == orderDTO.getGoodsGroup() || 0 >= orderDTO.getGoodsGroup().size()) {
            throw new FtcspRuntimeException("09030034", (Object) "商品组不存在信息");
        }
        //验证业务订单号是否存在
        if (checkBusinessOrderNo(orderDTO.getBusinessOrderNo(),
                Integer.valueOf(orderDTO.getChannel()))) {
            throw new FtcspRuntimeException("09030034", (Object) "业务订单号已存在");
        }
        //客户类型查询与校验
        customerIdFromOrderOrPort(orderDTO);
    }

    /**
     * 根据业务订单号和来源，验证是否存在订单。
     * 存在将返回true
     *
     * @param businessOrderNo
     * @param channel
     * @return
     */
    public boolean checkBusinessOrderNo(String businessOrderNo, Integer channel) {
        boolean result = false;
        if (null == businessOrderNo || "".equals(businessOrderNo)) {
            return result;
        }
        OrdOrder ordOrder = this.ordOrderExtMapper.selectByBusinessOrderNoAndChannel(businessOrderNo, channel);
        if (ordOrder != null) {
            result = true;
        }
        return result;
    }

    /**
     * 客户类型查询与校验
     * 可通过为true；
     *
     * @param orderDTO
     */
    public void customerIdFromOrderOrPort(OrderDTO orderDTO) {
        if (StringUtils.isEmpty(orderDTO.getCusId())) {
            if (!StringUtils.isEmpty(orderDTO.getCusType())) {
                if (StringUtils.isEmpty(orderDTO.getCusName())) {
                    throw new FtcspRuntimeException("09030034", (Object) "客户类型，客户名称不能为空！");
                }
                if (StringUtils.isEmpty(orderDTO.getCusIdentificationNumber())) {
                    throw new FtcspRuntimeException("09030034", (Object) "客户类型，纳税人识别号/身份证号码不能为空！");
                }
            } else {
                throw new FtcspRuntimeException("09030034", (Object) "客户类型不能为空！");
            }
            Map<String, String> param = new HashMap<>();
            param.put("cusType", orderDTO.getCusType());
            param.put("cusName", orderDTO.getCusName());
            param.put("taxIdentificationNumber", orderDTO.getCusIdentificationNumber());
            param.put("inputChannel", orderDTO.getChannel());
            Map<String, Object> resultMap = this.generateCusId(param);
            if ("1".equals(resultMap.get("errorCode"))) {
                throw new FtcspRuntimeException("09030034", (Object) resultMap.get("errorMsg"));
            }
            orderDTO.setCusId((String) resultMap.get("customerId"));
        } else {
            if (!customerInnerService.queryCustomerIfExistByCusId(orderDTO.getCusId())){
                throw new FtcspRuntimeException("09030034", (Object) ("此cusId:"+orderDTO.getCusId()+"不存在!"));
            }
            //获取是否可以重复创建订单标识
            if ("2".equals(orderDTO.getNeedRepeat())) {
                //把组装的sku进行分解,商品sku进行查询获取skuId
                List<String> skuList = new ArrayList<>();
                orderDTO.getGoodsGroup().forEach(goodsDTO -> skuList.add(goodsDTO.getGoodsSku()));
                if (null == skuList || 0 >= skuList.size()) {
                    throw new FtcspRuntimeException("09030034", (Object) "商品sku为空");
                }
                //根据skusList查询出以sku为主键，skuId为值的map
                Map<String, Long> skuMap = goodsInnerService.querySkuDTOByGoodSku(skuList);
                List<Long> skuIdList = new ArrayList<>(skuMap.values());
                //不可以重复，需判断是否有存在的订单。有就不返回不创建。以客户id，渠道，商品sku来查询。
                if (ordOrderExtMapper.selectByCusIdAndSkuAndChannle(orderDTO.getCusId(), orderDTO.getChannel(), skuIdList) > 0) {
                    throw new FtcspRuntimeException("09030034", (Object) ("订单已经有重复信息，不能购买"));
                }
            }
        }
    }


    /**
     * 根据传入订单基础数据，父订单，商品，订单，进行业务组装。
     * 最后对订单类进行需要的业务数据组成。
     * @param orderDTO
     * @param parentOrdOrder
     * @param goodsDTO
     * @param ordOrder
     * @return
     */
    public RepCreateOrderDTO handleOrderByGoods(OrderDTO orderDTO,OrdOrder parentOrdOrder,GoodsDTO goodsDTO,OrdOrder ordOrder){
        //生成基础订单基本信息(基础不含商品属性进去的)
        objectAssemblyService.creadMainOrder(orderDTO,ordOrder);
        //父订单标识与子订单标识组装
        objectAssemblyService.assemblyParentAndSubToOrder(orderDTO,parentOrdOrder,ordOrder);
        //根据商品补充订单信息
        objectAssemblyService.supplementOrderByGoods(orderDTO,ordOrder,goodsDTO);
        // 需要开票设置开票信息
        OrdBillingInfo ordBillingInfo = objectAssemblyService.createAnInvoice(orderDTO);
        //开票信息id billingInfoId 为order设置开票信息id
        ordOrder.setBillingInfoId(ordBillingInfo.getBillingInfoId());
        // 需要配送设置配送信息
        OrdDelivery deliver = null;
        if ("0".equals(orderDTO.getDeliveryMode()) || "1".equals(orderDTO.getDeliveryMode())) {
            deliver = objectAssemblyService.createDelivery(orderDTO,ordOrder);
        }
        //流水设置
        OrdPayFlowing ordPayFlowing = new OrdPayFlowing();
        //流水订单关联表
        OrdPayFlowingRef ordPayFlowingRef = new OrdPayFlowingRef();
        //初始化流水对象和流水订单关联对象
        objectAssemblyService.initializationOrdPayFlowing(ordPayFlowing,ordPayFlowingRef,ordOrder);
        //根据实际业务进行数据插入。
        //插入订单
        ordOrderMapper.insertSelective(ordOrder);
        //插入开票信息
        ordBillingInfoMapper.insertSelective(ordBillingInfo);
        //插入流水
        ordPayFlowingMapper.insertSelective(ordPayFlowing);
        //插入流水订单关联
        ordPayFlowingRefMapper.insertSelective(ordPayFlowingRef);
        //插入配送
        if (null!=deliver){
            ordDelinveryMapper.insertSelective(deliver);
        }
        // 订单属性值处理
        if (goodsDTO.getGoodsAttributeGroup() != null && goodsDTO.getGoodsAttributeGroup().size() > 0) {
            for (GoodsAttributeDTO goodsAttribute : goodsDTO.getGoodsAttributeGroup()) {
                OrdOrderAttribute orderAttribute = new OrdOrderAttribute();
                orderAttribute.setOrderAttributeId(this.idGenerator.getLong());
                orderAttribute.setOrderId(ordOrder.getOrderId());
                orderAttribute.setAttributeId(Long.valueOf(goodsAttribute.getAttributeId()));
                orderAttribute.setAttributeValueId(Long.valueOf(goodsAttribute.getAttributeValueId()));
                orderAttribute.setAttributeValue(goodsAttribute.getAttributeValue());
                ordOrderAttributeMapper.insertSelective(orderAttribute);
            }
        }
        /**
         * 这三个等确定好流程再进行开发
         * //账单设置
         * //期数 periods
         * //商品编码 goodsCode
         */
        RepCreateOrderDTO repCreateOrderDTO = new RepCreateOrderDTO();
        repCreateOrderDTO.setOrderCode(ordOrder.getOrderCode());
        repCreateOrderDTO.setPayNo(ordPayFlowing.getPayNo());
        repCreateOrderDTO.setOrderTime(ordOrder.getOrderTime());
        return repCreateOrderDTO;
    }

    /**
     * 根据订单id更换订单状态,支付状态，服务状态，服务时间。
     * @param ordOrderStatusDTO
     * @param orderId
     * @return
     */
    @Override
    public int updateOrderSatusByOrdSatus(OrdOrderStatusDTO ordOrderStatusDTO, Long orderId){
        return ordOrderExtMapper.updateOrderSatusByOrdSatus(ordOrderStatusDTO,orderId);
    }

}
