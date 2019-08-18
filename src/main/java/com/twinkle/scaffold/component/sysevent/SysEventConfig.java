package com.twinkle.scaffold.component.sysevent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月18日 下午4:28:30 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Configuration
@EnableAsync
public class SysEventConfig {

    @Bean("sysEventAsync")
    public Executor sysEventAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("sysEventAsync-");
        // 当线程池不够用且队列已满时，丢弃任务，但是不抛出异常。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
    
}
