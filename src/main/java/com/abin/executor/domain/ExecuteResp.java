package com.abin.executor.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExecuteResp implements Serializable {

    @Serial
    private static final long serialVersionUID = -4758003308102239406L;
    /**
     * 执行状态码
     */
    private Integer execStatusCode;

    /**
     * 执行结果
     */
    private String execResult;

    /**
     * 收集错误信息
     */
    private String errMsg;

    /**
     * 输出信息
     */
    private String output;

    /**
     * 时间消耗
     */
    private Long timeUsage;

    /**
     * 内存消耗
     */
    private Long memoryUsage;
}
