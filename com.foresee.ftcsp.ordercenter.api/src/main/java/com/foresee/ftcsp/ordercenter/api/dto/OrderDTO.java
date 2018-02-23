/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.ordercenter.api.dto;

import java.util.List;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;

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

public class OrderDTO {

    /**
     * userId:用户Id
     */
    private String                userId;

    /**
     * cusId:客户Id
     */
    private String                cusId;

    /**
     * channel:渠道 0 平台 1网站 2金财代账
     */
    @NotBlank(message = "参数[channel]不能为空")
    private String                channel;

    /**
     * businessOrderNo:业务订单号
     */
    private String                businessOrderNo;

    /**
     * cusType:客户类型 0个人 1单位
     */
    private String                cusType;

    /**
     * cusName:客户名称
     */
    private String                cusName;

    /**
     * cusIdentificationNumber:纳税人识别号或身份证号码
     */
    private String                cusIdentificationNumber;

    /**
     * feedback:客户留言
     */
    private String                feedback;

    /**
     * dividePay:0-全额 1分期
     */
    @NotBlank(message = "参数[dividePay]不能为空")
    private String                dividePay;

    /**
     * needInvoice:是否需要发票0：需要1：不需要
     */
    @NotBlank(message = "参数[needInvoice]不能为空")
    private String                needInvoice;

    /**
     * buyerType:购买方类型0 个人 1 单位(开发票)
     */
    private String                buyerType;

    /**
     * buyerTaxpayerNo:购买方纳税人识别号(开发票)
     */
    private String                buyerTaxpayerNo;

    /**
     * buyerName:购买方名称(开发票)
     */
    private String                buyerName;

    /**
     * buyerAddressPhone:购买方地址、电话(开发票)
     */
    private String                buyerAddressPhone;

    /**
     * deliveryMode配送方式0-快递，1-上门自提 2不需要配送
     */
    private String                deliveryMode;

    /**
     * freight：运费
     */
    private String                freight;

    /**
     * receiver:收货人
     */
    private String                receiver;

    /**
     * receiverPhone:收货人电话
     */
    private String                receiverPhone;
    
    /**
     * contactNumber:联系电话
     */
    private String                contactNumber;

    /**
     * receiverAddress：收货人地址
     */
    private String                receiverAddress;

    /**
     * inviteAddress:自提地址
     */
    private String                inviteAddress;
    
    /**
     * orderTime:订购时间
     */
    private String                orderTime;
    
    /**
     * orderPerson:订购人
     */
    private String                orderPerson;
    
    /**
     * goodsName:商品名称
     */
    private String                goodsName;
    
    /**
     * goodsSku:商品Sku
     */
    private String                goodsSku;
    
    /**
     * goodsQuantiy:商品数量
     */
    private String                goodsQuantiy;
    
    /**
     * goodsGroup:商品信息
     */
    @Valid
    private List<GoodsDTO>        goodsGroup;

    /**
     * discountItemGroup:优惠条目信息
     */
    private List<DiscountItemDTO> discountItemGroup;

    /**
     * needRepeat:是否可重复创建订单：1可以，2不可以
     */
    private String needRepeat;
    
    /**
     * careAboutOrderStatus:重复创单是否关注订单状态 Y：是，N：否。
     */
    private String careAboutOrderStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusIdentificationNumber() {
        return cusIdentificationNumber;
    }

    public void setCusIdentificationNumber(String cusIdentificationNumber) {
        this.cusIdentificationNumber = cusIdentificationNumber;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDividePay() {
        return dividePay;
    }

    public void setDividePay(String dividePay) {
        this.dividePay = dividePay;
    }

    public String getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(String needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public String getBuyerTaxpayerNo() {
        return buyerTaxpayerNo;
    }

    public void setBuyerTaxpayerNo(String buyerTaxpayerNo) {
        this.buyerTaxpayerNo = buyerTaxpayerNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddressPhone() {
        return buyerAddressPhone;
    }

    public void setBuyerAddressPhone(String buyerAddressPhone) {
        this.buyerAddressPhone = buyerAddressPhone;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getInviteAddress() {
        return inviteAddress;
    }

    public void setInviteAddress(String inviteAddress) {
        this.inviteAddress = inviteAddress;
    }

    public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderPerson () {
		return orderPerson;
	}

	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}

	public String getGoodsQuantiy() {
		return goodsQuantiy;
	}

	public void setGoodsQuantiy(String goodsQuantiy) {
		this.goodsQuantiy = goodsQuantiy;
	}

	public List<GoodsDTO> getGoodsGroup() {
        return goodsGroup;
    }

    public void setGoodsGroup(List<GoodsDTO> goodsGroup) {
        this.goodsGroup = goodsGroup;
    }

    public List<DiscountItemDTO> getDiscountItemGroup() {
        return discountItemGroup;
    }

    public void setDiscountItemGroup(List<DiscountItemDTO> discountItemGroup) {
        this.discountItemGroup = discountItemGroup;
    }

    public String getNeedRepeat() {
        return needRepeat;
    }

    public void setNeedRepeat(String needRepeat) {
        this.needRepeat = needRepeat;
    }

    public String getCareAboutOrderStatus() {
        return careAboutOrderStatus;
    }

    public void setCareAboutOrderStatus(String careAboutOrderStatus) {
        this.careAboutOrderStatus = careAboutOrderStatus;
    }

}
