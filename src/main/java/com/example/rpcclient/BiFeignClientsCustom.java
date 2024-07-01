package com.example.rpcclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BiFeignClientsCustom implements RequestInterceptor {
    public static final String HEADER_TOKEN_KEY = "Bi-Jwt-Token";

    @Override
    public void apply(RequestTemplate template) {
        if (BiRpcContext.status()) {
            template.header(HEADER_TOKEN_KEY, new String[]{BiRpcContext.getContext().getToken()});
        }
    }
}
