package com.abin.executor.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum ExecStatusEnums {
    SUCCESS(0, "通过"),
    COMPILE_ERROR(1, "编译错误"),
    TIME_LIMIT_EXCEEDED(2, "超出时间限制"),
    MEMORY_LIMIT_EXCEEDED(2, "超出内存限制"),
    RUNTIME_ERROR(3, "执行错误")
    ;
    private final Integer code;
    private final String desc;

    private static final Map<Integer, ExecStatusEnums> cache;

    static {
        cache = Arrays.stream(ExecStatusEnums.values()).collect(Collectors.toMap(ExecStatusEnums::getCode, Function.identity()));
    }

    public static ExecStatusEnums of(Integer type) {
        return cache.get(type);
    }
}
