/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.manual.dto.request;

/**
 * <pre>
 * 支付接口请求入参DTO。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月25日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class PayRequestDTO {

    /**
     * 接入应用ID。
     */
    String pay_app_id;

    /**
     * 支付渠道ID。
     */
    String pay_channel_id;

    /**
     * 支付渠道账号ID。
     */
    String pay_account_id;

    /**
     * 微信用户在商户对应appid下的唯一标识，支付渠道为公众号支付，此参数必传
     */
    String pay_open_id;

    /**
     * 当前时间时间戳
     */
    String pay_timestamp;

    /**
     * APP和网页支付提交用户端ip，支付渠道为公众号支付，此参数必传
     */
    String pay_client_ip;

    /**
     * 同步回调跳转URL页面，即支付成功后需要跳转的页面URL，且不能跟任何参数
     */
    String pay_ret_url;

    /**
     * 异步通知回调URL接口，支付成功后支付平台会回调通知到此URL接口，且不能跟任何参数。
     */
    String pay_notify_url;

    /**
     * 业务订单ID，在业务系统中需保证唯一。
     */
    String pay_biz_order_id;

    /**
     * 支付金额，单位：元。保留两位小数。
     */
    String pay_amount;

    /**
     * 业务订单创建时间，时间戳。
     */
    String pay_order_time;

    /**
     * 支付有效时间，时间戳。
     */
    String pay_invalid_time;

    /**
     * 商品标题/交易标题/订单标题。
     */
    String pay_subject;

    /**
     * 订单商品描述。
     */
    String pay_body;

    /**
     * 商品或者订单展示URL页面。
     */
    String pay_show_url;

    /**
     * API版本，如：1.0。
     */
    String pay_api_version;

    /**
     * 参数签名。
     */
    String pay_sign;

    
    public String getPay_app_id() {
        return pay_app_id;
    }

    
    public void setPay_app_id(String pay_app_id) {
        this.pay_app_id = pay_app_id;
    }

    
    public String getPay_channel_id() {
        return pay_channel_id;
    }

    
    public void setPay_channel_id(String pay_channel_id) {
        this.pay_channel_id = pay_channel_id;
    }

    
    public String getPay_account_id() {
        return pay_account_id;
    }

    
    public void setPay_account_id(String pay_account_id) {
        this.pay_account_id = pay_account_id;
    }

    
    public String getPay_open_id() {
        return pay_open_id;
    }

    
    public void setPay_open_id(String pay_open_id) {
        this.pay_open_id = pay_open_id;
    }

    
    public String getPay_timestamp() {
        return pay_timestamp;
    }

    
    public void setPay_timestamp(String pay_timestamp) {
        this.pay_timestamp = pay_timestamp;
    }

    
    public String getPay_client_ip() {
        return pay_client_ip;
    }

    
    public void setPay_client_ip(String pay_client_ip) {
        this.pay_client_ip = pay_client_ip;
    }

    
    public String getPay_ret_url() {
        return pay_ret_url;
    }

    
    public void setPay_ret_url(String pay_ret_url) {
        this.pay_ret_url = pay_ret_url;
    }

    
    public String getPay_notify_url() {
        return pay_notify_url;
    }

    
    public void setPay_notify_url(String pay_notify_url) {
        this.pay_notify_url = pay_notify_url;
    }

    
    public String getPay_biz_order_id() {
        return pay_biz_order_id;
    }

    
    public void setPay_biz_order_id(String pay_biz_order_id) {
        this.pay_biz_order_id = pay_biz_order_id;
    }

    
    public String getPay_amount() {
        return pay_amount;
    }

    
    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    
    public String getPay_order_time() {
        return pay_order_time;
    }

    
    public void setPay_order_time(String pay_order_time) {
        this.pay_order_time = pay_order_time;
    }

    
    public String getPay_invalid_time() {
        return pay_invalid_time;
    }

    
    public void setPay_invalid_time(String pay_invalid_time) {
        this.pay_invalid_time = pay_invalid_time;
    }

    
    public String getPay_subject() {
        return pay_subject;
    }

    
    public void setPay_subject(String pay_subject) {
        this.pay_subject = pay_subject;
    }

    
    public String getPay_body() {
        return pay_body;
    }

    
    public void setPay_body(String pay_body) {
        this.pay_body = pay_body;
    }

    
    public String getPay_show_url() {
        return pay_show_url;
    }

    
    public void setPay_show_url(String pay_show_url) {
        this.pay_show_url = pay_show_url;
    }

    
    public String getPay_api_version() {
        return pay_api_version;
    }

    
    public void setPay_api_version(String pay_api_version) {
        this.pay_api_version = pay_api_version;
    }

    
    public String getPay_sign() {
        return pay_sign;
    }

    
    public void setPay_sign(String pay_sign) {
        this.pay_sign = pay_sign;
    }
    
    

}
