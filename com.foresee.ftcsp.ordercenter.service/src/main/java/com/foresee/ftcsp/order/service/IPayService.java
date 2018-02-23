/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service;

import java.util.Map;
import com.foresee.ftcsp.order.manual.dto.request.MobilePayEntranceDTO;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamExtDTO;
import com.foresee.ftcsp.order.manual.restdto.*;

/**
 * <pre>
 * 支付接口。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月17日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public interface IPayService {

    /**
     * 扫码支付。
     * @param payDTO
     * @return Map<String,Object>
     */
    public Map<String, Object> payByScanCode(PayDTO payDTO);
    
    /**
     * 公众号支付。
     * @param payParamDTO
     * @return Map<String,Object>
     */
    public Map<String, Object> payByPublicNumber(GetPayParamDTO payParamDTO);
    
    /**
     * 打赏支付。
     * @param rewardPayDTO
     * @return Map<String,Object>
     */
    public Map<String,Object> rewardPay(RewardPayDTO rewardPayDTO);
    
    /**
     * 接收支付回调。
     * @param receivePayCallBackDTO
     * @return Map<String,Object>
     */
    public Map<String, Object> payCallBack(ReceivePayCallBackDTO receivePayCallBackDTO);
    
    /**
     * 移动端支付。
     * @param payParamDTO
     * @return Map<String,Object>
     */
    public Map<String,Object> payByMobileTerminal(GetPayParamDTO payParamDTO);
    
    /**
     * 移动端支付(扩展)。
     * @param GetPayParamExtDTO
     * @return Map<String,Object>
     */
    public Map<String,Object> payByMobileTerminalExt(GetPayParamExtDTO payParamExtDTO);

    /**
     * H5手机页面支付。
     * @param payParamDTO
     * @return Map<String,Object>
     */
    public Map<String,Object> payByHtml(GetPayParamDTO payParamDTO);
    
    /**
     * 打赏支付页面初始化。
     * @param rewardPayInitDTO
     * @return Map<String,Object>
     */
    public Map<String,Object> rewardPayInit(RewardPayInitDTO rewardPayInitDTO);

    /**
     * 统一移动端支付接入接口。
     * @param mobilePayEntranceDTO
     * @return String payPageUrl支付页面地址
     */
    public String mobilePayEntrance(MobilePayEntranceDTO mobilePayEntranceDTO);

    /**
     * 手机端页面支付初始化信息
     * @param mobilePaymentInitDTO
     * @return
     */
    Map<String,Object> mobilePaymentInit(MobilePaymentInitDTO mobilePaymentInitDTO);

    /**
     * 手机端支付确认接口
     * @param mobilePaymentDTO
     * @return
     */
    Map<String,Object> mobilePayment(MobilePaymentDTO mobilePaymentDTO);
}
