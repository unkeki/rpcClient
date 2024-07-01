package com.example.rpcclient.logger;

import feign.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

public class BiFeignLoggerFactory implements FeignLoggerFactory {
    @Override
    public Logger create(Class<?> type) {
        return new BiFeignLogger(LoggerFactory.getLogger(type));
    }
}
