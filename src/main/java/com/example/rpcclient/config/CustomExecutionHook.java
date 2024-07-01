package com.example.rpcclient.config;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;

/**
 * Hystrix插件集成，解决Hystrix线程池token重复使用问题
 * 由于当前使用dsf的HystrixCallableWrapper，故该接口暂时不用
 * 如需要开启，只需要增加spi注入即可。
 * resources\META-INF\services文件夹增加命名为：com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook的文本文件
 * 内容是：cn.com.do1.component.bi.feign.config.CustomExecutionHook
 */
public class CustomExecutionHook extends HystrixCommandExecutionHook {

    @Override
    public <T> void onStart(HystrixInvokable<T> commandInstance) {
        //当前主线程
    }

    @Override
    public <T> void onExecutionStart(HystrixInvokable<T> commandInstance) {
        //HystrixCommand执行线程
    }

    @Override
    public <T> void onFallbackStart(HystrixInvokable<T> commandInstance) {
        System.out.println(3);
    }
    @Override
    public <T> void onExecutionSuccess(HystrixInvokable<T> commandInstance) {
        super.onExecutionSuccess(commandInstance);
    }

    @Override
    public <T> Exception onExecutionError(HystrixInvokable<T> commandInstance, Exception e) {
        return super.onExecutionError(commandInstance, e);
    }

    @Override
    public <T> void onFallbackSuccess(HystrixInvokable<T> commandInstance) {
        super.onFallbackSuccess(commandInstance);
    }

    @Override
    public <T> Exception onFallbackError(HystrixInvokable<T> commandInstance, Exception e) {
        return super.onFallbackError(commandInstance, e);
    }

}