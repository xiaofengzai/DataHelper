package com.touyun.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncExecutorThread-");
        executor.initialize(); //如果不初始化，导致找到不到执行器
        return executor;
    }

    @Bean
    public ExecutorService getThreadPool(){
        return Executors.newFixedThreadPool(10);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        private final Logger logger = LoggerFactory.getLogger(getClass());

        //手动处理捕获的异常
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            System.out.println("-------------》》》捕获线程异常信息");
            logger.info("Exception message - " + throwable.getMessage());
            logger.info("Method name - " + method.getName());
            for (Object param : obj) {
                logger.info("Parameter value - " + param);
            }
        }
    }
}
