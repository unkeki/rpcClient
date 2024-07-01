package com.example.rpcclient;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BiRpcContext {
    private static final Logger log = LoggerFactory.getLogger(BiRpcContext.class);
    private static final ThreadLocal<BiRpcContext> BI_RPC_CONTEXT_THREAD_LOCAL = new InheritableThreadLocal<>();
    private String agentCode;
    private String appId;
    private String applicationName;
    private String secret;
    private Boolean isDSF;
    private String tenantId;
    private boolean status;
    private String token;

    /**
     * jwt token过期时间，秒为单位
     */
    @Value("${bi-feign.timeOut}")
    private Integer timeOut = 60;


    public BiRpcContext(String agentCode, String appId, String applicationName, String secret,Boolean isDSF) {
        this.agentCode = agentCode;
        this.appId = appId;
        this.applicationName = applicationName;
        this.secret = secret;
        this.isDSF = isDSF;
    }

    public static void setBiRpcContext(String agentCode,String appId, String applicationName, String secret,Boolean isDSF) {
        BI_RPC_CONTEXT_THREAD_LOCAL.set(new BiRpcContext(agentCode,appId,applicationName,secret,isDSF));
    }

    public static <T, R> R applyWithParam(String tenantId, InterfaceUnVoidWithParams<R> function) {
        BiRpcContext context = BI_RPC_CONTEXT_THREAD_LOCAL.get();
        Assert.notNull(context, "context不允许为null");
        if(context.isDSF){
            return applyWithParamByDSF(context,tenantId,function);
        }else{
            return applyWithParamByBI(context,tenantId,function);
        }
    }

    public static void applyWithParamAndNoResult(String tenantId,Consumer function) {
        BiRpcContext context = BI_RPC_CONTEXT_THREAD_LOCAL.get();
        Assert.notNull(context, "context不允许为null");
        if(context.isDSF){
            RpcContext.initContext(context.getApplicationName(),context.getSecret());
            RpcContext.open(tenantId,context.getAppId());
            function.accept(null);
        }else{
            context.tenantId = tenantId;
            context.createToken();
            context.status = true;
            function.accept(null);
        }
    }

    public static <T, R> R applyWithParam(InterfaceUnVoidWithParams<R> function) {
        BiRpcContext context = BI_RPC_CONTEXT_THREAD_LOCAL.get();
        Assert.notNull(context, "context不允许为null");
        if(context.isDSF){
            return applyWithParamByDSF(context,"",function);
        }else{
            return applyWithParamByBI(context,null,function);
        }
    }

    private static <T, R> R applyWithParamByDSF(BiRpcContext context,String tenantId,InterfaceUnVoidWithParams<R> function){
        RpcContext.initContext(context.getApplicationName(),context.getSecret());
        RpcContext.open(tenantId,context.getAppId());
        return function.run();
    }

    private static <T, R> R applyWithParamByBI(BiRpcContext context,String tenantId,InterfaceUnVoidWithParams<R> function){
        context.tenantId = tenantId;
        context.createToken();
        context.status = true;
        return function.run();
    }

    public static boolean status() {
        if(BI_RPC_CONTEXT_THREAD_LOCAL.get() == null){
            return false;
        }
        return (BI_RPC_CONTEXT_THREAD_LOCAL.get()).status;
    }

    public static BiRpcContext getContext() {
        return BI_RPC_CONTEXT_THREAD_LOCAL.get();
    }
    public static void setContext(BiRpcContext rpcContext) {
        BI_RPC_CONTEXT_THREAD_LOCAL.set(rpcContext);
    }
    private void createToken() {
        Assert.notNull(applicationName, "请先执行initContext方法进行初始化");
        try {
            Map<String,String> params = new HashMap<>();
            params.put("tenantId", this.tenantId);
            params.put("appId", this.appId);
            params.put("agentCode", this.agentCode);
            this.token = JWT.create().withIssuer(applicationName).withExpiresAt(DateUtils.addSeconds(new Date(),timeOut)).withSubject(JSON.toJSONString(params)).sign(Algorithm.HMAC256(secret));
        } catch (UnsupportedEncodingException var2) {
            log.error(var2.getMessage());
        } catch (JWTCreationException var3) {
            log.error(var3.getMessage());
        }

    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void close() {
        BiRpcContext context = BI_RPC_CONTEXT_THREAD_LOCAL.get();
        if(context == null){
            return;
        }
        if(context.isDSF){
            //清空登录信息，清除token信息
            RpcContext.close();
        }
        BI_RPC_CONTEXT_THREAD_LOCAL.remove();
    }
}
