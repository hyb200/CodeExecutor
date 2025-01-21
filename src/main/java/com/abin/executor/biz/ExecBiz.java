package com.abin.executor.biz;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.abin.executor.docker.DockerContainer;
import com.abin.executor.domain.CmdResult;
import com.abin.executor.domain.ExecuteReq;
import com.abin.executor.domain.ExecuteResp;
import com.abin.executor.domain.enums.LanguageEnums;

@Service
@Slf4j
public class ExecBiz {

    @Resource
    private DockerContainer dockerContainer;

    public ExecuteResp exec(ExecuteReq req) {
        String language = req.getLanguage();
        String code = req.getCode();

        String containerId = dockerContainer.create();
        log.info("create container, containerId: {}", containerId);
//        Optional.ofNullable(LanguageEnums.of(language)).ifPresent(languageEnum -> {
//            CmdResult cmdResult = dockerContainer.execCmd(containerId, languageEnum.getCompileCmd(), 10, TimeUnit.SECONDS);
//            log.info("compile result: {}", cmdResult);
//        });

        ExecuteResp executeResp = new ExecuteResp();
        return executeResp;
    }
}
