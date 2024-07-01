package com.example.rpcclient.config;

import com.example.rpcclient.logger.BiFeignLoggerFactory;
import com.example.rpcclient.wapper.BiRpcContextCallableWrapper;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BiFeignConfig {

    @Bean
    FeignLoggerFactory biFeignLoggerFactory() {
        return new BiFeignLoggerFactory();
    }

    @Bean
    public BiRpcContextCallableWrapper biRpcContextCallableWrapper() {
        return new BiRpcContextCallableWrapper();
    }

}