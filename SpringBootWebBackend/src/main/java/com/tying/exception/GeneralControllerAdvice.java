package com.tying.exception;

import com.tying.domain.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tying
 * @version 1.0
 */
//@ControllerAdvice
public class GeneralControllerAdvice {

    public static final Integer UNCLOGGED_IN = 300;

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseResult handleException(Exception e) {
        // 获取异常信息，存放到 ResponseResult 的 msg 属性
        String message = e.getMessage();
        ResponseResult responseResult = new ResponseResult(UNCLOGGED_IN, message);
        return responseResult;
    }
}
