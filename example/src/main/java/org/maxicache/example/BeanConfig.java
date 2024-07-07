package org.maxicache.example;

import org.maxicache.implementation.CacheClientImpl;
import org.maxicache.interfaces.CacheClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    CacheClient cacheClient() {
        return new CacheClientImpl("localhost", 6379);
    }

}
