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
 * @date 2017年8月23日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class GoodsAttributeDTO {
    
    /**
     * attributeId:属性id
     */
    private String attributeId;
    
    /**
     * attributeValueId:属性值id
     */
    private String attributeValueId;
    
    /**
     * attributeValue:属性值
     */
    private String attributeValue;

    
    public String getAttributeId() {
        return attributeId;
    }

    
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    
    public String getAttributeValueId() {
        return attributeValueId;
    }

    
    public void setAttributeValueId(String attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    
    public String getAttributeValue() {
        return attributeValue;
    }

    
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    
}
