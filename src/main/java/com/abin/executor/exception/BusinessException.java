package com.abin.executor.exception;

import lombok.Data;

import com.abin.executor.domain.enums.ResponseType;

@Data
public class BusinessException extends RuntimeException {

    private Integer errorType;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResponseType responseType) {
        super(responseType.getMessage());
        this.errorType = responseType.getCode();
    }

    public BusinessException(Integer errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
