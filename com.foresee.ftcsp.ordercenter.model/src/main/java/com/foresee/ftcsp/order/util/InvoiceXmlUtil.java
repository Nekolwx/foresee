/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.util;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.foresee.ftcsp.order.constant.InvoiceConstant;
import com.foresee.ftcsp.util.MoneyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.util.StringUtils;

/**
 * <pre>
 * xml工具类（针对2.0的发票开具）
 * </pre>
 *
 * @author yangchengjun@foresee.com.cn
 * @date 2017年11月7日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class InvoiceXmlUtil {

    private static final Log    log                                    = LogFactory.getLog(InvoiceXmlUtil.class);

    private static final String DEFAULT_ENCODING                       = "UTF-8";

    private static final Integer RED                                    = 1;

    private static final String INVOICE_SERVICE_PLATFORM_RESPONSE_CODE = "0000";


    public static String requestXml(Map<String, Object> ordInvoiceParam, List<Map<String, Object>> fpkjXmxxParams) {

        // 创建XML文档树  
        Document document = DocumentHelper.createDocument();
        // 创建根节点items  
        Element rootElement = document.addElement("root");
        rootElement.addAttribute("version", "1.0");
        Element headElement = rootElement.addElement("head");
        Element serviceElement = headElement.addElement("service");
        Element handlerElement = serviceElement.addElement("handler");
        handlerElement.setText("fpkj");
        Element nsrsbhElement = serviceElement.addElement("nsrsbh");
        nsrsbhElement.setText(nullConvertEmpty(ordInvoiceParam.get("sellerTaxpayerNo")));
        Element securityElement = serviceElement.addElement("security");
        Element principalElement = securityElement.addElement("principal");
        principalElement.setText(nullConvertEmpty(ordInvoiceParam.get("principal")));
        Element credentialsElement = securityElement.addElement("credentials");
        credentialsElement.addCDATA(nullConvertEmpty(ordInvoiceParam.get("credentials")));
        Element zipElement = serviceElement.addElement("zip");
        zipElement.setText("0"); // body内容是否压缩 0不压缩 1压缩
        Element encryptElement = serviceElement.addElement("encrypt");
        encryptElement.setText("0"); // body内容是否加密 0不加密,1加密,密码用数字证书密码前8位,不足补0
        // 创建根节点items  
        Element bodyElement = rootElement.addElement("body");
        Document documentBody = DocumentHelper.createDocument();
        // 创建根节点items  
        Element businessElement = documentBody.addElement("business");
        businessElement.addAttribute("id", "FPKJ");
        businessElement.addAttribute("comment", "发票开具");
        Element commonFpkjRequestElement = businessElement.addElement("commonFpkjRequest");
        Element commonFpkjFptElement = commonFpkjRequestElement.addElement("commonFpkjFpt");
        Element ywlshElement = commonFpkjFptElement.addElement("ywlsh");
        ywlshElement.setText(nullConvertEmpty(ordInvoiceParam.get("code"))); // 业务流水号(唯一)
        Element fpqqlshElement = commonFpkjFptElement.addElement("fpqqlsh");
        fpqqlshElement.setText(nullConvertEmpty(ordInvoiceParam.get("code"))); // 发票请求流水号(唯一)
        Element kplxElement = commonFpkjFptElement.addElement("kplx");
        kplxElement.setText(nullConvertEmpty(ordInvoiceParam.get("billingType"))); //开票类型
        Element xsfNsrsbhElement = commonFpkjFptElement.addElement("xsfNsrsbh");
        xsfNsrsbhElement.setText(nullConvertEmpty(ordInvoiceParam.get("sellerTaxpayerNo")));// 销售方纳税人识别号
        Element xsfMcElement = commonFpkjFptElement.addElement("xsfMc");
        xsfMcElement.setText(nullConvertEmpty(ordInvoiceParam.get("sellerName"))); // 销售方名称
        Element xsfDzdhElement = commonFpkjFptElement.addElement("xsfDzdh");
        xsfDzdhElement.setText(nullConvertEmpty(ordInvoiceParam.get("sellerAddressPhone")));// 销售方地址、电话
        Element xsfYhzhElement = commonFpkjFptElement.addElement("xsfYhzh");
        xsfYhzhElement.setText(nullConvertEmpty(ordInvoiceParam.get("sellerBankCard"))); // 销售方银行账号
        Element gmfNsrsbhElement = commonFpkjFptElement.addElement("gmfNsrsbh"); // 购买方纳税人识别号
        gmfNsrsbhElement.setText(nullConvertEmpty(ordInvoiceParam.get("buyerTaxpayerNo")));
        Element gmfMcElement = commonFpkjFptElement.addElement("gmfMc");
        gmfMcElement.setText(nullConvertEmpty(ordInvoiceParam.get("buyerName"))); // 购买方名称
        Element gmfDzdhElement = commonFpkjFptElement.addElement("gmfDzdh"); // 购买方地址、电话
        gmfDzdhElement.setText(nullConvertEmpty(ordInvoiceParam.get("buyerAddressPhone")));
        Element gmfYhzhElement = commonFpkjFptElement.addElement("gmfYhzh"); // 购买方银行账号
        gmfYhzhElement.setText(nullConvertEmpty(ordInvoiceParam.get("buyerBankCard")));
        Element kprElement = commonFpkjFptElement.addElement("kpr");
        kprElement.setText(nullConvertEmpty(ordInvoiceParam.get("drawer"))); // 开票人
        Element skrElement = commonFpkjFptElement.addElement("skr");
        skrElement.setText(nullConvertEmpty(ordInvoiceParam.get("payee"))); // 收款人
        Element fhrElement = commonFpkjFptElement.addElement("fhr");
        fhrElement.setText(nullConvertEmpty(ordInvoiceParam.get("reviewer"))); // 复核人
        Element yfpDmElement = commonFpkjFptElement.addElement("yfpDm");
        Element yfpHmElement = commonFpkjFptElement.addElement("yfpHm");
        if (RED.equals((Integer)ordInvoiceParam.get("billingType"))) {
            yfpDmElement.setText(nullConvertEmpty(ordInvoiceParam.get("originalInvoiceCode")));// 原发票代码
            yfpHmElement.setText(nullConvertEmpty(ordInvoiceParam.get("originalInvoiceNo")));// 原发票号码
        }
        DecimalFormat formatter = new DecimalFormat("0.00");
        Element jshjElement = commonFpkjFptElement.addElement("jshj");
        jshjElement.setText(formatter.format(Double.valueOf(MoneyUtil.getYuanFromFen3((Long) ordInvoiceParam.get("totalAmountTax")))));// 价税合计
        Element hjjeElement = commonFpkjFptElement.addElement("hjje");
        hjjeElement.setText(formatter.format(Double.valueOf(MoneyUtil.getYuanFromFen3((Long) ordInvoiceParam.get("totalAmount")))));// 合计金额
        Element hjseElement = commonFpkjFptElement.addElement("hjse");
        hjseElement.setText(formatter.format(Double.valueOf(MoneyUtil.getYuanFromFen3((Long) ordInvoiceParam.get("totalTax")))));// 合计税额
        Element bzElement = commonFpkjFptElement.addElement("bz");
        if (RED.equals((Integer)ordInvoiceParam.get("billingType"))) {
            bzElement.setText(String.format("原发票代码：%s    原发票号码：%s", ordInvoiceParam.get("originalInvoiceCode"),
                    ordInvoiceParam.get("originalInvoiceNo")));
        }
        Element gmfEmailElement = commonFpkjFptElement.addElement("gmfEmail");
        gmfEmailElement.setText(nullConvertEmpty(ordInvoiceParam.get("buyerEmail"))); // 购买方电子邮箱地址
        Element gmfSjElement = commonFpkjFptElement.addElement("gmfSj");
        gmfSjElement.setText(nullConvertEmpty(ordInvoiceParam.get("buyerMobile"))); // 购买方手机号码
        Element commonFpkjXmxxsElement = commonFpkjRequestElement.addElement("commonFpkjXmxxs");

        //适应多项目开票
        for (Map<String, Object> xmxxMap : fpkjXmxxParams) {
            Element commonFpkjXmxxElement = commonFpkjXmxxsElement.addElement("commonFpkjXmxx");
            Element fphxzElement = commonFpkjXmxxElement.addElement("fphxz");
            fphxzElement.setText(nullConvertEmpty(xmxxMap.get("lineNature"))); // 发票行性质
            Element xmmcElement = commonFpkjXmxxElement.addElement("xmmc");
            xmmcElement.setText(nullConvertEmpty(xmxxMap.get("itemName"))); // 项目名称
            
            if (!InvoiceConstant.INTERFACE_LINE_NATURE.ZHE_KOU.equals((Integer)xmxxMap.get("lineNature"))) {
                //折扣行无规格型号、计量单位、项目数量、项目单价
                Element ggxhElement = commonFpkjXmxxElement.addElement("ggxh"); // 规格型号
                ggxhElement.setText(nullConvertEmpty(xmxxMap.get("spec")));
                Element dwElement = commonFpkjXmxxElement.addElement("dw"); // 计量单位
                dwElement.setText(nullConvertEmpty(xmxxMap.get("unit")));
                formatter = new DecimalFormat("0");
                Element xmslElement = commonFpkjXmxxElement.addElement("xmsl");
                xmslElement.setText(formatter.format(xmxxMap.get("qty"))); // 项目数量
                formatter = new DecimalFormat("0.00000000");
                Element xmdjElement = commonFpkjXmxxElement.addElement("xmdj");
                xmdjElement.setText(formatter.format(((BigDecimal) xmxxMap.get("price")).doubleValue() / 100)); // 项目单价
            }
            
            formatter = new DecimalFormat("0.00");
            Element xmjeElement = commonFpkjXmxxElement.addElement("xmje");
            xmjeElement.setText(formatter.format(Double.valueOf(MoneyUtil.getYuanFromFen3((Long) xmxxMap.get("amount")))));// 项目金额
            Element slElement = commonFpkjXmxxElement.addElement("sl");
            slElement.setText(formatter.format(xmxxMap.get("taxRate"))); // 税率
            Element seElement = commonFpkjXmxxElement.addElement("se");
            seElement.setText(formatter.format(Double.valueOf(MoneyUtil.getYuanFromFen3((Long) xmxxMap.get("taxAmount")))));// 税额
            Element spbmElement = commonFpkjXmxxElement.addElement("spbm");
            spbmElement.setText(nullConvertEmpty(xmxxMap.get("itemCode"))); // 商品编码，软件服务
        }

        //bodyElement.addCDATA(documentBody.asXML());//默认是转义且未格式化的
        bodyElement.addCDATA(formatXml(documentBody, DEFAULT_ENCODING, true)); //重点：CDATA内包含的如果是普通内容则不需要转义(false)；如果是XML字符串文件内容，则必须转义(true)，因为接收方需要按照XML解析方式解析其内部相关节点，不转义的话接收方处理则会报错
        log.info(formatXml(document, DEFAULT_ENCODING, true));
        //return document.asXML();//默认是转义且未格式化的
        return formatXml(document, DEFAULT_ENCODING, true);
    }

    /**
     * 解析XML【对响应的XML结果进行 处理，组装为瞬时态发票信息VO对象】
     *
     * @param resultXml 响应XML结果
     * @return 瞬时态发票信息VO对象
     * @throws DocumentException 将文件或者字符串转换为XML错误或者转换后的XML节点不存在均会抛出异常
     */
    public static Map<String, Object> responseXmlHandler(String resultXml) throws DocumentException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Document document = DocumentHelper.parseText(resultXml);//读取XML字符串
        log.info(String.format("响应值转换后的XML为：%s", document.asXML()));
        Element root = document.getRootElement(); //根节点
        Element head = root.element("head"); // 对方约定的二级节点
        Element service = head.element("service"); // 对方约定的三级节点
        Element replyCode = service.element("replyCode"); // 对方约定的四级节点
        Element replyMsg = service.element("replyMsg"); // 对方约定的四级节点
        Element body = root.element("body"); // 对方约定的二级节点
        if ("0".equals(replyCode.getText())) {
            Document documentBody = DocumentHelper.parseText(body.getText()); //对方约定的body里的内容是用CDATA包含的XML字符串，故需要将其内部的内容组装为XML文档方式；重点：body.getText里面的内容不应该包含<或&或>等特殊字符,主要因为此body.getText里的内容不是普通字符串内容，而是一个XML字符串，需要转换为XML文档，否则DOM4J不能解析为XML文档；如果body里的内容是普通字符串内容，则可包含<或&或>等特殊字符
            Element business = documentBody.getRootElement(); // 对方约定的body内的一级节点
            Element commonFpkjResponse = business.element("commonFpkjResponse"); // 对方约定的body内的二级节点
            Element returnCode = commonFpkjResponse.element("returnCode"); // 返回代码(对方约定的body内的三级节点)
            if (INVOICE_SERVICE_PLATFORM_RESPONSE_CODE.equals(returnCode.getText())) {
                log.info(String.format("开始对解析报文赋值"));
                Element returnMsg = commonFpkjResponse.element("returnMsg"); // 响应消息
                Element fpqqlsh = commonFpkjResponse.element("fpqqlsh"); // 发票请求流水号
                Element jqbh = commonFpkjResponse.element("jqbh"); // 税控设备编号
                Element fpDm = commonFpkjResponse.element("fpDm"); // 发票代码
                Element fpHm = commonFpkjResponse.element("fpHm"); // 发票号码
                Element kprq = commonFpkjResponse.element("kprq"); // 开票日期
                Element fpMw = commonFpkjResponse.element("fpMw"); // 发票密文
                Element jym = commonFpkjResponse.element("jym");// 发票校验码
                Element ewm = commonFpkjResponse.element("ewm");// 二维码
                Element bz = commonFpkjResponse.element("bz");// 备注
                Element xzm = commonFpkjResponse.element("xzm");// 下载码，用来查看下载发票

                returnMap.put("invoiceFlowingCode", fpqqlsh.getText());
                returnMap.put("receiptCode", fpDm.getText());
                returnMap.put("receiptNumber", fpHm.getText());
                returnMap.put("deviceCode", jqbh.getText());
                returnMap.put("invoiceCiphertext", fpMw.getText());
                returnMap.put("validateCode", jym.getText());
                returnMap.put("qrcode", ewm.getText());
                returnMap.put("billingDate", kprq.getText());
                returnMap.put("remark", bz.getText());
                returnMap.put("xzm", xzm.getText());
                returnMap.put("returnCode", returnCode.getText());
                returnMap.put("returnMsg", returnMsg.getText());
            } else {
                Element returnMsg = commonFpkjResponse.element("returnMsg");// 响应消息
                String message = returnMsg == null ? "开具发票失败，发票无返回错误信息" : returnMsg.getText();
                returnMap.put("returnCode", returnCode.getText());
                returnMap.put("returnMsg", message.length()>1000?message.substring(0, 1000):message);
                log.warn("============完整的开票失败错误信息"+returnMsg.getText());
                //throw new RuntimeException(message);
            }
        } else {
            if (body != null) {
                Document documentBody = DocumentHelper.parseText(body.getText());//对方约定的body里的内容是用CDATA包含的XML字符串，故需要将其内部的内容组装为XML文档方式；重点：body.getText里面的内容不应该包含<或&或>等特殊字符,主要因为此body.getText里的内容不是普通字符串内容，而是一个XML字符串，需要转换为XML文档，否则DOM4J不能解析为XML文档；如果body里的内容是普通字符串内容，则可包含<或&或>等特殊字符
                Element business = documentBody.getRootElement();// 对方约定的body内的一级节点
                Element commonFpkjResponse = business.element("commonFpkjResponse");// 对方约定的body内的二级节点
                Element returnCode = commonFpkjResponse.element("returnCode");// 返回代码(对方约定的body内的三级节点)
                Element returnMsg = commonFpkjResponse.element("returnMsg");// 返回代码(对方约定的body内的三级节点)
                if (!INVOICE_SERVICE_PLATFORM_RESPONSE_CODE.equals(returnCode.getText())) {
                    String message = returnMsg == null ? "开具发票失败，发票无返回错误信息" : returnMsg.getText();
                    returnMap.put("returnCode", returnCode.getText());
                    returnMap.put("returnMsg",message.length()>1000?message.substring(0, 1000):message);
                    log.warn("============完整的开票失败错误信息"+message);
                   // throw new RuntimeException(message);
                }
            } else {
                String message = nullConvertEmpty(replyMsg.getText());
                returnMap.put("returnCode", replyCode.getText());
                returnMap.put("returnMsg", message.length()>1000?message.substring(0, 1000):message);
                log.warn("============完整的开票失败错误信息"+message);
               // throw new RuntimeException(message);
            }
        }
        return returnMap;
        //对应如下为响应的XML报文格式
        //        <?xml version="1.0" encoding="UTF-8"?>
        //        <root version="1.0">
        //            <!-- 报文头节点，主要描述接口标识、接口安全、用户安全等信息 -->
        //            <head>
        //                <service>
        //                    <nsrsbh>纳税人识别号</nsrsbh>
        //                    <zip>body内容是否压缩 0不压缩 1压缩</zip>
        //                    <encrypt>body内容是否加密 0不加密 1加密, 密码用数字证书密码前8位,不足补0</encrypt>
        //                    <!-- 响应代码，0表示接口调用成功，1或其他值表示接口调用失败 -->
        //                    <replyCode>0</replyCode>
        //                    <!-- 响应信息，调用成功时可忽略，调用失败时为失败描述信息 -->
        //                    <replyMsg>响应信息</replyMsg>
        //                </service>
        //            </head>
        //            <!-- 报文体节点，用于放接口调用参数与数据-->
        //            <body>
        //                  <![CDATA[
        //                        <?xml version="1.0" encoding="UTF-8"?>
        //                        <business id="FPKJ" comment="发票开具">
        //                            <commonFpkjResponse>
        //                                <ywlsh>10000002</ywlsh>
        //                                <jqbh>661559402123</jqbh>
        //                                <fpDm>044001500111</fpDm>
        //                                <fpHm>62536617</fpHm>
        //                                <kprq>20160413234856</kprq>
        //                                <fpMw>94/2-81+9&gt;-/58427*&lt;*63/91** 05&gt;*3&gt;&gt;-48/&gt;555*20+8344270+ 5&lt;0--0659&lt;&gt;-6*-05*027/&gt;427&gt; 908827&lt;09*&gt;9*08&lt;471420+&lt;+01</fpMw>
        //                                <jym>12345678901234567890</jym>
        //                                <ewm>2222222222222222222222222</ewm>
        //                                <bz>备注</bz>
        //                                <returnCode>0000</returnCode>
        //                                <returnMsg>发票查询成功</returnMsg>
        //                            </commonFpkjResponse>
        //                        </business>
        //                  ]]>
        //            </body>
        //        </root>
    }

    /**
     * 格式化XML文档
     *
     * @param document xml文档
     * @param charset 字符串的编码
     * @param istrans 是否对属性和元素值进行转义
     * @return 格式化后XML字符串
     */
    private static String formatXml(Document document, String charset, boolean istrans) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        format.setIndentSize(2);
        format.setNewlines(true);
        format.setTrimText(false);
        format.setPadText(true);
        //以上4行用于处理base64图片编码以后放入xml时的回车变空格问题      
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw, format);
        xw.setEscapeText(istrans);
        try {
            xw.write(document);
            xw.flush();
            xw.close();
        } catch (IOException e) {
            String message = String.format("格式化XML文档发生异常，请检查！异常消息如下：% s", e.getMessage());
            log.info(message);
            throw new RuntimeException(message);
        }
        return sw.toString();
    }

    public static String nullConvertEmpty(Object object) {
        if (StringUtils.isEmpty(object)) {
            return "";
        } else {
            return object.toString();
        }
    }
}
