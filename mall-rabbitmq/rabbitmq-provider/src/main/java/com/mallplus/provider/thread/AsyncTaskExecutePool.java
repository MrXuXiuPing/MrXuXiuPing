/**
* Copyright (C) 2018-2020
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制
*/
package com.mallplus.provider.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池装配类
 * 原生(Spring)异步任务线程池装配类，实现AsyncConfigurer重写他的两个方法，这样在使用默认的线程池的时候就会使用自己重写的
 * @author https://juejin.im/entry/5abb8f6951882555677e9da2
 * @date 2019年10月31日15:06:18
 */
@Slf4j
@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {

    /** 注入配置类 */
    private final AsyncTaskProperties config;

    public AsyncTaskExecutePool(AsyncTaskProperties config) {
        this.config = config;
    }
//    @Bean("taskExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(config.getCorePoolSize());
        //最大线程数
        executor.setMaxPoolSize(config.getMaxPoolSize());
        //队列容量
        executor.setQueueCapacity(config.getQueueCapacity());
        //活跃时间
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        //线程名字前缀
        executor.setThreadNamePrefix("mall-async-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        //线程池的饱和策略 我这里设置的是 CallerRunsPolicy 也就是由用调用者所在的线程来执行任务 共有四种
        //AbortPolicy：直接抛出异常，这是默认策略；
        //CallerRunsPolicy：不在新线程中执行任务，用调用者所在的线程来执行任务；
        //DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
        //DiscardPolicy：直接丢弃任务；
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置在关闭线程池时是否等待任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置等待终止的秒数
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("===="+throwable.getMessage()+"====", throwable);
            log.error("exception method:"+method.getName());
        };
    }
}
