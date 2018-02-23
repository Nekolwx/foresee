/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author Foresee@foresee.com.cn
 * @date 2017年8月26日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class GetOrderPhotoBySkuDTO {

    /**
     * 图片URL
     * 表 : t_pdt_photo_album
     * 对应字段 : photo_url
     */
    private String photoUrl;
    
    /**
     * 排序号
     * 表 : t_pdt_photo_album
     * 对应字段 : sort_number
     */
    private Integer sortNumber;
    
    /**
     * 显示端类型（1、为PC；2、为移动端）
     * 表 : t_pdt_photo_album
     * 对应字段 : display_type
     */
    private Integer displayType;

    
    public String getPhotoUrl() {
        return photoUrl;
    }

    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    
    public Integer getSortNumber() {
        return sortNumber;
    }

    
    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    
    public Integer getDisplayType() {
        return displayType;
    }

    
    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }
    
    
}
