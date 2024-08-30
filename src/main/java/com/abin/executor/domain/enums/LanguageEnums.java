package com.abin.executor.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum LanguageEnums {
    C("c",
            "main.c",
            new String[]{"gcc", "-finput-charset=UTF-8", "-fexec-charset=UTF-8", "-o", "main", "main.c"},
            new String[]{"./main"}),
    CPP("cpp",
            "main.cpp",
            new String[]{"g++", "-finput-charset=UTF-8", "-fexec-charset=UTF-8", "-o", "main", "main.cpp"},
            new String[]{"./main"}),
    JAVA("java",
            "Main.java",
            new String[]{"javac", "-cp", "/box", "-encoding", "utf-8", "Main.java"},
            new String[]{"java", "-Dfile.encoding=UTF-8", "Main"}),
    PYTHON3("python",
            "main.py",
           null,
            new String[]{"python3", "main.py"}),
    JAVASCRIPT("javascript",
            "main.js",
            null,
            new String[]{"node", "main.js"}),
    TYPESCRIPT("typescript",
            "main.ts",
            null,
            new String[]{"node", "main.ts"}),
    GO("go",
            "main.go",
            null,
            new String[]{"go", "run", "main.go"}),
    ;
    private final String language;

    private final String fileName;

    private final String[] compileCmd;

    private final String[] execCmd;

    public static final Map<String, LanguageEnums> cache;

    static {
        cache = Arrays.stream(LanguageEnums.values()).collect(Collectors.toMap(LanguageEnums::getLanguage, Function.identity()));
    }

    public static LanguageEnums of(String language) {
        return cache.get(language.toLowerCase());
    }
}
