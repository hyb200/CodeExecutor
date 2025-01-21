package com.abin.executor.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abin.executor.domain.BaseResponse;
import com.abin.executor.domain.enums.ResponseType;
import com.abin.executor.exception.BusinessException;
import com.abin.executor.uitls.ResponseUtils;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse<?> globalExceptionHandler(BusinessException e) {
        Integer errorType = e.getErrorType();
        return ResponseUtils.error(ResponseType.of(errorType));
    }
}
