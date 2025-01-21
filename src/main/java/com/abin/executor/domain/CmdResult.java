package com.abin.executor.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CmdResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -5403231354693534619L;

    private String flag;

    private int code;

    private String message;

    private String errorMsg;

    /**
     * 耗时，单位ms
     */
    private long time;

    /**
     * 内存，单位 KB
     */
    private long memory;
}
