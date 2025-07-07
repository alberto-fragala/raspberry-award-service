package com.award.application.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class ThreadPoolConfig {

    @Produces
    @Named("producerIntervalsExecutor")
    public Executor producerIntervalsExecutor() {
        int corePoolSize = 4;
        int maxPoolSize = 10;
        long keepAlive = 60L;

        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAlive,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
