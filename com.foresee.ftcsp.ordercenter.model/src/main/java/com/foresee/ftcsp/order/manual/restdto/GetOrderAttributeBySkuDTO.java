/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.io.Serializable;

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

public class GetOrderAttributeBySkuDTO implements Serializable {

    /**
     * serialVersionUID:TODO。
     */
    private static final long serialVersionUID = 1L;

    /**
     * 属性名称
     * 表 : t_pdt_attribute
     * 对应字段 : attribute_name
     */
    private String attributeName;
    
    /**
     * 属性值名称
     * 表 : t_pdt_attribute_value
     * 对应字段 : attribute_value_name
     */
    private String attributeValueName;

    /**
     * 属性值内容
     * 表 : t_pdt_attribute_value
     * 对应字段 : attribute_value_content
     */
    private String attributeValueContent;

    
    public String getAttributeName() {
        return attributeName;
    }

    
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    
    public String getAttributeValueName() {
        return attributeValueName;
    }

    
    public void setAttributeValueName(String attributeValueName) {
        this.attributeValueName = attributeValueName;
    }

    
    public String getAttributeValueContent() {
        return attributeValueContent;
    }

    
    public void setAttributeValueContent(String attributeValueContent) {
        this.attributeValueContent = attributeValueContent;
    }
    
    
}
