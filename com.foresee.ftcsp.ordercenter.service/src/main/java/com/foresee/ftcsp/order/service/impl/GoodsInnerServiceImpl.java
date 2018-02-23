package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.Loggers;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.manual.dto.PdtGoodsSkuExtDTO;
import com.foresee.ftcsp.goods.manual.dto.QueryGoodsBySkuToOrderDTO;
import com.foresee.ftcsp.goods.manual.restdto.GoodsIdAndSkuIdDTO;
import com.foresee.ftcsp.goods.manual.restdto.ListGoodsSku;
import com.foresee.ftcsp.goods.manual.restdto.QueryGoodInfoBySkuDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.GoodsCodeListDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.ListSkuDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryBriefGoodsForOrder;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.service.IGoodsInnerService;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 内部请求goods接口处理实现类。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @version 1.00.00
 * <p>
 * <pre>
 *     修改记录
 *     修改后版本:     修改人：  修改日期:     修改内容:
 *   </pre>
 * @date 2018年01月12日
 */
@Service
public class GoodsInnerServiceImpl implements IGoodsInnerService {

    private Logger logger = Loggers.make();

    @Resource
    private GoodsApi goodsApi;

    /**
     * 根据goodsId或者goodsSkuId来查询商品开通方式或者价格
     * goodsId只返回开通方式
     * goodsSkuId返回开通方式和价格（小于0是面议）
     * @param goodsId
     * @param goodsSkuId
     * @return
     */
    @Override
    public QueryBriefGoodsForOrder queryBriefGoods(Long goodsId, Long goodsSkuId){
        GoodsIdAndSkuIdDTO goodsIdAndSkuIdDTO = new GoodsIdAndSkuIdDTO();
        goodsIdAndSkuIdDTO.setGoodsSkuId(goodsSkuId);
        goodsIdAndSkuIdDTO.setGoodsId(goodsId);
        RestResponse<QueryBriefGoodsForOrder> queryBriefGoodsForOrderRestResponse = goodsApi.queryBriefGoods(goodsIdAndSkuIdDTO);
        if (!queryBriefGoodsForOrderRestResponse.isSuccess()){
            logger.error(queryBriefGoodsForOrderRestResponse.getHead().getErrorMsg());
            throw new FtcspRuntimeException("09030034", (Object) queryBriefGoodsForOrderRestResponse.getHead().getErrorMsg());
        }
        return queryBriefGoodsForOrderRestResponse.getBody();
    }

    /**
     * 根据sku查询商品信息接口
     * --后期将根据需求修改此接口，或者新增一个价格等附属查询。
     * @param sourceGoodsDTO
     * @return
     */
    @Override
    public GoodsDTO queryGoodsDTO(GoodsDTO sourceGoodsDTO) {
        QueryGoodInfoBySkuDTO queryGoods = new QueryGoodInfoBySkuDTO();
        if(0>=Integer.valueOf(sourceGoodsDTO.getGoodsQuantiy())){
            throw new FtcspRuntimeException("09030034", (Object) ("sku:"+sourceGoodsDTO.getGoodsSku()+"商品没有数量！"));
        }
        queryGoods.setGoodsSku(sourceGoodsDTO.getGoodsSku());
        RestResponse<QueryGoodsBySkuToOrderDTO> restResponse = this.goodsApi.queryGoodInfoBySku(queryGoods);
        if (restResponse != null && restResponse.getBody() != null) {
            if (OrderConstant.TWO!=restResponse.getBody().getGoodsStatus()){
                throw new FtcspRuntimeException("09030034", (Object) ("sku:"+sourceGoodsDTO.getGoodsSku()+"的商品没有上架，不能下单！"));
            }
            GoodsDTO goodsDTO = new GoodsDTO();
            goodsDTO.setGoodsOriginalPrice(restResponse.getBody().getPrice());
            goodsDTO.setDiscountAmount(new BigDecimal(0));
            goodsDTO.setGoodsDiscountPrice(restResponse.getBody().getPrice());
            goodsDTO.setFreight(new BigDecimal(0));// 运费默认0
            goodsDTO.setGoodsSkuId(restResponse.getBody().getGoodsSkuId());
            goodsDTO.setGoodsId(restResponse.getBody().getGoodsId());
            goodsDTO.setProductId(restResponse.getBody().getProductId());
            goodsDTO.setGoodsQuantityUnit(restResponse.getBody().getProductUnit());
            goodsDTO.setAreaCode(sourceGoodsDTO.getAreaCode());
            goodsDTO.setIsOrderAreaDeploy(restResponse.getBody().getIsOrderAreaDeploy());
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
            goodsDTO.setGoodsQuantity(Integer.valueOf(sourceGoodsDTO.getGoodsQuantiy()));
            goodsDTO.setCustomPrice(sourceGoodsDTO.getCustomPrice());
            //将接口传来的商品属性保存
            goodsDTO.setGoodsAttributeGroup(sourceGoodsDTO.getGoodsAttributeGroup());
            return goodsDTO;
        } else {
            logger.error("根据sku:"+sourceGoodsDTO.getGoodsSku()+"查询商品信息异常" + restResponse.getHead().getErrorMsg());
            // 调用商品接口出错返回
            throw new FtcspRuntimeException("09030034", (Object) ("根据sku:"+sourceGoodsDTO.getGoodsSku()+"查询商品信息异常" + restResponse.getHead().getErrorMsg()));
        }
    }

