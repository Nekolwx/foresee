package com.foresee.ftcsp.ordercenter.api.dto;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author foresee@foresee.com.cn
 * @date 2017年12月27日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class QueryOrderByCusIdDTO {

    private List<String> cusIds;

    @NotNull(message = "来源不能为空")
    private Integer channel;

    private Integer goodsId;

    private String goodsCode;

    

    
    public List<String> getCusIds() {
        return cusIds;
    }

    
    public void setCusIds(List<String> cusIds) {
        this.cusIds = cusIds;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
}
