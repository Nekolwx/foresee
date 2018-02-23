/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author linliangwei@foresee.com.cn
 * @date 2017年8月20日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class GetOrederGoodSkuDTO implements Serializable {

    /**
     * 商品/套餐SKU
     * 表 : t_pdt_goods_sku
     * 对应字段 : goods_sku
     */
    private Long goodsSkuId;
    
    /**
     * 商品/套餐名称
     * 表 : t_pdt_goods
     * 对应字段 : goods_name
     */
    private String goodsName;
    
    /**
     * 价格
     * 表 : t_pdt_goods_sku
     * 对应字段 : price
     */
    private BigDecimal price;
    
    /**
     * 产品/产品包单位(1、次；2、台；3、件；4、套)
     * 表 : t_pdt_product
     * 对应字段 : product_unit
     */
    private Integer unit;
    
    /**
     * 服务开始日期
     * 表 : t_ord_order
     * 对应字段 : service_start_date
     */
    private Date serviceStartDate;

    /**
     * 服务结束日期
     * 表 : t_ord_order
     * 对应字段 : service_end_date
     */
    private Date serviceEndDate;
    
    private List<GetOrderAttributeBySkuDTO> attributeGroup;
    
    
    
    /**
     * 商品数量
     */
    private Integer quantity;
    
    /**
     * 商品原价
     */
    private BigDecimal originalPrice;
    
    /**
     * 商品折扣价
     */
    private BigDecimal discountPrice;
    
    /**
     * 区域code
     */
    private String areaCode;
   
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 区域名称
     */
    private String areaName;
    
    /**
     * 组合串名称
     * 表 : t_pdt_goods_sku
     * 对应字段 : group_bunch_code_name
     */
    private String groupBunchCodeName;
    
    private List<GetOrderPhotoBySkuDTO> photoGroup;
    
    /**
     * serialVersionUID:TODO。
     */
    private static final long serialVersionUID = 1L;

    
    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<GetOrderAttributeBySkuDTO> getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(List<GetOrderAttributeBySkuDTO> attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    public String getGroupBunchCodeName() {
        return groupBunchCodeName;
    }

    public void setGroupBunchCodeName(String groupBunchCodeName) {
        this.groupBunchCodeName = groupBunchCodeName;
    }

    public List<GetOrderPhotoBySkuDTO> getPhotoGroup() {
        return photoGroup;
    }

    public void setPhotoGroup(List<GetOrderPhotoBySkuDTO> photoGroup) {
        this.photoGroup = photoGroup;
    }
    
}
