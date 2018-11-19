package com.wangfan.example.junit.externalservice.stub;

import jdk.nashorn.internal.runtime.logging.Logger;

import java.lang.annotation.Annotation;

public class stubLogTest implements Logger {
    @Override
    public String name() {
        /**
         * stub就是不做无意义的操作，比如没必要单元测试的时候，实际录入一段日志。
         * stub返回硬编码值，替换掉缓慢的真实事物，以及鞭长莫及的依赖。
         */
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
