package com.example.rpcclient.aop;

import com.example.rpcclient.BiRpcContext;
import com.example.rpcclient.annotation.BiFeign;
import com.example.rpcclient.config.BiFeignConfigItemBean;
import com.example.rpcclient.domain.BiFeignConfigItem;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * biFeign的校验字段处理
 */
@Aspect
@Component
@ConditionalOnClass({BiFeignConfigItemBean.class})
@Slf4j
public class BiRpcAspect {

    @Autowired(required = false)
    BiFeignConfigItemBean biFeignConfigItemBean;

    @Value("${spring.application.name}")
    private String appName;

    @Around("@annotation(com.example.rpcclient.annotation.BiFeign) || @within(com.example.rpcclient.annotation.BiFeign)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        BiFeign annotation = point.getTarget().getClass().getAnnotation(BiFeign.class);
        String agentCode = annotation.value().getAgentCode();
        Assert.notEmpty(biFeignConfigItemBean.getBiFeignConfigItemList(), "获取的配置项为空");
        BiFeignConfigItem item = biFeignConfigItemBean.getBiFeignConfigItemList().stream().filter(u -> u.getAgentCode().equals(agentCode)).findFirst().get();
        Assert.notNull(item, "agentCode:"+agentCode+"获取的配置项为空");
        BiRpcContext.setBiRpcContext(item.getAgentCode(),item.getAppId(),appName,item.getSecret(),item.getIsDSF());
        try {
            return point.proceed();
        } finally {
            //清空登录信息，清除token信息
            BiRpcContext.close();
        }
    }

}
