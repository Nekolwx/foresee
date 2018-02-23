package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.goods.manual.restdto.response.GoodsCodeListDTO;
import com.foresee.ftcsp.goods.manual.restdto.response.QueryBriefGoodsForOrder;
import com.foresee.ftcsp.ordercenter.api.dto.GoodsDTO;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 内部请求goods接口。
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
public interface IGoodsInnerService {

    /**
     * 根据goodsId或者goodsSkuId来查询商品开通方式或者价格
     * goodsId只返回开通方式
     * goodsSkuId返回开通方式和价格（小于0是面议）
     * @param goodsId
     * @param goodsSkuId
     * @return
     */
    QueryBriefGoodsForOrder queryBriefGoods(Long goodsId, Long goodsSkuId);

    /**
     * 根据sku查询商品信息接口
     * --后期将根据需求修改此接口，或者新增一个价格等附属查询。
     * @param sourceGoodsDTO
     * @return
     */
    GoodsDTO queryGoodsDTO(GoodsDTO sourceGoodsDTO);

    /**
     * 根据skusList查询出以sku为主键，skuId为值的map
     *
     * @param skus
     * @return
     */
    Map<String, Long> querySkuDTOByGoodSku(List<String> skus);

    /**
     * 根据TagCode查询相关的goodsCode
     * @param tagCode
     * @return
     */
    List<String> queryGoodsCodeByTagCode(String tagCode);
}
