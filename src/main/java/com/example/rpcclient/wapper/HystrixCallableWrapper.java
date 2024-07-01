package com.example.rpcclient.wapper;

import java.util.concurrent.Callable;

public interface HystrixCallableWrapper {

    <T> Callable<T> wrap(Callable<T> var1);
}
