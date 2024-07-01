package com.example.rpcclient.config;

import com.example.rpcclient.domain.BiFeignConfigItem;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 从配置文件获取数据源配置
 */
@ConfigurationProperties(prefix = "bi-feign")
@RefreshScope
@Configuration
@Data
public class BiFeignConfigItemBean {
    /**
     * 使用如下配置
     *   bi-feign:
     *     biFeignConfigItemList:
     *      -  agentCode: bi
     *         appId: biAppId
     *         secret: biSecret
     *      -  agentCode: addressbook
     *         appId: addressbookAppId
     *         secret: addressbookSecret
     */
    private List<BiFeignConfigItem> biFeignConfigItemList = new ArrayList<>();

}


