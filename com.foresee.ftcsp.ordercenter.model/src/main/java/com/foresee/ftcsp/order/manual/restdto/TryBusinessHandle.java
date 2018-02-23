package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class TryBusinessHandle {
    /**
     * 客户ID
     */
    @NotNull
    @NotBlank(message = "参数客户ID不能为空")
    private String cusId;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    /**
     * 产品ID
     */
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
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
}
