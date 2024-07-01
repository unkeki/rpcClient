package com.example.rpcclient.annotation;

import com.example.rpcclient.enums.BiFeignTypeEnum;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作权限注解
 * 可放到方法上，也可放到类上
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Order(3)
public @interface BiFeign {
    BiFeignTypeEnum value();
}