package com.example.rpcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@EnableDiscoveryClient
@SpringCloudApplication
@ComponentScan({"com.example.rpcclient"})
public class RpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }

}
