package com.abin.executor.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abin.executor.biz.ExecBiz;
import com.abin.executor.domain.BaseResponse;
import com.abin.executor.domain.ExecuteReq;
import com.abin.executor.domain.ExecuteResp;
import com.abin.executor.uitls.ResponseUtils;

@RestController
@RequiredArgsConstructor
public class ExecController {

    private final ExecBiz execBiz;

    @PostMapping("/exec")
    public BaseResponse<ExecuteResp> execute(@RequestBody ExecuteReq req) {
        return ResponseUtils.success(execBiz.exec(req));
    }
}
