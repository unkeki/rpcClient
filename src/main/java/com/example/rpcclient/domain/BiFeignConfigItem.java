package com.example.rpcclient.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 配置信息实体
 */
@Data
public class BiFeignConfigItem implements Serializable {
    private String agentCode;
    private String appId;
    private String secret;
    private Boolean isDSF = false;

}
