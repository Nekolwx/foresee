/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.ordercenter.api.dto;

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

public class DiscountItemDTO {

    /**
     * discountItemId:优惠条目id
     */
    private String discountItemId;

    /**
     * discountItemCode:优惠条目编码
     */
    private String discountItemCode;


    public String getDiscountItemId() {
        return discountItemId;
    }

    public void setDiscountItemId(String discountItemId) {
        this.discountItemId = discountItemId;
    }

    public String getDiscountItemCode() {
        return discountItemCode;
    }

    public void setDiscountItemCode(String discountItemCode) {
        this.discountItemCode = discountItemCode;
    }

}
