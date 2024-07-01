package com.example.rpcclient.logger;

import feign.Logger;
import feign.Request;
import feign.Response;

import java.io.IOException;

public class BiFeignLogger extends Logger {

        private final org.slf4j.Logger logger;

        public BiFeignLogger(org.slf4j.Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void log(String configKey, String format, Object... args) {
            // info级别
            if (logger.isInfoEnabled()) {
                this.logger.info(String.format(methodTag(configKey) + format, args));
            }
        }

        @Override
        protected void logRequest(String configKey, Level logLevel, Request request) {
            // info级别
            if (this.logger.isInfoEnabled()) {
                super.logRequest(configKey, logLevel, request);
            }

        }

        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
            // info级别
            return this.logger.isInfoEnabled() ? super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime) : response;
        }
    }
