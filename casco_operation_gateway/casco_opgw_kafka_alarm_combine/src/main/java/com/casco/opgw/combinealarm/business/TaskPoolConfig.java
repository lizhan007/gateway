package com.casco.opgw.combinealarm.business;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class TaskPoolConfig {
    @Bean
    public Executor executor() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        return executorService;
    }
}
