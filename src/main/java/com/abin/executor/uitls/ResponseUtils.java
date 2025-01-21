package com.abin.executor.uitls;

import com.abin.executor.domain.BaseResponse;
import com.abin.executor.domain.enums.ResponseType;

public class ResponseUtils {

    public static BaseResponse<Void> success() {
        BaseResponse<Void> response = new BaseResponse<>();
        response.setCode(ResponseType.SUCCESS.getCode());
        response.setMessage(ResponseType.SUCCESS.getMessage());
        return response;
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResponseType.SUCCESS.getCode());
        response.setMessage(ResponseType.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static BaseResponse<Void> error(ResponseType responseType) {
        BaseResponse<Void> response = new BaseResponse<>();
        response.setCode(responseType.getCode());
        response.setMessage(responseType.getMessage());
        return response;
    }
}
