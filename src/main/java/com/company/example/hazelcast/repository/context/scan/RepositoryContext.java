package com.company.example.hazelcast.repository.context.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.company.example.hazelcast.repository.context.config"})
public class RepositoryContext {

}
