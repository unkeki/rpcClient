package com.example.rpcclient;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RpcContext {
    private static final Logger log = LoggerFactory.getLogger(RpcContext.class);
    private static final ThreadLocal<RpcContext> JWT_LOCAL = ThreadLocal.withInitial(() -> {
        return new RpcContext();
    });
    private static String applicationName;
    private static String secret;
    private static int timeOut = 60;
    private boolean status;
    private String token;
    private String tenantId;
    private String appId;
    private List<Long> departmentIds;
    private List<Long> personIds;
    protected final Map<String, Object> attachments = new HashMap();

    public RpcContext() {
    }

    public static void initContext(String applicationName, String secret) {
        RpcContext.applicationName = applicationName;
        RpcContext.secret = secret;
    }

    public static void open(@NotNull String tenantId) {
        open(tenantId, (String)null);
    }

    public static void open(@NotNull String tenantId, String appId) {
        RpcContext context = (RpcContext)JWT_LOCAL.get();
        context.tenantId = tenantId;
        context.appId = appId;
        context.createToken();
        context.status = true;
    }

    public static void close() {
        JWT_LOCAL.remove();
    }

    public static boolean status() {
        return ((RpcContext)JWT_LOCAL.get()).status;
    }

    public RpcContext setAttachments(String key, Object value) {
        return this.setObjectAttachment(key, value);
    }

    public RpcContext setObjectAttachment(String key, Object value) {
        if (value == null) {
            this.attachments.remove(key);
        } else {
            this.attachments.put(key, value);
        }

        return this;
    }

    public Object getAttachmentObject(String key) {
        return this.attachments.get(key);
    }

    public Map<String, Object> getAttachments() {
        return this.attachments;
    }

    public String getToken() {
        return this.token;
    }

    public static RpcContext getContext() {
        return (RpcContext)JWT_LOCAL.get();
    }

    public static void setContext(RpcContext rpcContext) {
        JWT_LOCAL.set(rpcContext);
    }

    public static void setTimeOut(int amount) {
        timeOut = amount;
    }

    private void createToken() {
        Assert.notNull(applicationName, "请先执行initContext方法进行初始化");
        Assert.notNull(this.tenantId, "tenantId不允许为null");

        try {
            this.attachments.put("tenantId", this.tenantId);
            if (this.appId != null) {
                this.attachments.put("appId", this.appId);
            }

            Calendar cal = Calendar.getInstance();
            cal.add(13, timeOut);
            this.token = JWT.create().withIssuer(applicationName).withExpiresAt(cal.getTime()).withSubject(JSON.toJSONString(this.attachments)).sign(Algorithm.HMAC256(secret));
        } catch (UnsupportedEncodingException var2) {
            log.error(var2.getMessage());
        } catch (JWTCreationException var3) {
            log.error(var3.getMessage());
        }

    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<Long> getPersonIds() {
        return this.personIds;
    }

    public void setPersonIds(List<Long> personIds) {
        this.personIds = personIds;
    }

    public List<Long> getDepartmentIds() {
        return this.departmentIds;
    }

    public void setDepartmentIds(List<Long> departmentIds) {
        this.departmentIds = departmentIds;
    }
}
