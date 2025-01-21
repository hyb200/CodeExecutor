package com.abin.executor.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

@Data
public class BaseResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1468440919654282401L;

    private int code;

    private T data;

    private String message;
}
