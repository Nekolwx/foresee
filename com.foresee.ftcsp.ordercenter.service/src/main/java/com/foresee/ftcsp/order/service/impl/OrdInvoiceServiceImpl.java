/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;
import com.foresee.ftcsp.common.core.api.DictionaryApi;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.common.core.page.PageQueryResult;
import com.foresee.ftcsp.common.core.redis.IRedisClient;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.IDUtil;
import com.foresee.ftcsp.common.core.util.LocalDateUtil;
import com.foresee.ftcsp.common.core.util.StrUtil;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.goods.manual.dto.PdtBillingUnitDTO;
import com.foresee.ftcsp.goods.manual.restdto.QueryInvoiceUnitDTO;
import com.foresee.ftcsp.order.auto.dao.OrdBillInvoiceRelMapper;
import com.foresee.ftcsp.order.auto.dao.OrdBillMapper;
import com.foresee.ftcsp.order.auto.dao.OrdInvoiceExtendMapper;
import com.foresee.ftcsp.order.auto.dao.OrdInvoiceItemMapper;
import com.foresee.ftcsp.order.auto.dao.OrdInvoiceMapper;
import com.foresee.ftcsp.order.auto.dao.OrdOrderMapper;
import com.foresee.ftcsp.order.auto.model.OrdBill;
import com.foresee.ftcsp.order.auto.model.OrdBillInvoiceRel;
import com.foresee.ftcsp.order.auto.model.OrdBillItem;
import com.foresee.ftcsp.order.auto.model.OrdInvoice;
import com.foresee.ftcsp.order.auto.model.OrdInvoiceExtend;
import com.foresee.ftcsp.order.auto.model.OrdInvoiceItem;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.constant.BillConstant;
import com.foresee.ftcsp.order.constant.InvoiceConstant;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.manual.dao.OrdBillExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdBillItemExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdInvoiceExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdInvoiceItemExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.dao.OrdPayFlowingExtMapper;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.dto.OrdInvoiceExtendDTO;
import com.foresee.ftcsp.order.manual.dto.OrdInvoiceListDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.dto.OrdPayFlowingDTO;
import com.foresee.ftcsp.order.manual.restdto.BillInvoiceStatusDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceBuyerInfoResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceDetailDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceResultDTO;
import com.foresee.ftcsp.order.manual.restdto.InvoiceStatusResultDTO;
import com.foresee.ftcsp.order.manual.restdto.IssueElectronicInvoiceDTO;
import com.foresee.ftcsp.order.manual.restdto.IssueInvoiceResultDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceBuyerInfoDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryInvoiceStatusDTO;
import com.foresee.ftcsp.order.service.IOrdInvoiceService;
import com.foresee.ftcsp.order.util.HttpRequestUtil;
import com.foresee.ftcsp.order.util.InvoiceXmlUtil;
import com.foresee.ftcsp.util.MoneyUtil;
import com.foresee.ftcsp.order.util.NumberRandom;

