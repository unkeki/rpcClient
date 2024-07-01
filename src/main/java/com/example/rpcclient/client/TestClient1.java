package com.example.rpcclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "test-client1")
public interface TestClient1 {

    @GetMapping("/")
    String get();
}
