package com.abin.executor.uitls;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.abin.executor.domain.enums.LanguageEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
public class CommonUtils {

    private static final String GLOBAL_CODE_PATH = "code";

    /**
     * 保存代码
     *
     * @param language 语言
     * @param code     代码
     * @return 文件地址
     */
    public static String saveCode(String language, String code) {
        String userDir = System.getProperty("user.dir");
        String globalCodePath = userDir + File.separator + GLOBAL_CODE_PATH;

        if (!FileUtil.exist(globalCodePath)) {
            FileUtil.mkdir(globalCodePath);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(System.currentTimeMillis());

        LanguageEnums languageEnum = LanguageEnums.of(language);

        String codePath = globalCodePath + File.separator +
                date + File.separator +
                IdUtil.fastSimpleUUID() + File.separator +
                languageEnum.getFileName();

        FileUtil.touch(codePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(codePath))) {
            writer.write(code);
            return codePath;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 删除文件
     * @param codePath 文件父目录
     */
    public static void deleteFile(String codePath) {
        FileUtil.del(FileUtil.getParent(codePath, 1));
    }
}
