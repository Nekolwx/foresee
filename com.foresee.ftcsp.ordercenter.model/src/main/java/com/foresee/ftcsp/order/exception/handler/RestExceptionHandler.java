/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.exception.handler;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.order.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * 拦截参数校验异常。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年8月10日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
//@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    private Object illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult br = e.getBindingResult();
        FieldError error = br.getFieldError();
        String message = error.getDefaultMessage();
        return new RestResponse(ErrorCode.INVALID_PARAM.getErrorCode(), message);
    }*/
}
