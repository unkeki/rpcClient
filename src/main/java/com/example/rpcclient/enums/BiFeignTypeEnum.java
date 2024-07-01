package com.example.rpcclient.enums;

import lombok.Getter;

@Getter
public enum BiFeignTypeEnum {

    /**
     * BI系统API应用ID
     */
    API_FEIGN_BI("bi"),
    /**
     * 中台通讯录应用ID
     */
    API_FEIGN_ADDRESSBOOK("addressbook"),
    ;

    BiFeignTypeEnum(String agentCode) {
        this.agentCode = agentCode;
    }

    /**
     * 类型
     */
    private String agentCode;

}
