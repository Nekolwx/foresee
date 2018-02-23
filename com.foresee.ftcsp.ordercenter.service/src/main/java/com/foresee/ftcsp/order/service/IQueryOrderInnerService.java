package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.ordercenter.api.dto.UpdateTestGoodsOrderToEndDTO;

public interface IQueryOrderInnerService {
    /**
     * 根据渠道和时间，终止其相关联的试用商品的订单。
     * @param updateTestGoodsOrderToEndDTO
     * @return
     */
    Integer updateTestGoodsOrderToEnd(UpdateTestGoodsOrderToEndDTO updateTestGoodsOrderToEndDTO);
}
