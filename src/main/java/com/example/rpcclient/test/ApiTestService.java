package com.example.rpcclient.test;

import com.example.rpcclient.BiRpcContext;
import com.example.rpcclient.annotation.BiFeign;
import com.example.rpcclient.client.TestClient1;
import com.example.rpcclient.enums.BiFeignTypeEnum;

@BiFeign(value = BiFeignTypeEnum.API_FEIGN_BI)
public class ApiTestService {

    private final TestClient1 testClient1;

    public ApiTestService(TestClient1 testClient1){
        this.testClient1 = testClient1;
    }

    public void test1(){
        BiRpcContext.applyWithParam(testClient1::get);
    }
}