/**
 * <pre>
 * 发票开具、发票查看相关业务处理
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @date 2017年8月31日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional
public class OrdInvoiceServiceImpl implements IOrdInvoiceService {

    private Logger                  LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private OrdInvoiceExtMapper     ordInvoiceExtMapper;

    @Resource
    private OrdOrderExtMapper       ordOrderExtMapper;

    @Resource
    private OrdPayFlowingExtMapper  ordPayFlowingExtMapper;

    @Resource
    private OrdBillExtMapper        ordBillExtMapper;

    @Resource
    private IdGenerator             idGenerator;

    @Autowired
    private GoodsApi                goodsApi;

    @Resource
    private OrdBillItemExtMapper    ordBillItemExtMapper;

    @Resource
    private OrdInvoiceItemExtMapper ordInvoiceItemExtMapper;

    @Resource
    private IDUtil                  idUtil;

    @Resource
    private OrdInvoiceMapper        ordInvoiceMapper;

    @Autowired
    private DictionaryApi           dictionaryApi;

    @Resource
    private OrdOrderMapper          ordOrderMapper;

    @Resource
    private OrdInvoiceExtendMapper  ordInvoiceExtendMapper;

    @Resource
    private OrdInvoiceItemMapper    ordInvoiceItemMapper;

    @Resource
    private OrdBillMapper           ordbillMapper;

    @Autowired
    IRedisClient                    redisClient;

    @Resource
    private OrdBillInvoiceRelMapper ordBillInvoiceRelMapper;


    @Override
    public PageQueryResult<OrdInvoiceListDTO> queryOrdInvoiceList(PageQueryParam param) {
        return ordInvoiceExtMapper.queryOrdInvoiceList(param);
    }

    @Override
    public InvoiceStatusResultDTO queryInvoiceStatus(QueryInvoiceStatusDTO queryInvoiceStatusDTO) {
        InvoiceStatusResultDTO invoiceStatusResultDTO = new InvoiceStatusResultDTO();
        OrdOrder order = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(queryInvoiceStatusDTO.getBusinessOrderNo(),
                Integer.parseInt(queryInvoiceStatusDTO.getChannel()));
        if (order == null) {
            throw new FtcspRuntimeException("03010001", (Object) "所查询的订单不存在，请重新检查请求参数");
        }
        invoiceStatusResultDTO.setOrderCode(order.getOrderCode());
        invoiceStatusResultDTO.setOrderInvoiceStatus(order.getBillingStatus());
        invoiceStatusResultDTO.setOrderPayStatus(order.getPayStatus());
        invoiceStatusResultDTO.setOrderPayAmount(order.getPayAmount());
        //如果订单为全额付款方式
        if (OrderConstant.DIVIDE_PAY_NO == order.getDividePay()) {
            invoiceStatusResultDTO.setDividePay(OrderConstant.DIVIDE_PAY_NO);
            return queryWhenDivideNo(invoiceStatusResultDTO, order);
        }
        //如果订单为分期付款方式
        if (OrderConstant.DIVIDE_PAY_YES == order.getDividePay()) {
            invoiceStatusResultDTO.setDividePay(OrderConstant.DIVIDE_PAY_YES);
            return queryWhenDivideYes(invoiceStatusResultDTO, order);
        }

        throw new FtcspRuntimeException("03010001", (Object) "订单内部错误，分期付款方式未定义");
    }

    /**
     * 查询开票状态时，订单为全额支付时所进行的查询处理。
     * @param invoiceStatusResultDTO
     * @param order
     * @return InvoiceStatusResultDTO
     */
    InvoiceStatusResultDTO queryWhenDivideNo(InvoiceStatusResultDTO invoiceStatusResultDTO, OrdOrder order) {
        invoiceStatusResultDTO.setDividePay(OrderConstant.DIVIDE_PAY_NO);
        //如果付款状态为已付款,返回订单的支付时间
        if (order.getPayStatus() == 4) {
            OrdPayFlowingDTO ordPayFlowingDTO = ordPayFlowingExtMapper.selectByOrderId(order.getOrderId());
            if (ordPayFlowingDTO == null) {
                throw new FtcspRuntimeException("03010001", (Object) "订单内部错误，支付流水记录不存在");
            }
            invoiceStatusResultDTO.setOrderPayTime(LocalDateUtil.getDateStr(LocalDateUtil.Y_M_D_HH_MM_SS, ordPayFlowingDTO.getPayTime()));
        }
        return invoiceStatusResultDTO;
    }

    /**
     * 查询开票状态时，订单为分期支付时所进行的查询处理。
     * @param invoiceStatusResultDTO
     * @param order
     * @return InvoiceStatusResultDTO
     */
    InvoiceStatusResultDTO queryWhenDivideYes(InvoiceStatusResultDTO invoiceStatusResultDTO, OrdOrder order) {
        //根据分期订单查询子账单
        OrdBillDTO billDTO = new OrdBillDTO();
        billDTO.setOrderId(order.getOrderId());
        List<OrdBillDTO> billList = ordBillExtMapper.queryByOrdBillDTO(billDTO);
        if (billList == null) {
            throw new FtcspRuntimeException("03010001", (Object) "订单内部错误，子账单记录不存在");
        }
        List<BillInvoiceStatusDTO> billStatusList = new ArrayList<>();
        for (OrdBillDTO bill : billList) {
            BillInvoiceStatusDTO billStatus = new BillInvoiceStatusDTO();
            billStatus.setBillCode(bill.getBillCode());
            billStatus.setBillInvoiceStatus(bill.getInvoiceStatus());
            billStatus.setBillPayAmount(bill.getBillAmount());
            billStatus.setBillPayStatus(bill.getPayStatus());
            billStatus.setBillPayTime(LocalDateUtil.getDateStr(LocalDateUtil.Y_M_D_HH_MM_SS, bill.getPayTime()));
            billStatusList.add(billStatus);
        }
        invoiceStatusResultDTO.setBillStatusList(billStatusList);

        return invoiceStatusResultDTO;
    }

    @Override
    public InvoiceResultDTO queryInvoice(QueryInvoiceDTO queryInvoiceDTO) {

        //查询订单是否存在
        InvoiceResultDTO invoiceResultDTO = new InvoiceResultDTO();
        OrdOrder order = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(queryInvoiceDTO.getBusinessOrderNo(),
                Integer.parseInt(queryInvoiceDTO.getChannel()));
        if (order == null) {
            throw new FtcspRuntimeException("03010002", (Object) "所查询的订单不存在，请重新检查请求参数");
        }
        invoiceResultDTO.setOrderCode(order.getOrderCode());

        //如果订单为全额付款方式
        if (OrderConstant.DIVIDE_PAY_NO == order.getDividePay()) {
            invoiceResultDTO.setDividePay(OrderConstant.DIVIDE_PAY_NO);
            //根据订单Id查询账单
            List<OrdBillDTO> billList = queryBillByOrderId(order.getOrderId(), order.getOrderTime());
            if (billList == null || billList.isEmpty()) {
                throw new FtcspRuntimeException("03010002", (Object) "所查询的订单尚未生成账单，请检查支付状态");
            }
            //根据账单Id查询发票
            List<InvoiceDetailDTO> invoiceList = queryInvoiceByBillId(billList.get(0).getBillId());
            if (invoiceList == null || invoiceList.isEmpty()) {
                throw new FtcspRuntimeException("03010002", (Object) "所查询的订单尚未开具发票");
            }
            //封装返回的发票信息
            invoiceList = dealInvoiceList(invoiceList, billList.get(0).getBillCode());
            invoiceResultDTO.setInvoiceList(invoiceList);
            return invoiceResultDTO;
        }
        //如果订单为分期付款方式
        if (OrderConstant.DIVIDE_PAY_YES == order.getDividePay()) {
            //校验入参billCode是否为空
            if (queryInvoiceDTO.getBillCode() == null || "".equals(queryInvoiceDTO.getBillCode())) {
                throw new FtcspRuntimeException("03010002", (Object) "查询分期付款的订单发票，账单编码[billCode]不能为空");
            }
            invoiceResultDTO.setDividePay(OrderConstant.DIVIDE_PAY_YES);
            //根据订单Id查询账单
            List<OrdBillDTO> billList = queryBillByOrderId(order.getOrderId(), order.getOrderTime());
            if (billList == null || billList.isEmpty()) {
                throw new FtcspRuntimeException("03010002", (Object) "所查询的订单尚未生成账单，请检查支付状态");
            }
            //查询订单是否与所传的billCode相关联
            Long billId = queryIfAssociated(billList, queryInvoiceDTO.getBillCode());
            if (billId == null) {
                throw new FtcspRuntimeException("03010002", (Object) "订单编码和账单编码不匹配");
            }
            //根据账单Id查询发票信息
            List<InvoiceDetailDTO> invoiceList = queryInvoiceByBillId(billId);
            if (invoiceList == null) {
                throw new FtcspRuntimeException("03010002", (Object) "所查询的订单尚未开具发票");
            }
            invoiceList = dealInvoiceList(invoiceList, queryInvoiceDTO.getBillCode());
            invoiceResultDTO.setInvoiceList(invoiceList);
            return invoiceResultDTO;
        }
        throw new FtcspRuntimeException("03010002", (Object) "订单内部错误，分期付款方式未定义");

    }

    /**
     * 根据订单Id查询相关联的账单。
     * @param orderId 订单Id
     * @param orderTime 下单时间，关联父子订单时用到
     * @return List<OrdBillDTO>
     */
    List<OrdBillDTO> queryBillByOrderId(Long orderId, Date orderTime) {
        //先查询父订单没有子订单的账单情况
        OrdBillDTO billDTO = new OrdBillDTO();
        billDTO.setOrderId(orderId);
        List<OrdBillDTO> billList = ordBillExtMapper.queryByOrdBillDTO(billDTO);
        if (billList == null || billList.isEmpty()) {
            //再查询父订单存在子订单的账单情况
            List<OrdOrderDTO> orderList = ordOrderExtMapper.selectByParentId(orderId, orderTime);
            if (orderList != null && !orderList.isEmpty()) {
                billDTO.setOrderId(orderList.get(0).getOrderId());
                billList = ordBillExtMapper.queryByOrdBillDTO(billDTO);
            }
        }

        return billList;
    }

    /**
     * 根据账单Id查询发票信息。
     * @param billId 账单Id
     * @return List<InvoiceDetailDTO>
     */
    List<InvoiceDetailDTO> queryInvoiceByBillId(Long billId) {
        OrdBillDTO billDTO = new OrdBillDTO();
        billDTO.setBillId(billId);
        List<InvoiceDetailDTO> invoiceList = ordInvoiceExtMapper.queryInvoiceByBillId(billDTO);
        return invoiceList;
    }

    /**
     * 封装返回的发票信息。
     * @param invoiceList 查询到的发票信息
     * @param billCode 发票对应的账单编码
     * @return List<InvoiceDetailDTO>
     */
    List<InvoiceDetailDTO> dealInvoiceList(List<InvoiceDetailDTO> invoiceList, String billCode) {
        for (InvoiceDetailDTO invoiceDetail : invoiceList) {
            invoiceDetail.setBillCode(billCode);
            invoiceDetail.setBillingTime(LocalDateUtil.getDateStr(LocalDateUtil.Y_M_D_HH_MM_SS, invoiceDetail.getBillingTimeD()));
            String fpDownLoadUrl = dictionaryApi.getDynamicFeildByDicCodeAndItemCode(InvoiceConstant.INVOICE_CONFIG, "fpDownloadUrl",
                    "itemValue");
            String xzm = invoiceDetail.getDownloadCode();
            if (StringUtils.isBlank(fpDownLoadUrl)) {
                LOGGER.info("字典管理值缺失=====dicCode为:" + InvoiceConstant.INVOICE_CONFIG + " itemCode为：" + "fpDownloadUrl");
                throw new FtcspRuntimeException("03010004", (Object) "请在字典管理配置发票服务查看、下载发票地址！");
            }
            StringBuffer url = new StringBuffer();
            url.append(fpDownLoadUrl);
            url.append(xzm);
            url.append("?type=preview");

            /*
             * StringBuffer url = new StringBuffer();
             * url.append("http://183.62.253.122:6879/dzfpfw/dzfp/pdf/download.do?method=downloadPdf"); url.append("&fpDm=");
             * url.append(invoiceDetail.getInvoiceCode()); url.append("&fpHm="); url.append(invoiceDetail.getInvoiceNo());
             * url.append("&jym="); url.append(invoiceDetail.getValidateCode()); url.append("&type=preview");
             */
            invoiceDetail.setInvoiceUrl(url + "");
        }
        return invoiceList;
    }

    /**
     * 查询订单是否与所传的billCode相关联。
     * @param billList
     * @param billCode
     * @return Long 若相关联，则返回billId,没有则返回null
     */
    Long queryIfAssociated(List<OrdBillDTO> billList, String billCode) {
        OrdBillDTO billDTO = new OrdBillDTO();
        for (OrdBillDTO ordBillDTO : billList) {
            if (billCode.equals(ordBillDTO.getBillCode())) {
                billDTO.setBillId(ordBillDTO.getBillId());
            }
        }
        return billDTO.getBillId();
    }

    @Override
    public InvoiceBuyerInfoResultDTO queryInvoiceBuyerInfo(QueryInvoiceBuyerInfoDTO queryInvoiceBuyerInfoDTO) {
        //查询订单是否存在
        OrdOrder order = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(queryInvoiceBuyerInfoDTO.getBusinessOrderNo(),
                Integer.parseInt(queryInvoiceBuyerInfoDTO.getChannel()));
        if (order == null) {
            throw new FtcspRuntimeException("03010003", (Object) "所查询的订单不存在，请重新检查请求参数");
        }

        //先查订单的历史开票记录是否存在,父订单没有子订单情况
        List<InvoiceBuyerInfoResultDTO> list = ordInvoiceExtMapper.queryBuyerInfoByOrderId(order.getOrderId());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        //再查订单的历史开票记录是否存在,父订单存在子订单情况
        List<InvoiceBuyerInfoResultDTO> listExt = ordInvoiceExtMapper.queryBuyerInfoByParentId(order.getOrderId(), order.getOrderTime());
        if (listExt != null && !listExt.isEmpty()) {
            return listExt.get(0);
        }
        //再查询下单时候所填的购买方信息
        InvoiceBuyerInfoResultDTO invoiceBuyerInfoResultDTO = ordBillExtMapper.queryBuyerInfoByOrderId(order.getOrderId());
        return invoiceBuyerInfoResultDTO;
    }

    @Override
    public IssueInvoiceResultDTO issueElectronicInvoice(IssueElectronicInvoiceDTO issueElectronicInvoiceDTO) {
        List<OrdBillDTO> ordBillDTOList = null;//待开票账单集合
        OrdBillDTO queryDto = new OrdBillDTO();//查询账单dto
        List<String> billCodeList = new ArrayList<>();//红冲账单集合
        OrdOrder order = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(issueElectronicInvoiceDTO.getBusinessOrderNo(),
                Integer.parseInt(issueElectronicInvoiceDTO.getChannel()));
        if (order == null) {
            throw new FtcspRuntimeException("03010004", (Object) "所查询的订单不存在，请重新检查请求参数");
        }
        //申请开蓝票
        if (issueElectronicInvoiceDTO.getBillingType().equals(InvoiceConstant.INTERFACE_BILLING_TYPE.BLUE)) {
            if (issueElectronicInvoiceDTO.getBuyerType() == null) {
                throw new FtcspRuntimeException("03010004", (Object) "购买方类型[buyerType]不能为空");
            }
            if (StringUtils.isBlank(issueElectronicInvoiceDTO.getBuyerName())) {
                throw new FtcspRuntimeException("03010004", (Object) "购买方姓名[buyerName]不能为空");
            }
            if (issueElectronicInvoiceDTO.getBuyerType() != 0 && issueElectronicInvoiceDTO.getBuyerType() != 1) {
                throw new FtcspRuntimeException("03010004", (Object) "购买方类型[buyerType]不能只能为0[个人]或者1[单位]");
            }
            if (issueElectronicInvoiceDTO.getBuyerType() == 1) {
                if (StringUtils.isBlank(issueElectronicInvoiceDTO.getBuyerTaxpayerNo())) {
                    throw new FtcspRuntimeException("03010004", (Object) "当购买方类型[buyerType]为1（单位）时，购买方纳税人识别号[buyerTaxpayerNo]不能为空");
                }
            }
        } else if (issueElectronicInvoiceDTO.getBillingType().equals(InvoiceConstant.INTERFACE_BILLING_TYPE.RED)) {
            if (StringUtils.isBlank(issueElectronicInvoiceDTO.getOriginalInvoiceCode())) {
                throw new FtcspRuntimeException("03010004", (Object) "申请开红字发票时原发票代码[originalInvoiceCode]不能为空");
            }
            if (StringUtils.isBlank(issueElectronicInvoiceDTO.getOriginalInvoiceNo())) {
                throw new FtcspRuntimeException("03010004", (Object) "申请开红字发票时原发票号码[originalInvoiceNo]不能为空");
            }
            OrdInvoiceExtendDTO invoiceExtendDTO = new OrdInvoiceExtendDTO();
            invoiceExtendDTO.setInvoiceCode(issueElectronicInvoiceDTO.getOriginalInvoiceCode());
            invoiceExtendDTO.setInvoiceNumber(issueElectronicInvoiceDTO.getOriginalInvoiceNo());
            invoiceExtendDTO.setOrderId(order.getOrderId());
            List<OrdBillDTO> billDTOCheckList = ordBillExtMapper.queryBillByOrderIdAndInvoiceInfo(invoiceExtendDTO);
            if (billDTOCheckList.isEmpty()) {
                invoiceExtendDTO.setOrderId(null);
                invoiceExtendDTO.setParentId(order.getOrderId());
                invoiceExtendDTO.setOrderTime(order.getOrderTime());
                billDTOCheckList = ordBillExtMapper.queryBillByOrderIdAndInvoiceInfo(invoiceExtendDTO);
                if (billDTOCheckList.isEmpty()) {
                    throw new FtcspRuntimeException("03010004", (Object) "订单与发票信息不匹配或该发票不符合红冲条件，红冲操作失败！");
                }
            }
            for (OrdBillDTO billDTO : billDTOCheckList) {
                billCodeList.add(billDTO.getBillCode());
            }
        } else {
            throw new FtcspRuntimeException("03010004", (Object) "开票类型[billingType]不合法");
        }

        boolean flag = redisClient.setNx("ordercenter_issueInvoice_wait" + order.getBusinessOrderNo() + order.getChannel(), "wait", 3l);
        if (!flag) {
            throw new FtcspRuntimeException("03010004", (Object) "此业务订单号正在开票中，请不要重复提交!");
        }

        //构造发票流水
        OrdInvoice ordInvoice = buildOrdInvoice(issueElectronicInvoiceDTO, order);

        if (OrderConstant.DIVIDE_PAY_YES.equals(order.getDividePay())) {//分期付款订单，可以根据账单开票
            if (StringUtils.isBlank(issueElectronicInvoiceDTO.getBillCode())) {
                throw new FtcspRuntimeException("03010004", (Object) "分期付款的订单开票时账单编码[billCode]不能为空");
            } else {
                ordBillDTOList = new ArrayList<>();
                int start = 0;
                String[] billCodeAr = issueElectronicInvoiceDTO.getBillCode().split(",");
                for (String billCode : billCodeAr) {
                    if (issueElectronicInvoiceDTO.getBillingType().equals(InvoiceConstant.INTERFACE_BILLING_TYPE.RED)
                            && !billCodeList.contains(billCode)) {
                        throw new FtcspRuntimeException("03010004", (Object) ("分期账单[billCode]+" + billCode + "+跟待红冲的账单不匹配！"));
                    }
                    queryDto.setBillCode(billCode);
                    List<OrdBillDTO> resultDtos = ordBillExtMapper.queryByOrdBillDTO(queryDto);
                    if (resultDtos != null && !resultDtos.isEmpty()) {
                        if (!resultDtos.get(0).getOrderId().equals(order.getOrderId())) {
                            throw new FtcspRuntimeException("03010004", (Object) ("账单[billCode]+" + billCode + "+跟订单不匹配！"));
                        }
                        start++;
                        ordBillDTOList.add(resultDtos.get(0));
                    } else {
                        throw new FtcspRuntimeException("03010004", (Object) ("根据账单编码[billCode]+" + billCode + "+查询不到账单"));
                    }
                }
                if (issueElectronicInvoiceDTO.getBillingType().equals(InvoiceConstant.INTERFACE_BILLING_TYPE.RED)
                        && start != billCodeList.size()) {
                    throw new FtcspRuntimeException("03010004", (Object) "分期账单跟待红冲的账单数量不匹配！");
                }
                return collectInvoiceInfo(ordBillDTOList, ordInvoice);
            }
        } else if (OrderConstant.DIVIDE_PAY_NO.equals(order.getDividePay())) {
            queryDto.setOrderId(order.getOrderId());
            ordBillDTOList = ordBillExtMapper.queryByOrdBillDTO(queryDto);
            if (ordBillDTOList == null || ordBillDTOList.isEmpty()) {
                queryDto.setOrderId(null);
                queryDto.setParentId(order.getOrderId());
                queryDto.setOrderTime(order.getOrderTime());
                ordBillDTOList = ordBillExtMapper.queryByOrdBillDTO(queryDto);
                if (ordBillDTOList == null || ordBillDTOList.isEmpty()) {
                    throw new FtcspRuntimeException("03010004", (Object) "所查询的订单未查询到账单，开票失败");
                }
            }
            //全额付款的订单,可以根据订单开票
            if (BillConstant.BILL_INVOICE_STATUS_BLUE_FAIL.equals(order.getBillingStatus())
                    || BillConstant.BILL_INVOICE_STATUS_RED_FAIL.equals(order.getBillingStatus())) {
                //针对红冲失败或开票失败的进行处理
                List<OrdInvoice> invoiceList = ordInvoiceExtMapper.queryOrdInvoiceFailByBillId(ordBillDTOList.get(0).getBillId(),
                        InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL, issueElectronicInvoiceDTO.getBillingType());
                ordBillDTOList = null;
                if (!invoiceList.isEmpty() && invoiceList.size() == 1) {
                    OrdInvoice failInvoice = invoiceList.get(0);
                    failInvoice.setCallNumber(0);
                    failInvoice.setBuyerName(ordInvoice.getBuyerName());
                    failInvoice.setBuyerTaxpayerNo(ordInvoice.getBuyerTaxpayerNo());
                    failInvoice.setSerialNumber(ordInvoice.getSerialNumber());
                    return collectInvoiceInfo(null, failInvoice);
                } else {
                    LOGGER.info("有效的失败开票流水记录应该只有一条，实际条数为===============" + (invoiceList == null ? 0 : invoiceList.size()));
                    throw new FtcspRuntimeException("03010004", (Object) "开票失败再次开票查询发票流水出错！");
                }
            }
            return collectInvoiceInfo(ordBillDTOList, ordInvoice);
        } else {
            throw new FtcspRuntimeException("03010004", (Object) "订单内部状态错误，不允许开票");
        }
    }

    /**
     * 保存信息到发票信息表并且根据配置决定是否即时开票
     * @param ordBillDTOList
     * @param issueElectronicInvoiceDTO
     * @return Map<String,Object>
     */
    public IssueInvoiceResultDTO collectInvoiceInfo(List<OrdBillDTO> ordBillDTOList, OrdInvoice ordInvoice) {
        Integer billingType = ordInvoice.getBillingType();
        Long totalAmount = 0L;
        Long totalTax = 0L;
        BigDecimal oriAmount = new BigDecimal(0);
        //发票项目List
        List<OrdInvoiceItem> invoiceItemList = new ArrayList<OrdInvoiceItem>();

        //红冲折扣项集合
        Map<String, List<OrdInvoiceItem>> srcMap = new HashMap<>();

        //发票信息与账单关联List
        List<OrdBillInvoiceRel> billInvoiceRelList = new ArrayList<OrdBillInvoiceRel>();

        IssueInvoiceResultDTO issueInvoiceResultDTO = new IssueInvoiceResultDTO();

        if (ordBillDTOList == null && ordInvoice.getCallStatus().equals(InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL)) {
            //处理失败的开票，再次开票
            invoiceItemList = ordInvoiceItemExtMapper.queryInvoiceItemByInvoiceId(ordInvoice.getInvoiceId());
            if (invoiceItemList == null || invoiceItemList.isEmpty()) {
                throw new FtcspRuntimeException("03010004", (Object) "根据发票ID获取发票项目失败！");
            }
        } else {
            for (OrdBillDTO ordBillDTO : ordBillDTOList) {
                //校验账单付款状态和开票状态
                checkBillInvoiceStatus(ordBillDTO, billingType);
                //发票与账单关联关系维护
                OrdBillInvoiceRel billInvoiceRel = new OrdBillInvoiceRel();
                billInvoiceRel.setBillInvoiceRelId(idGenerator.getLong());
                LOGGER.debug("==================获取到的账单ID为==========" + ordBillDTO.getBillId());
                billInvoiceRel.setBillId(ordBillDTO.getBillId());
                billInvoiceRel.setInvoiceId(ordInvoice.getInvoiceId());
                billInvoiceRel.setCreateUser(ordInvoice.getCreateUser());
                billInvoiceRelList.add(billInvoiceRel);

                List<OrdBillItem> ordBillItems = ordBillItemExtMapper.queryBillItemByBillId(ordBillDTO.getBillId());
                if (ordBillItems.isEmpty()) {
                    throw new FtcspRuntimeException("03010004", (Object) ("账单[" + ordBillDTO.getBillCode() + "]无账单项目！"));
                }
                for (OrdBillItem billItem : ordBillItems) {

                    if (billItem.getQty() == null || billItem.getPrice() == null || billItem.getTaxRate() == null
                            || billItem.getItemType() == null) {
                        throw new FtcspRuntimeException("03010004", (Object) "账单项目单价/数量/税率/发票行性质不能为空！");
                    }
                    if (billItem.getTaxRate().doubleValue() > 1) {
                        throw new FtcspRuntimeException("03010004", (Object) "税率不能大于1(100%)");
                    }
                    OrdInvoiceItem invoiceItem = new OrdInvoiceItem();
                    BeanUtils.copyProperties(billItem, invoiceItem);
                    invoiceItem.setInvoiceItemId(idGenerator.getLong());
                    invoiceItem.setLineNature(billItem.getItemType());
                    invoiceItem.setInvoiceId(ordInvoice.getInvoiceId());
                    if (billItem.getItemType().equals(InvoiceConstant.INTERFACE_LINE_NATURE.ZHE_KOU)) {
                        //处理折扣行，折扣行金额只能为负数
                        invoiceItem.setAmount(Long.valueOf(MoneyUtil
                                .getFenFromYuan2(billItem.getAmount().doubleValue() / (1 + billItem.getTaxRate().doubleValue()))));
                        invoiceItem.setTaxAmount(Long
                                .valueOf(Math.round(invoiceItem.getAmount().doubleValue() * (invoiceItem.getTaxRate().doubleValue()))));
                        if (InvoiceConstant.INTERFACE_BILLING_TYPE.RED.equals(billingType)) {
                            srcMap = putInvoiceItem(srcMap, invoiceItem);
                            continue;
                        }
                    } else {
                        if (InvoiceConstant.INTERFACE_BILLING_TYPE.RED.equals(billingType)) {
                            invoiceItem.setQty(-billItem.getQty());
                        }
                        invoiceItem.setPrice(new BigDecimal(
                                taxComputer(billItem.getPrice().doubleValue() * 100, billItem.getTaxRate().doubleValue())));
                        invoiceItem.setAmount(Long.valueOf(
                                MoneyUtil.getFenFromYuan2(invoiceItem.getPrice().doubleValue() / 100 * invoiceItem.getQty().intValue())));
                        invoiceItem.setTaxAmount(
                                Long.valueOf(Math.round((invoiceItem.getQty().intValue() * invoiceItem.getPrice().doubleValue())
                                        * (invoiceItem.getTaxRate().doubleValue()))));
                    }

                    invoiceItem.setCreateUser(ordInvoice.getCreateUser());
                    totalTax += invoiceItem.getTaxAmount();
                    totalAmount += invoiceItem.getAmount();
                    invoiceItemList.add(invoiceItem);
                }
                if (InvoiceConstant.INTERFACE_BILLING_TYPE.RED.equals(billingType)) {
                    oriAmount = oriAmount.add(ordBillDTO.getBillAmount().negate());
                } else {
                    oriAmount = oriAmount.add(ordBillDTO.getBillAmount());
                }
            }

            if (InvoiceConstant.INTERFACE_BILLING_TYPE.RED.equals(billingType) && !srcMap.isEmpty()) {
                //如果折扣map不为空且为红冲操作时
                totalAmount = 0L;
                totalTax = 0L;
                for (OrdInvoiceItem invoiceItem2 : invoiceItemList) {
                    invoiceItem2 = computeHcAmountAndTax(srcMap, invoiceItem2);
                    totalTax += invoiceItem2.getTaxAmount();
                    totalAmount += invoiceItem2.getAmount();
                }
            }
            ordInvoice.setTotalAmount(totalAmount);
            ordInvoice.setTotalTax(totalTax);
            ordInvoice.setTotalAmountTax(totalAmount + totalTax);
            if (!checkOrdIvoiceJSHJ(oriAmount, ordInvoice.getTotalAmountTax())) {
                throw new FtcspRuntimeException("03010004", (Object) "账单总价与价税合计不等，开票失败！");
            }
            /*---开发票相关信息采集完毕,数据进行入库操作---*/
            ordInvoiceMapper.insertSelective(ordInvoice);
            if (billInvoiceRelList.size() > 0 && invoiceItemList.size() > 0) {
                for (OrdBillInvoiceRel billInvoiceRel : billInvoiceRelList) {
                    ordBillInvoiceRelMapper.insertSelective(billInvoiceRel);
                }
                for (OrdInvoiceItem invoiceItem : invoiceItemList) {
                    ordInvoiceItemMapper.insertSelective(invoiceItem);
                }
            }
        }

        //根据字典管理配置是否即时开票
        String generateType = dictionaryApi.getDynamicFeildByDicCodeAndItemCode(InvoiceConstant.INVOICE_CONFIG, "generateType",
                "itemValue");
        if (StringUtils.isBlank(generateType)) {
            throw new FtcspRuntimeException("03010004", (Object) ("请在字典管理配置发票生成控制（0：手动（即时开票） 1：自动（异步调度开票）！"));
        }
        if ((InvoiceConstant.INTERFACE_GENERATE_TYPE.MANUAL).equals(generateType)) {
            LOGGER.debug("===============即时开票开始=============");
            OrdInvoiceExtend ordInvoiceExtend = this.generateInvoice(ordInvoice, invoiceItemList,
                    InvoiceConstant.INTERFACE_GENERATE_TYPE.MANUAL);
            issueInvoiceResultDTO.setReturnCode(ordInvoiceExtend.getReturnCode());
            issueInvoiceResultDTO.setReturnMsg(ordInvoiceExtend.getReturnMsg());
            if (InvoiceConstant.INVOICE_SERVICE_PLATFORM_RESPONSE_CODE.equals(ordInvoiceExtend.getReturnCode())) {
                String invoiceUrl = this.getInvoiceUrl(ordInvoiceExtend);
                issueInvoiceResultDTO.setInvoiceUrl(invoiceUrl);
            }
        } else {
            //调度开票,更新账单、订单的开票状态为开票中或红冲中
            updateBillOrOrderInvoiceStatus(billingType, InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG, ordInvoice.getInvoiceId());
            issueInvoiceResultDTO.setReturnCode(InvoiceConstant.INVOICE_SERVICE_PLATFORM_RESPONSE_CODE);
            issueInvoiceResultDTO.setReturnMsg("提交申请开具发票成功，请耐心等待后台自动开票！");
        }
        return issueInvoiceResultDTO;
    }

    /**
     * 校验账单的付款状态和发票状态
     * @param ordBillDTO
     * @param billingType void
     */
    private void checkBillInvoiceStatus(OrdBillDTO ordBillDTO, Integer billingType) {
        if (!ordBillDTO.getPayStatus().equals(BillConstant.BILL_PAY_STATUS_ALREADY)) {
            throw new FtcspRuntimeException("03010004", (Object) ("账单[" + ordBillDTO.getBillCode() + "]付款状态异常，不允许开票"));
        }
        if (ordBillDTO.getInvoiceStatus().equals(BillConstant.BILL_INVOICE_STATUS_BLUE_ING)) {
            throw new FtcspRuntimeException("03010004", (Object) ("账单[" + ordBillDTO.getBillCode() + "]正在开票中！"));
        } else if (ordBillDTO.getInvoiceStatus().equals(BillConstant.BILL_INVOICE_STATUS_RED_ING)) {
            throw new FtcspRuntimeException("03010004", (Object) ("账单[" + ordBillDTO.getBillCode() + "]正在红冲中!"));
        } else if (billingType.equals(InvoiceConstant.INTERFACE_BILLING_TYPE.BLUE)
                && ordBillDTO.getInvoiceStatus().equals(BillConstant.BILL_INVOICE_STATUS_BLUE_ALREADY)) {
            throw new FtcspRuntimeException("03010004", (Object) ("账单[" + ordBillDTO.getBillCode() + "]已开票！不允许重复开票"));
        } else if (billingType.equals(InvoiceConstant.INTERFACE_BILLING_TYPE.RED)
                && ordBillDTO.getInvoiceStatus().equals(BillConstant.BILL_INVOICE_STATUS_RED_ALREADY)) {
            throw new FtcspRuntimeException("03010004", (Object) ("账单[" + ordBillDTO.getBillCode() + "]已红冲，不允许重复红冲"));
        }
    }

    /**
     * 查询商品销售方信息
     * @param ordInvoice
     * @param skuId
     * @return OrdInvoice
     */
    private OrdInvoice getSellerInfo(OrdInvoice ordInvoice, Long skuId) {
        if (skuId == null) {
            LOGGER.info("订单skuid为空！查询销售方信息失败");
            throw new FtcspRuntimeException("03010004", (Object) "查询商品销售方信息失败");
        }
        QueryInvoiceUnitDTO queryInvoiceUnitDTO = new QueryInvoiceUnitDTO();
        queryInvoiceUnitDTO.setGoodsSkuId(skuId);
        RestResponse<PdtBillingUnitDTO> response = goodsApi.queryInvoiceUnitBySkuId(queryInvoiceUnitDTO);
        if ("0".equals(response.getHead().getErrorCode())) {
            PdtBillingUnitDTO pdtBillingUnitDTO = response.getBody();
            if (pdtBillingUnitDTO != null) {
                ordInvoice.setSellerName(pdtBillingUnitDTO.getBillingUnitName());
                ordInvoice.setSellerTaxpayerNo(pdtBillingUnitDTO.getTaxpayerIdentificationNumber());
                ordInvoice.setSellerAddressPhone(pdtBillingUnitDTO.getAddress() + pdtBillingUnitDTO.getPhone());
                ordInvoice.setSellerBankCard(pdtBillingUnitDTO.getAccounts());
                ordInvoice.setDrawer(pdtBillingUnitDTO.getIssuer());
                ordInvoice.setPayee(pdtBillingUnitDTO.getPayee());
                ordInvoice.setReviewer(pdtBillingUnitDTO.getReviewer());
                return ordInvoice;
            } else {
                LOGGER.info("查询商品销售方信息出错原因===返回报文体body为空！");
                throw new FtcspRuntimeException("03010004", (Object) "查询商品销售方信息失败！");
            }
        } else {
            LOGGER.info("查询商品销售方信息出错原因===" + response.getHead().getErrorMsg());
            throw new FtcspRuntimeException("03010004", (Object) "查询商品销售方信息失败！");
        }
    }

    /**
     * 获取购买方信息（优先级顺序：参数传来-->开过票的购买方信息-->订单关联的购买方信息）
     * @param issueElectronicInvoiceDTO
     * @param orderId
     * @return OrdInvoice
     */
    private OrdInvoice getBuyerInfo(OrdInvoice ordInvoice, IssueElectronicInvoiceDTO issueElectronicInvoiceDTO, OrdOrder order) {

        if (issueElectronicInvoiceDTO != null) {
            if (issueElectronicInvoiceDTO.getBuyerType() != null && !StringUtils.isBlank(issueElectronicInvoiceDTO.getBuyerName())) {
                //从请求参数里取购买方信息
                ordInvoice.setBuyerType(issueElectronicInvoiceDTO.getBuyerType());
                ordInvoice.setBuyerTaxpayerNo(issueElectronicInvoiceDTO.getBuyerTaxpayerNo());
                ordInvoice.setBuyerName(issueElectronicInvoiceDTO.getBuyerName());
                if (!StringUtils.isBlank(issueElectronicInvoiceDTO.getBuyerAddress())
                        && !StringUtils.isBlank(issueElectronicInvoiceDTO.getBuyerPhone())) {
                    ordInvoice.setBuyerAddressPhone(
                            issueElectronicInvoiceDTO.getBuyerAddress() + issueElectronicInvoiceDTO.getBuyerPhone());
                }
                ordInvoice.setBuyerBankCard(issueElectronicInvoiceDTO.getBuyerBankAccount());
                ordInvoice.setBuyerMobile(issueElectronicInvoiceDTO.getBuyerMobile());
                return ordInvoice;
            }
        }

        InvoiceBuyerInfoResultDTO invoiceBuyerInfoResultDTO = null;
        //先查订单的历史开票记录是否存在,父订单没有子订单情况
        List<InvoiceBuyerInfoResultDTO> list = ordInvoiceExtMapper.queryBuyerInfoByOrderId(order.getOrderId());
        if (list != null && !list.isEmpty()) {
            invoiceBuyerInfoResultDTO = list.get(0);
        } else {
            //再查订单的历史开票记录是否存在,父订单存在子订单情况
            List<InvoiceBuyerInfoResultDTO> listExt = ordInvoiceExtMapper.queryBuyerInfoByParentId(order.getOrderId(),
                    order.getOrderTime());
            if (listExt != null && !listExt.isEmpty()) {
                invoiceBuyerInfoResultDTO = listExt.get(0);
            } else {
                //再查询下单时候所填的购买方信息
                invoiceBuyerInfoResultDTO = ordBillExtMapper.queryBuyerInfoByOrderId(order.getOrderId());
            }
        }

        if (invoiceBuyerInfoResultDTO != null && !StringUtils.isBlank(invoiceBuyerInfoResultDTO.getBuyerName())) {
            ordInvoice.setBuyerType(invoiceBuyerInfoResultDTO.getBuyerType());
            ordInvoice.setBuyerTaxpayerNo(invoiceBuyerInfoResultDTO.getBuyerTaxpayerNo());
            ordInvoice.setBuyerName(invoiceBuyerInfoResultDTO.getBuyerName());
            ordInvoice.setBuyerAddressPhone(invoiceBuyerInfoResultDTO.getBuyerAddressPhone());
            ordInvoice.setBuyerBankCard(invoiceBuyerInfoResultDTO.getBuyerBankCard());
            ordInvoice.setBuyerMobile(invoiceBuyerInfoResultDTO.getBuyerMobile());
            return ordInvoice;
        } else {
            throw new FtcspRuntimeException("03010004", (Object) "购买方信息不存在，不允许开票！");
        }
    }

    /**
     * 更新订单或账单的开票状态
     * @param billingType
     * @param optFlag 1:开票中（红冲中） 2：开票成功（红冲成功） 3：开票失败（红冲失败）
     * @param invoiceId void
     */
    private void updateBillOrOrderInvoiceStatus(Integer billingType, Integer optFlag, Long invoiceId) {
        Integer invoiceStatus = 0;
        if (billingType.equals(InvoiceConstant.INTERFACE_BILLING_TYPE.BLUE)) {
            //蓝字发票
            if (optFlag.equals(InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG)) {
                invoiceStatus = BillConstant.BILL_INVOICE_STATUS_BLUE_ING;
            } else if (optFlag.equals(InvoiceConstant.INTERFACE_CALL_STATUS.YI_DIAO_YONG)) {
                invoiceStatus = BillConstant.BILL_INVOICE_STATUS_BLUE_ALREADY;
            } else if (optFlag.equals(InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL)) {
                invoiceStatus = BillConstant.BILL_INVOICE_STATUS_BLUE_FAIL;
            } else {
                throw new FtcspRuntimeException("03010004", (Object) "操作标识非法！");
            }
        } else {
            //红字发票
            if (optFlag.equals(InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG)) {
                invoiceStatus = BillConstant.BILL_INVOICE_STATUS_RED_ING;
            } else if (optFlag.equals(InvoiceConstant.INTERFACE_CALL_STATUS.YI_DIAO_YONG)) {
                invoiceStatus = BillConstant.BILL_INVOICE_STATUS_RED_ALREADY;
            } else if (optFlag.equals(InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL)) {
                invoiceStatus = BillConstant.BILL_INVOICE_STATUS_RED_FAIL;
            } else {
                throw new FtcspRuntimeException("03010004", (Object) "操作标识非法！");
            }
        }
        List<OrdBillDTO> fzBillDTOList = ordBillExtMapper.queryBillAndOrdByInvoiceId(invoiceId);
        OrdBill ordBill2 = new OrdBill();
        OrdOrder order2 = new OrdOrder();
        for (OrdBillDTO ordBillDTO : fzBillDTOList) {
            //更新订单开票状态
            order2.setOrderId(ordBillDTO.getOrderId());
            order2.setBillingStatus(invoiceStatus);
            ordOrderMapper.updateByPrimaryKeySelective(order2);
            //更新账单开票状态
            ordBill2.setBillId(ordBillDTO.getBillId());
            ordBill2.setInvoiceStatus(invoiceStatus);
            ordbillMapper.updateByPrimaryKeySelective(ordBill2);
        }
        //处理父子订单的情况，判断并更新父订单的开票状态
        if (OrderConstant.ONE.equals(fzBillDTOList.get(0).getIsParent())) { //如果是子订单，则要更新父订单的开票状态
            OrdOrder order = new OrdOrder();
            order.setOrderId(fzBillDTOList.get(0).getParentId());
            order.setBillingStatus(invoiceStatus);
            ordOrderMapper.updateByPrimaryKeySelective(order);
        }
    }

    /**
     * 如果手动开具发票或者手动红冲，则同步请求第三方系统（电子发票服务平台）开具；备注：针对自动或者批量由定时任务执行
     * @param ordInvoice
     * @param invoiceItemList
     * @param generateType
     * @return OrdInvoiceExtend
     */
    @Override
    public OrdInvoiceExtend generateInvoice(OrdInvoice ordInvoice, List<OrdInvoiceItem> invoiceItemList, String generateType) {
        OrdInvoiceExtend invoiceExtend = null;
        String result = null;
        String fwpt_url = dictionaryApi.getDynamicFeildByDicCodeAndItemCode(InvoiceConstant.INVOICE_CONFIG, "fwptUrl", "itemValue");
        String principal = dictionaryApi.getDynamicFeildByDicCodeAndItemCode(InvoiceConstant.INVOICE_CONFIG, "principal", "itemValue");
        String credentials = dictionaryApi.getDynamicFeildByDicCodeAndItemCode(InvoiceConstant.INVOICE_CONFIG, "credentials",
                "itemValue");
        if (StringUtils.isBlank(fwpt_url) || StringUtils.isBlank(principal) || StringUtils.isBlank(credentials)) {
            LOGGER.info("字典管理值缺失=====dicCode为:" + InvoiceConstant.INVOICE_CONFIG + " itemCode为：" + "fwptUrl/principal/credentials");
            throw new FtcspRuntimeException("03010004", (Object) "请在字典管理配置发票服务平台地址/用户名/密码！");
        }
        try {
            List<Map<String, Object>> fpkjXmxxParams = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("principal", principal);
            paramMap.put("credentials", credentials);
            paramMap.put("sellerTaxpayerNo", ordInvoice.getSellerTaxpayerNo());
            paramMap.put("code", ordInvoice.getSerialNumber());
            paramMap.put("billingType", ordInvoice.getBillingType());
            paramMap.put("sellerName", ordInvoice.getSellerName());
            paramMap.put("sellerAddressPhone", ordInvoice.getSellerAddressPhone());
            paramMap.put("sellerBankCard", ordInvoice.getSellerBankCard());
            paramMap.put("buyerTaxpayerNo", ordInvoice.getBuyerTaxpayerNo());
            paramMap.put("buyerName", ordInvoice.getBuyerName());
            paramMap.put("buyerAddressPhone", ordInvoice.getBuyerAddressPhone());
            paramMap.put("buyerBankCard", ordInvoice.getBuyerBankCard());
            paramMap.put("drawer", ordInvoice.getDrawer());
            paramMap.put("payee", ordInvoice.getPayee());
            paramMap.put("reviewer", ordInvoice.getReviewer());
            paramMap.put("originalInvoiceCode", ordInvoice.getOriginalInvoiceCode());
            paramMap.put("originalInvoiceNo", ordInvoice.getOriginalInvoiceNo());

            paramMap.put("totalAmountTax", ordInvoice.getTotalAmountTax());
            paramMap.put("totalAmount", ordInvoice.getTotalAmount());
            paramMap.put("totalTax", ordInvoice.getTotalTax());
            paramMap.put("buyerEmail", ordInvoice.getBuyerEmail());
            paramMap.put("buyerMobile", ordInvoice.getBuyerMobile());
            for (OrdInvoiceItem invoiceItem : invoiceItemList) {
                Map<String, Object> invoiceItemMap = new HashMap<>();
                invoiceItemMap.put("lineNature", invoiceItem.getLineNature());
                invoiceItemMap.put("itemName", invoiceItem.getItemName());
                invoiceItemMap.put("itemCode", invoiceItem.getItemCode());
                invoiceItemMap.put("qty", invoiceItem.getQty());
                invoiceItemMap.put("price", invoiceItem.getPrice());
                invoiceItemMap.put("amount", invoiceItem.getAmount());

                invoiceItemMap.put("taxRate", invoiceItem.getTaxRate());
                invoiceItemMap.put("taxAmount", invoiceItem.getTaxAmount());
                fpkjXmxxParams.add(invoiceItemMap);
            }
            String xml = InvoiceXmlUtil.requestXml(paramMap, fpkjXmxxParams);
            result = HttpRequestUtil.requestCall(fwpt_url, xml);

            LOGGER.info(String.format("电子发票服务平台response xml = %s", result));
            Map<String, Object> resultMap = InvoiceXmlUtil.responseXmlHandler(result);
            // 设置返回值到ftcspOrdReceiptVO
            invoiceExtend = mapToOrdInvoiceExtend(resultMap);
            ordInvoice.setErrorCode(invoiceExtend.getReturnCode());
            ordInvoice.setErrorMessage(invoiceExtend.getReturnMsg());
        } catch (DocumentException e) {
            String message = String.format("请求电子发票服务平台开票异常DocumentException，信息如下： %s", e.getMessage());
            LOGGER.error(message);
            throw new FtcspRuntimeException("03010004", (Object) (message));
        } catch (RuntimeException e) {
            String message = String.format("请求电子发票服务平台开票异常RuntimeException，信息如下： %s", e.getMessage());
            LOGGER.error(message);
            throw new FtcspRuntimeException("03010004", (Object) (message));
        }
        if (InvoiceConstant.INVOICE_SERVICE_PLATFORM_RESPONSE_CODE.equals(invoiceExtend.getReturnCode())) {// 如果第三方开票成功，则新增内容至发票信息表
            invoiceExtend.setInvoiceAmount(new BigDecimal(MoneyUtil.getYuanFromFen3(ordInvoice.getTotalAmountTax())));// 单位：元（早期设置的单位是元，故需要转换）
            invoiceExtend.setInvoiceExtendId(idGenerator.getLong());
            invoiceExtend.setBillingType(ordInvoice.getBillingType());
            invoiceExtend.setInvalid(InvoiceConstant.INTERFACE_IS_RED.NO);
            invoiceExtend.setInvoiceId(ordInvoice.getInvoiceId());
            invoiceExtend.setCreateUser(ordInvoice.getCreateUser());
            ordInvoiceExtendMapper.insertSelective(invoiceExtend);
            ordInvoice.setCallStatus(InvoiceConstant.INTERFACE_CALL_STATUS.YI_DIAO_YONG);
            this.updateCallStatusAndNumber(ordInvoice, generateType);// 更新发票流水表调用状态和次数

            if (InvoiceConstant.INTERFACE_BILLING_TYPE.RED.equals(ordInvoice.getBillingType())) {// 如果开票类型是红红字发票，则更新原发票号码的红冲标记
                ordInvoiceExtMapper.updateInvoiceExtendInValid(ordInvoice.getOriginalInvoiceCode(), ordInvoice.getOriginalInvoiceNo(),
                        InvoiceConstant.INTERFACE_IS_RED.YES);
            }

            //更新账单、订单发票状态
            updateBillOrOrderInvoiceStatus(ordInvoice.getBillingType(), InvoiceConstant.INTERFACE_CALL_STATUS.YI_DIAO_YONG,
                    ordInvoice.getInvoiceId());

            if (InvoiceConstant.INTERFACE_BILLING_TYPE.BLUE.equals(ordInvoice.getBillingType())) {// 红冲的时候不发送消息
                /*
                 * invoiceMessageService.sendMessage(ftcspOrdOrder, ordInvoice.getBuyerName(), ftcspOrdPackVO, invoiceExtend,
                 * ordInvoice.getBuyerMobile());// 调用消息接口；如果消息调用失败，则由定时器作业调度
                 */ // 自动发送消息
                if (ordInvoice.getBuyerMobile() != null && StrUtil.checkMobile(ordInvoice.getBuyerMobile())) {
                    // .......
                } else {
                    LOGGER.warn(String.format("发票代码：%s，发票号码：%s,发票流水ID:%s，手机号：%s; 对应的手机号错误，不能发送消息！", invoiceExtend.getInvoiceCode(),
                            invoiceExtend.getInvoiceNumber(), ordInvoice.getInvoiceId(), ordInvoice.getBuyerMobile()));
                }
            }
        } else {
            ordInvoice.setCallStatus(InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL);
            if (ordInvoice.getCallNumber() > 3) {
                this.updateBillOrOrderInvoiceStatus(ordInvoice.getBillingType(), InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL,
                        ordInvoice.getInvoiceId());
            } else {
                //不超过3次的话直接改成开票中，锁定不允许手工开票
                this.updateBillOrOrderInvoiceStatus(ordInvoice.getBillingType(), InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG,
                        ordInvoice.getInvoiceId());
            }
            this.updateCallStatusAndNumber(ordInvoice, generateType);
        }
        return invoiceExtend;
    }

    private OrdInvoice buildOrdInvoice(IssueElectronicInvoiceDTO issueElectronicInvoiceDTO, OrdOrder order) {
        OrdInvoice ordInvoice = new OrdInvoice();
        ordInvoice.setInvoiceId(idGenerator.getLong());
        ordInvoice.setInvoiceFormality(InvoiceConstant.INTERFACE_INVOICE_FORMALITY.DIAN_ZI);
        ordInvoice.setInvoiceType(InvoiceConstant.INTERFACE_INVOICE_TYPE.PU_TONG);
        ordInvoice.setBillingType(issueElectronicInvoiceDTO.getBillingType());
        ordInvoice.setOriginalInvoiceCode(issueElectronicInvoiceDTO.getOriginalInvoiceCode());
        ordInvoice.setOriginalInvoiceNo(issueElectronicInvoiceDTO.getOriginalInvoiceNo());
        ordInvoice.setCallStatus(InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG);
        ordInvoice.setCallNumber(0);
        ordInvoice.setSerialNumber(
                NumberRandom.getSystemCodeRecursion(this.idUtil.createSequenceId(InvoiceConstant.INVOICE_SERIAL_NUMBER_SEQUENCE_KEY)));// 由于第三方系统限制业务流水号首位数字不能为0，故此处的编号采用循环调用方式获取系统编码
        ordInvoice
                .setCreateUser(StringUtils.isBlank(issueElectronicInvoiceDTO.getUserId()) ? "1" : issueElectronicInvoiceDTO.getUserId());
        //获取购买方信息
        ordInvoice = getBuyerInfo(ordInvoice, issueElectronicInvoiceDTO, order);
        //查询销售方开票信息
        Long skuId = order.getSkuId();
        if (skuId == null) {
            //取子订单skuId
            List<OrdOrderDTO> orderList = ordOrderExtMapper.selectByParentId(order.getOrderId(), order.getOrderTime());
            if (orderList.isEmpty()) {
                throw new FtcspRuntimeException("03010004", (Object) "订单skuId为空，获取商品销售方失败！");
            }
            skuId = orderList.get(0).getSkuId();
        }
        ordInvoice = getSellerInfo(ordInvoice, skuId);
        return ordInvoice;
    }

    /**
     * 构造发票扩展实体类
     * @param resultMap
     * @return OrdInvoiceExtend
     */
    private OrdInvoiceExtend mapToOrdInvoiceExtend(Map<String, Object> resultMap) {
        OrdInvoiceExtend invoiceExtend = new OrdInvoiceExtend();
        invoiceExtend.setReturnCode(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("returnCode")));
        invoiceExtend.setReturnMsg(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("returnMsg")));

        if (InvoiceConstant.INVOICE_SERVICE_PLATFORM_RESPONSE_CODE.equals(invoiceExtend.getReturnCode())) {
            //开票成功
            invoiceExtend.setInvoiceFlowingCode(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("invoiceFlowingCode")));
            invoiceExtend.setInvoiceCode(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("receiptCode")));
            invoiceExtend.setInvoiceNumber(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("receiptNumber")));
            invoiceExtend.setDeviceCode(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("deviceCode")));
            invoiceExtend.setInvoceCiphertext(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("invoiceCiphertext")));
            invoiceExtend.setValidateCode(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("validateCode")));
            invoiceExtend.setQrcode(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("qrcode")));
            invoiceExtend.setXzm(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("xzm")));
            invoiceExtend.setRemark(InvoiceXmlUtil.nullConvertEmpty(resultMap.get("remark")));
            try {
                Date billingDate = LocalDateUtil.getDateByStr(LocalDateUtil.YMDHMS, resultMap.get("billingDate").toString());
                invoiceExtend.setBillingTime(billingDate);
            } catch (ParseException e) {
                String message = String.format("开票日期转换异常：%s", e.getMessage());
                LOGGER.error(message);
                throw new FtcspRuntimeException("03010004", (Object) (message));
            }
        }
        return invoiceExtend;
    }

    /**
     * 更新调用状态和调用次数
     * @param ordInvoice
     * @param generateType void
     */
    private void updateCallStatusAndNumber(OrdInvoice ordInvoice, String generateType) {
        OrdInvoice upInvoice = new OrdInvoice();
        upInvoice.setInvoiceId(ordInvoice.getInvoiceId());
        upInvoice.setCallStatus(ordInvoice.getCallStatus());
        upInvoice.setErrorCode(ordInvoice.getErrorCode());
        upInvoice.setErrorMessage(ordInvoice.getErrorMessage());
        upInvoice.setSerialNumber(InvoiceXmlUtil.nullConvertEmpty(ordInvoice.getSerialNumber()));
        upInvoice.setBuyerName(ordInvoice.getBuyerName());//开票失败时再开票有可能要更新纳税人名称
        upInvoice.setBuyerTaxpayerNo(ordInvoice.getBuyerTaxpayerNo());//开票失败时再开票有可能要更新纳税人识别号
        if (InvoiceConstant.INTERFACE_GENERATE_TYPE.MANUAL.equals(generateType)) {
            //手工开票的，次数改成1,调度还可以再跑4次
            if (InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL.equals(ordInvoice.getCallStatus())) {
                upInvoice.setCallStatus(InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG);
            }
            upInvoice.setCallNumber(1);
        } else {
            if (InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL.equals(ordInvoice.getCallStatus())) {
                //调用5次失败才更改成失败状态
                if (ordInvoice.getCallNumber() > 3) {
                    upInvoice.setCallStatus(InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL);
                    //更新订单，账单开票失败状态
                    updateBillOrOrderInvoiceStatus(ordInvoice.getBillingType(), InvoiceConstant.INTERFACE_CALL_STATUS.DIAO_YONG_FAIL,
                            ordInvoice.getInvoiceId());
                } else {
                    upInvoice.setCallStatus(InvoiceConstant.INTERFACE_CALL_STATUS.WEI_DIAO_YONG);
                }
            }
            upInvoice.setCallNumber(ordInvoice.getCallNumber() + 1);
        }
        //更新调用状态和次数
        ordInvoiceMapper.updateByPrimaryKeySelective(upInvoice);
    }

    /**
     * 返回发票查看地址
     * @param invoiceExtend
     * @return String
     */
    private String getInvoiceUrl(OrdInvoiceExtend invoiceExtend) {
        String fpDownLoadUrl = dictionaryApi.getDynamicFeildByDicCodeAndItemCode(InvoiceConstant.INVOICE_CONFIG, "fpDownloadUrl",
                "itemValue");
        String xzm = invoiceExtend.getXzm();
        if (StringUtils.isBlank(fpDownLoadUrl)) {
            LOGGER.info("字典管理值缺失=====dicCode为:" + InvoiceConstant.INVOICE_CONFIG + " itemCode为：" + "fpDownloadUrl");
            throw new FtcspRuntimeException("03010004", (Object) "请在字典管理配置发票服务查看、下载发票地址！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(fpDownLoadUrl);
        sb.append(xzm);
        sb.append("?type=preview");
        return sb.toString();
    }

    /**
     * 根据含税单价计算出不含税的单价
     */
    private Double taxComputer(Double hanshuiUnitPrice, Double taxRate) {
        return hanshuiUnitPrice / (1 + taxRate);
    }

    /**
     * 校验原始竞价与价税合计是否相等
     * @param oriAmount
     * @param totalAmount
     * @return boolean
     */
    private boolean checkOrdIvoiceJSHJ(BigDecimal oriAmount, Long totalAmount) {
        if (oriAmount == null) {
            return false;
        }
        Long targetAmount = new Double(oriAmount.doubleValue() * 100).longValue();
        if (targetAmount.equals(totalAmount)) {
            return true;
        }
        return false;
    }

    private Map<String, List<OrdInvoiceItem>> putInvoiceItem(Map<String, List<OrdInvoiceItem>> srcMap, OrdInvoiceItem invoiceItem) {
        List<OrdInvoiceItem> srcItemList = srcMap.get(invoiceItem.getItemCode());
        if (srcItemList == null) {
            srcItemList = new ArrayList<>();
        }
        srcItemList.add(invoiceItem);
        srcMap.put(invoiceItem.getItemCode(), srcItemList);
        return srcMap;
    }

    /**
     * 红冲带折扣的蓝字发票示例： 明细被折扣-折扣 明细金额 4861.54-50.44=4811.1=4811.10 明细税额 826.46-8.56=817.9=817.90 然后取反，得到负数
     * @param srcMap
     * @param invoiceItem
     * @return OrdInvoiceItem
     */
    private OrdInvoiceItem computeHcAmountAndTax(Map<String, List<OrdInvoiceItem>> srcMap, OrdInvoiceItem invoiceItem) {
        List<OrdInvoiceItem> srcItemList = srcMap.get(invoiceItem.getItemCode());
        if (srcItemList == null) {
            return invoiceItem;
        }
        invoiceItem.setLineNature(InvoiceConstant.INTERFACE_LINE_NATURE.ZHENG_CHANG);
        Long amount = invoiceItem.getAmount();
        Long taxAmount = invoiceItem.getTaxAmount();
        for (OrdInvoiceItem ordInvoiceItem : srcItemList) {
            amount -= ordInvoiceItem.getAmount();
            taxAmount -= ordInvoiceItem.getTaxAmount();
        }
        invoiceItem.setPrice(new BigDecimal(amount.doubleValue() / invoiceItem.getQty()));
        invoiceItem.setAmount(amount);
        invoiceItem.setTaxAmount(taxAmount);
        return invoiceItem;
    }
}
