package com.despegar.example.hazelcast.job.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = { "com.despegar.example.hazelcast.job" })
public class JobContextConfig {
	
}

