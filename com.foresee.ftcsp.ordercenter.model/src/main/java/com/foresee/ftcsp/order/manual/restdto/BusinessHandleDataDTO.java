/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.manual.restdto;

/**
 * <pre>
 * 业务办理后要报文写入mq，mq服务根据报文信息调用悦财税接口入参DTO。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年12月15日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class BusinessHandleDataDTO {
    
    /**
     * dljgid:代理机构ID,指客户ID。
     * 
     * 
     */
    private String dljgId;
    
    /**
     * userId:登录用户ID(可为空)。
     */
    private String userId;
    
    /**
     * goodsCode:商品编码。
     */
    private String goodsCode;
    
    /**
     * serviceState：服务状态 0 可用服务 1 不能用服务。
     */
    private String serviceState;

    


    
    
    public String getDljgId() {
        return dljgId;
    }


    
    public void setDljgId(String dljgId) {
        this.dljgId = dljgId;
    }


    public String getUserId() {
        return userId;
    }

    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    public String getGoodsCode() {
        return goodsCode;
    }

    
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }



    
    public String getServiceState() {
        return serviceState;
    }



    
    public void setServiceState(String serviceState) {
        this.serviceState = serviceState;
    }

    

    
    

}
