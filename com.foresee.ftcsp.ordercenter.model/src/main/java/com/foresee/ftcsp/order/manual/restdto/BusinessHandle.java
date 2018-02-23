package com.foresee.ftcsp.order.manual.restdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class BusinessHandle {

    /**
     * 客户ID
     */
    @NotNull
    @NotBlank(message = "参数客户ID不能为空")
    private String cusId;

    /**
     * 传入合同信息
     */
    @Valid
    @NotNull(message = "合同信息不能为空")
    private OrdOrderContractRest ordOrderContractRest;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    /**
     * 产品ID
     */
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    /**
     * 传入首次充值信息
     */
    @Valid
    @NotNull(message = "首次充值信息不能为空")
    private RechargeInformation rechargeInformation;

    /**
     * 开票信息
     */
    @Valid
    private OrdBillingInfoRest ordBillingInfoRest;


    public class OrdOrderContractRest {
        /**
         * 合同ID
         * 表 : t_ord_order_contract
         * 对应字段 : contract_id
         */
        @NotNull
        @NotBlank(message = "参数[contractId]不能为空")
        private String contractId;

        /**
         * 合同编码
         * 表 : t_ord_order_contract
         * 对应字段 : contract_code
         */
        @NotNull
        @NotBlank(message = "参数[contractCode]不能为空")
        private String contractCode;

        /**
         * 合同签订日期
         * 表 : t_ord_order_contract
         * 对应字段 : contract_sign_time
         */
        @NotNull(message = "合同签订日期不能为空")
        @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
        private Date contractSignTime;

        /**
         * 拥有者ID 如订单ID
         * 表 : t_ord_order_contract
         * 对应字段 : owner_id
         */
        private Long ownerId;

        /**
         * 拥有者类型 1-订单
         * 表 : t_ord_order_contract
         * 对应字段 : owner_type
         */
        private Integer ownerType;

        /**
         * 首期预付费
         * 表 : t_ord_order_contract
         * 对应字段 : down_payment
         */
        private BigDecimal downPayment;

        /**
         * 平台服务费
         * 表 : t_ord_order_contract
         * 对应字段 : platform_service_payment
         */
        private BigDecimal platformServicePayment;

        /**
         * 服务开始日期
         * 表 : t_ord_order_contract
         * 对应字段 : contract_service_start_time
         */
        @NotNull(message = "服务开始日期不能为空")
        @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
        private Date contractServiceStartTime;

        /**
         * 服务结束日期
         * 表 : t_ord_order_contract
         * 对应字段 : contract_service_end_time
         */
        private Date contractServiceEndTime;

        /**
         * 计费开始日期
         * 表 : t_ord_order_contract
         * 对应字段 : contract_charging_start_time
         */
        @NotNull(message = "计费开始日期不能为空")
        @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
        private Date contractChargingStartTime;

        /**
         * 计费结束日期
         * 表 : t_ord_order_contract
         * 对应字段 : contract_charging_end_time
         */
        @NotNull(message = "计费结束日期不能为空")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date contractChargingEndTime;

        /**
         * 备注
         * 表 : t_ord_order_contract
         * 对应字段 : remark
         */
        private String remark;

        /**
         * 乙方
         * 表 : t_ord_order_contract
         * 对应字段 : part_b
         */
        @NotBlank(message = "合同乙方不能为空")
        private String partB;

        /**
         * 甲方
         * 表 : t_ord_order_contract
         * 对应字段 : part_a
         */
        @NotBlank(message = "合同甲方不能为空")
        private String partA;

        /**
         * 丙方
         * 表 : t_ord_order_contract
         * 对应字段 : part_c
         */
        //@NotBlank(message = "合同丙方不能为空")
        private String partC;



        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public Date getContractSignTime() {
            return contractSignTime;
        }

        public void setContractSignTime(Date contractSignTime) {
            this.contractSignTime = contractSignTime;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public Integer getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(Integer ownerType) {
            this.ownerType = ownerType;
        }

        public BigDecimal getDownPayment() {
            return downPayment;
        }

        public void setDownPayment(BigDecimal downPayment) {
            this.downPayment = downPayment;
        }

        public BigDecimal getPlatformServicePayment() {
            return platformServicePayment;
        }

        public void setPlatformServicePayment(BigDecimal platformServicePayment) {
            this.platformServicePayment = platformServicePayment;
        }

        public Date getContractServiceStartTime() {
            return contractServiceStartTime;
        }

        public void setContractServiceStartTime(Date contractServiceStartTime) {
            this.contractServiceStartTime = contractServiceStartTime;
        }

        public Date getContractServiceEndTime() {
            return contractServiceEndTime;
        }

        public void setContractServiceEndTime(Date contractServiceEndTime) {
            this.contractServiceEndTime = contractServiceEndTime;
        }

        public Date getContractChargingStartTime() {
            return contractChargingStartTime;
        }

        public void setContractChargingStartTime(Date contractChargingStartTime) {
            this.contractChargingStartTime = contractChargingStartTime;
        }

        public Date getContractChargingEndTime() {
            return contractChargingEndTime;
        }

        public void setContractChargingEndTime(Date contractChargingEndTime) {
            this.contractChargingEndTime = contractChargingEndTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPartB() {
            return partB;
        }

        public void setPartB(String partB) {
            this.partB = partB;
        }

        public String getPartA() {
            return partA;
        }

        public void setPartA(String partA) {
            this.partA = partA;
        }

        public String getPartC() {
            return partC;
        }

        public void setPartC(String partC) {
            this.partC = partC;
        }
    }

    public class RechargeInformation {
        /**
         * ownerId:账号拥有者ID 用户ID或企业ID。
         */
        private String ownerId;

        /**
         * ownerType:拥有者类型：1个人 2 企业。
         */
        private String ownerType;

        /**
         * currency:币种:1财币，2人民币。
         */
        private String currency;

        /**
         * outTradeNo:外部交易号：可用UUID，由调用方生成，64个字符以内，需保证调用端不重复（平台订单的流水）。
         */
        private String outTradeNo;

        /**
         * outPayNo:外部支付流水号（页面上的流水）。
         */
        @NotBlank(message = "交易流水不能为空")
        private String outPayNo;

        /**
         * rechargeWay:充值方式：1银联，2支付宝，3微信。
         */
        @NotBlank(message = "充值方式不能为空")
        private String rechargeWay;

        /**
         * certificate:充值凭证。
         */
        @NotBlank(message = "充值凭证不能为空")
        private String certificate;

        /**
         * subject:主题，填写充值原因。
         */
        private String subject;

        /**
         * amount:充值金额。
         */
        @NotBlank(message = "充值金额不能为空")
        private String amount;

        /**
         * givenAmount:充值金额
         */
        @NotBlank(message = "赠送金额不能为空")
        private  String givenAmount;

        /**
         * partyAccountNo:对方账号。
         */
        private String partyAccountNo;

        /**
         * partyAccountName:对方账号名称。
         */
        private String partyAccountName;

        /**
         * remark:备注。
         */
        private String remark;

        /**
         * rechargeType:1线上充值，2线下充值
         */
        private String rechargeType;

        /**
         * rechargeDate:充值时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date rechargeDate;

        @NotBlank(message = "收款方不能为空")
        private String payeeAccountNo;

        public String getPayeeAccountNo() {
            return payeeAccountNo;
        }

        public void setPayeeAccountNo(String payeeAccountNo) {
            this.payeeAccountNo = payeeAccountNo;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(String ownerType) {
            this.ownerType = ownerType;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getOutPayNo() {
            return outPayNo;
        }

        public void setOutPayNo(String outPayNo) {
            this.outPayNo = outPayNo;
        }

        public String getRechargeWay() {
            return rechargeWay;
        }

        public void setRechargeWay(String rechargeWay) {
            this.rechargeWay = rechargeWay;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPartyAccountNo() {
            return partyAccountNo;
        }

        public void setPartyAccountNo(String partyAccountNo) {
            this.partyAccountNo = partyAccountNo;
        }

        public String getPartyAccountName() {
            return partyAccountName;
        }

        public void setPartyAccountName(String partyAccountName) {
            this.partyAccountName = partyAccountName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRechargeType() {
            return rechargeType;
        }

        public void setRechargeType(String rechargeType) {
            this.rechargeType = rechargeType;
        }

        public Date getRechargeDate() {
            return rechargeDate;
        }

        public void setRechargeDate(Date rechargeDate) {
            this.rechargeDate = rechargeDate;
        }

        public String getGivenAmount() {
            return givenAmount;
        }

        public void setGivenAmount(String givenAmount) {
            this.givenAmount = givenAmount;
        }
    }

    public class OrdBillingInfoRest {
        /**
         * 开票信息id
         * 表 : t_ord_billing_info
         * 对应字段 : billing_info_id
         */
        private Long billingInfoId;

        /**
         * 购买方类型0 个人 1 单位
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_type
         */
        private Integer buyerType;

        /**
         * 购买方纳税人识别号
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_taxpayer_no
         */
        private String buyerTaxpayerNo;

        /**
         * 购买方名称
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_name
         */
        private String buyerName;

        /**
         * 购买方地址、电话
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_address_phone
         */
        private String buyerAddressPhone;

        /**
         * 购买方银行账号
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_bank_card
         */
        private String buyerBankCard;

        /**
         * 购买方电子邮箱地址
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_email
         */
        private String buyerEmail;

        /**
         * 购买方手机号码
         * 表 : t_ord_billing_info
         * 对应字段 : buyer_mobile
         */
        private String buyerMobile;

        /**
         * 开户行名称
         * 表 : t_ord_billing_info
         * 对应字段 : bank_name
         */
        private String bankName;

        public Long getBillingInfoId() {
            return billingInfoId;
        }

        public void setBillingInfoId(Long billingInfoId) {
            this.billingInfoId = billingInfoId;
        }

        public Integer getBuyerType() {
            return buyerType;
        }

        public void setBuyerType(Integer buyerType) {
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

        public String getBuyerBankCard() {
            return buyerBankCard;
        }

        public void setBuyerBankCard(String buyerBankCard) {
            this.buyerBankCard = buyerBankCard;
        }

        public String getBuyerEmail() {
            return buyerEmail;
        }

        public void setBuyerEmail(String buyerEmail) {
            this.buyerEmail = buyerEmail;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }


    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public OrdOrderContractRest getOrdOrderContractRest() {
        return ordOrderContractRest;
    }

    public void setOrdOrderContractRest(OrdOrderContractRest ordOrderContractRest) {
        this.ordOrderContractRest = ordOrderContractRest;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public RechargeInformation getRechargeInformation() {
        return rechargeInformation;
    }

    public void setRechargeInformation(RechargeInformation rechargeInformation) {
        this.rechargeInformation = rechargeInformation;
    }

    public OrdBillingInfoRest getOrdBillingInfoRest() {
        return ordBillingInfoRest;
    }

    public void setOrdBillingInfoRest(OrdBillingInfoRest ordBillingInfoRest) {
        this.ordBillingInfoRest = ordBillingInfoRest;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


}
