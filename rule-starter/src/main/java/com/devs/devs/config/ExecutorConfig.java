package com.devs.devs.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置类
 */
@Configuration
public class ExecutorConfig {

    @Bean("asyncExecutor")
    public ThreadPoolExecutor getAsync() {
        ThreadFactory name = new ThreadFactoryBuilder().setNameFormat("async_threadPool").build();
        return new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), name, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
