package com.example.rpcclient.test;

import com.example.rpcclient.RpcClientApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RpcClientApplication.class)
@RunWith(SpringRunner.class)
//@Ignore
@Slf4j
class ApiTest1Test {

    private final ApiTestService apiTestService;

    ApiTest1Test(ApiTestService apiTestService) {
        this.apiTestService = apiTestService;
    }

    @Test
    public void getTest(){
        apiTestService.test1();
    }
}