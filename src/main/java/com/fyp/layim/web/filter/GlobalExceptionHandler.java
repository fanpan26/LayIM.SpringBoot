package com.fyp.layim.web.filter;

import com.fyp.layim.domain.result.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public JsonResult jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return JsonResult.fail("程序异常："+e.getMessage());
    }
}
