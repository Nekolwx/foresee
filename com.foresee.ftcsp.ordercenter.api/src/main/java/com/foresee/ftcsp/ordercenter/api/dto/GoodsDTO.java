/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.ordercenter.api.dto;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author huangzhao@foresee.com.cn
 * @date 2017年8月18日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class GoodsDTO {

    /**
     * goodsSku:商品sku
     */
    @NotBlank(message = "参数[goodsSku]不能为空")
    private String                     goodsSku;

    /**
     * goodsQuantiy:商品数量
     */
    @NotBlank(message = "参数[goodsQuantiy]不能为空")
    private String                     goodsQuantiy;
    
    /**
     * skuId：skuId
     */
    private Long goodsSkuId;

    /**
     * goodsId:商品id
     */
    private Long                       goodsId;

    /**
     * productId:产品id
     */
    private Long                       productId;

    /**
     * goodsOriginalPrice:商品原价
     */
    private BigDecimal                 goodsOriginalPrice;

    /**
     * goodsDiscountPrice:商品折扣价
     */
    private BigDecimal                 goodsDiscountPrice;

    /**
     * goodsQuantity:商品数量
     */
    private Integer                    goodsQuantity;
    
    /**
     * 数量单位(1、次；2、台；3、件；4、套)
     * 表 : t_ord_order
     * 对应字段 : goods_quantity_unit
     */
    private Integer goodsQuantityUnit;

    /**
     * freight:运费
     */
    private BigDecimal                 freight;

    /**
     * 优惠金额
     */
    private BigDecimal                 discountAmount;
    
    /**
     * 1、即时开通;2、人工开通;3、付款开通
     * 表 : t_ord_order
     * 对应字段 : opening_mode
     */
    private Integer openingMode;
    
    /**
     * 服务方式 0 按时间服务 1按次数服务
     * 表 : t_ord_order
     * 对应字段 : service_mode
     */
    private Integer serviceMode;

    /**
     * 服务期限值
     * 表 : t_ord_order
     * 对应字段 : service_term_value
     */
    private Integer serviceTermValue;

    /**
     * 服务期限单位0 天 1 月 2 年
     * 表 : t_ord_order
     * 对应字段 : service_term_unit
     */
    private Integer serviceTermUnit;
    
   /**
    * 地区编码
    */
    private String areaCode;

    /**
     * 商品属性值
     */
    private List<GoodsAttributeDTO> goodsAttributeGroup;

    /**
     * 面议的自定义价格
     */
    private BigDecimal customPrice;

    /**
     * 是否下单区域配置
     */
    private Integer isOrderAreaDeploy;


    public String getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public String getGoodsQuantiy() {
        return goodsQuantiy;
    }

    public void setGoodsQuantiy(String goodsQuantiy) {
        this.goodsQuantiy = goodsQuantiy;
    }
    
    public Integer getGoodsQuantityUnit() {
        return goodsQuantityUnit;
    }
    
    public void setGoodsQuantityUnit(Integer goodsQuantityUnit) {
        this.goodsQuantityUnit = goodsQuantityUnit;
    }

    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    
    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getGoodsOriginalPrice() {
        return goodsOriginalPrice;
    }

    public void setGoodsOriginalPrice(BigDecimal goodsOriginalPrice) {
        this.goodsOriginalPrice = goodsOriginalPrice;
    }

    public BigDecimal getGoodsDiscountPrice() {
        return goodsDiscountPrice;
    }

    public void setGoodsDiscountPrice(BigDecimal goodsDiscountPrice) {
        this.goodsDiscountPrice = goodsDiscountPrice;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
   
    public Integer getOpeningMode() {
        return openingMode;
    }
    
    public void setOpeningMode(Integer openingMode) {
        this.openingMode = openingMode;
    }
    
    public Integer getServiceMode() {
        return serviceMode;
    }

    
    public void setServiceMode(Integer serviceMode) {
        this.serviceMode = serviceMode;
    }

    
    public Integer getServiceTermValue() {
        return serviceTermValue;
    }

    
    public void setServiceTermValue(Integer serviceTermValue) {
        this.serviceTermValue = serviceTermValue;
    }

    
    public Integer getServiceTermUnit() {
        return serviceTermUnit;
    }

    
    public void setServiceTermUnit(Integer serviceTermUnit) {
        this.serviceTermUnit = serviceTermUnit;
    }
    
    public String getAreaCode() {
        return areaCode;
    }

    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<GoodsAttributeDTO> getGoodsAttributeGroup() {
        return goodsAttributeGroup;
    }

    
    public void setGoodsAttributeGroup(List<GoodsAttributeDTO> goodsAttributeGroup) {
        this.goodsAttributeGroup = goodsAttributeGroup;
    }

    public BigDecimal getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(BigDecimal customPrice) {
        this.customPrice = customPrice;
    }

    public Integer getIsOrderAreaDeploy() {
        return isOrderAreaDeploy;
    }

    public void setIsOrderAreaDeploy(Integer isOrderAreaDeploy) {
        this.isOrderAreaDeploy = isOrderAreaDeploy;
    }
}
