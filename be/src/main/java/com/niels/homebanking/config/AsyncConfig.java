package com.niels.homebanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig {

	@Bean("asyncTaskExecutor")
    public TaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
