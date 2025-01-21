package com.abin.executor.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecuteReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -3760558969508483517L;

    private String language;

    private String code;
}
