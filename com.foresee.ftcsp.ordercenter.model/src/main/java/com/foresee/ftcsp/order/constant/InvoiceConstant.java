/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.constant;

/**
 * <pre>
 * 发票相关表的常量值
 * </pre>
 *
 * @author yangchengjun@foresee.com.cn
 * @date 2017年11月13日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public interface InvoiceConstant {
    /**
     * 字典管理的发票配置dicCode
     */
    public static final String  INVOICE_CONFIG       = "invoice_config";
    
    public static final String INVOICE_SERVICE_PLATFORM_RESPONSE_CODE = "0000";
    
    public static final String  INVOICE_SERIAL_NUMBER_SEQUENCE_KEY = "invoice_serial_number_sequence"; 

    public static class INTERFACE_INVOICE_FORMALITY {

        public static final Integer DIAN_ZI = 0; // 电子发票

        public static final Integer DING_E  = 1; // 定额发票

        public static final Integer JI_DA   = 2; // 机打发票


        public static String getText(String code) {
            if ("0".equals(code)) return "电子发票";
            if ("1".equals(code)) return "定额发票";
            if ("2".equals(code)) return "机打发票";
            return "";
        }
    }

    public static class INTERFACE_INVOICE_TYPE {

        public static final Integer PU_TONG    = 0; // 增值税普通发票

        public static final Integer ZHUAN_YONG = 1; // 增值税专用发票


        public static String getText(String code) {
            if ("0".equals(code)) return "增值税普通发票";
            if ("1".equals(code)) return "增值税专用发票";
            return "";
        }
    }

    public static class INTERFACE_BILLING_TYPE {

        public static final Integer BLUE = 0; // 蓝字发票

        public static final Integer RED  = 1; // 红字发票


        public static String getText(String code) {
            if ("0".equals(code)) return "蓝字发票";
            if ("1".equals(code)) return "红字发票";
            return "";
        }
    }

    public static class INTERFACE_CALL_STATUS {

        public static final Integer BU_DIAO_YONG        = 0; // 不调用

        public static final Integer WEI_DIAO_YONG       = 1; // 未调用

        public static final Integer YI_DIAO_YONG        = 2; // 已调用

        public static final Integer DIAO_YONG_FAIL      = 3; // 调用失败

        public static final Integer CHONG_XIN_DIAO_YONG = 4; // 调用失败后重新调用成功


        public static String getText(String code) {
            if ("0".equals(code)) return "不调用";
            if ("1".equals(code)) return "未调用";
            if ("2".equals(code)) return "已开票";
            if ("3".equals(code)) return "调用失败";
            if ("4".equals(code)) return "调用失败后重新调用成功";
            return "";
        }
    }

    public static class INTERFACE_LINE_NATURE {

        public static final Integer ZHENG_CHANG = 0; // 正常行

        public static final Integer ZHE_KOU     = 1; // 折扣行

        public static final Integer BEI_ZHE_KOU = 2; // 被折扣行


        public static String getText(String code) {
            if ("0".equals(code)) return "正常行";
            if ("1".equals(code)) return "折扣行";
            if ("2".equals(code)) return "被折扣行";
            return "";
        }
    }

    public static class INTERFACE_GENERATE_TYPE {

        public static final String MANUAL = "0"; // 手动(即时开票)

        public static final String AUTO   = "1"; // 自动(消息队列调度开票)

        public static final String BATCH  = "2"; // 批量


        public static String getText(String code) {
            if ("0".equals(code)) return "手动";
            if ("1".equals(code)) return "自动";
            if ("2".equals(code)) return "批量";
            return "";
        }
    }
    
    public static class INTERFACE_SMS_SEND_FLAG {

        public static final String WEI_FA_SONG        = "0"; // 未发送

        public static final String FA_SONG_ZHONG      = "1"; // 发送中

        public static final String FA_SONG_CHENG_GONG = "2"; // 发送成功

        public static final String FA_SONG_FAIL       = "3"; // 发送失败


        public static String getText(String code) {
            if ("0".equals(code)) return "未发送";
            if ("1".equals(code)) return "发送中";
            if ("2".equals(code)) return "发送成功";
            if ("3".equals(code)) return "发送失败";
            return "";
        }
    }

    public static class INTERFACE_IS_RED {

        public static final Integer NO  = 0; // 未被红冲

        public static final Integer YES = 1; // 已被红冲


        public static String getText(String code) {
            if ("0".equals(code)) return "否";
            if ("1".equals(code)) return "是";
            return "";
        }
    }
}
