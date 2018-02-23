/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.enums;

/**
 * <pre>
 * 错误码
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *             修改后版本:     修改人：  修改日期:     修改内容:
 *                   </pre>
 * @date 2017年03月23日
 */

public enum ErrorCode {

    SUCCESS("0", "SUCCESS"),
    INVALID_PARAM("7201", "参数格式非法"),
    ;

    private final String errorCode;

    ErrorCode(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        
        return errorCode;
    }

}

