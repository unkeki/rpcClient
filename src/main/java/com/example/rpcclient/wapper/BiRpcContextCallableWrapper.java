
package com.example.rpcclient.wapper;


import com.example.rpcclient.BiRpcContext;

import java.util.concurrent.Callable;

public class BiRpcContextCallableWrapper implements HystrixCallableWrapper {
    public BiRpcContextCallableWrapper() {
    }

    public <T> Callable<T> wrap(Callable<T> callable) {
        return new RequestAttributeAwareCallable(callable, BiRpcContext.getContext());
    }

    static class RequestAttributeAwareCallable<T> implements Callable<T> {
        private final Callable<T> delegate;
        private final BiRpcContext rpcContext;

        RequestAttributeAwareCallable(Callable<T> callable, BiRpcContext rpcContext) {
            this.delegate = callable;
            this.rpcContext = rpcContext;
        }

        public T call() throws Exception {
            Object var1;
            try {
                BiRpcContext.setContext(rpcContext);
                var1 = this.delegate.call();
            } finally {
                BiRpcContext.close();
            }

            return (T) var1;
        }
    }
}