    /**
     * 根据skusList查询出以sku为主键，skuId为值的map
     *
     * @param skus
     * @return
     */
    @Override
    public Map<String, Long> querySkuDTOByGoodSku(List<String> skus) {
        //验证是否存为空
        if (null == skus || 0 >= skus.size()) {
            throw new FtcspRuntimeException("09030034", (Object) "查询SKUId传入的sku数组为空");
        }
        Map<String, Long> skuIdMap = new HashMap<>();
        ListGoodsSku listGoodsSku = new ListGoodsSku();
        listGoodsSku.setGoodsSkus(skus);
        RestResponse<ListSkuDTO> listSkuDTORestResponse = goodsApi.querySkuDTOByGoodSku(listGoodsSku);
        if (listSkuDTORestResponse.isSuccess()) {
            List<PdtGoodsSkuExtDTO> pdtGoodsSkuExtDTOList = listSkuDTORestResponse.getBody().getPdtGoodsSkuExtDTOList();
            pdtGoodsSkuExtDTOList.forEach(pdtGoodsSkuExtDTO -> skuIdMap.put(pdtGoodsSkuExtDTO.getGoodsSku(), pdtGoodsSkuExtDTO.getGoodsSkuId()));
        } else {
            throw new FtcspRuntimeException("09030034", (Object) listSkuDTORestResponse.getHead().getErrorMsg());
        }
        skus.forEach(sku -> {
            if (null == skuIdMap.get(sku))
                throw new FtcspRuntimeException("09030034", (Object) ("sku:" + sku + "的skuId查询不到"));
        });
        return skuIdMap;
    }

    /**
     * 根据TagCode查询相关的goodsCode
     * @param tagCode
     * @return
     */
    @Override
    public List<String> queryGoodsCodeByTagCode(String tagCode){
        RestResponse<GoodsCodeListDTO> querySkuPriceListByGoodIdDTORestResponse = goodsApi.queryGoodsCodeByTagCode(tagCode);
        if (querySkuPriceListByGoodIdDTORestResponse.isSuccess()){
            List<String> goodsCodeListDTO = querySkuPriceListByGoodIdDTORestResponse.getBody().getGoodsCodes();
            if (null==goodsCodeListDTO|| OrderConstant.ZERO>=goodsCodeListDTO.size()){
                throw new FtcspRuntimeException("09030034", (Object) ("tagCode:" + tagCode + "的goodsCode查询不到"));
            }
            return goodsCodeListDTO;
        }else {
            logger.error("queryGoodsCodeByTagCode--tagCode:"+tagCode+",errorMsg"+querySkuPriceListByGoodIdDTORestResponse.getHead().getErrorMsg());
            throw new FtcspRuntimeException("09030034", (Object) ("errorMsg:" + querySkuPriceListByGoodIdDTORestResponse.getHead().getErrorMsg()));
        }
    }
}
