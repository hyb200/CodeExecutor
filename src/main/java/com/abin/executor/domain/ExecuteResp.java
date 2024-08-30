package com.abin.executor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteResp {

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
