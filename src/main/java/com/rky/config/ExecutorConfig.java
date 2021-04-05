package com.rky.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {
	Logger logger = org.slf4j.LoggerFactory.getLogger(ExecutorConfig.class);

	@Bean(name = "myExecutor")
	public Executor executor() {
		ThreadPoolTaskExecutor threadPoolexecutor = new ThreadPoolTaskExecutor();
		threadPoolexecutor.setCorePoolSize(2);
		threadPoolexecutor.setMaxPoolSize(4);
		threadPoolexecutor.setQueueCapacity(5);
		threadPoolexecutor.setThreadNamePrefix("threadTask-");
		threadPoolexecutor.setRejectedExecutionHandler((r, executor) -> {
			try {
				Thread.sleep(500);
				logger.warn("Queue is full, Waiting for some time");
			} catch (InterruptedException e) {
				logger.error("Thread interrupted:  ()", e.getCause());
				Thread.currentThread().interrupt();
			}
			threadPoolexecutor.execute(r);
		});
		threadPoolexecutor.initialize();
		return threadPoolexecutor;
	}
}
