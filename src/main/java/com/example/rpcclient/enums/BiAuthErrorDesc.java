package com.example.rpcclient.enums;



public enum BiAuthErrorDesc {
    /**
     * 登录核心代码错误码
     */
    authCode_is_null(100001, "authCode为空"),
    accessToken_is_null(100002, "accessToken不存在或已过期"),
    result_is_null(100003, "请求返回内容为空"),
    content_is_error(100004, "请求返回的格式错误"),
    role_is_error(100005, "当前登录账号不具备权限"),
    user_is_null(100006, "登录用户信息为空"),
    user_is_error(100007, "当前登录信息为外部人员"),
    pre_id_is_error(100008, "登录跳转地址已失效，请重新进入登录页登录"),
    status_is_disable(100009, "账号已被禁用"),
    permission_is_null(100010, "该账号没有关联角色"),
    tenant_is_disable(100011,"租户已被禁用"),
    mgr_user_is_null(100012,"管理员账号不存在"),

    /**
     * 本地账号密码登录错误码
     */
    account_or_password_is_error(101001, "账号不存在或密码错误"),
    account_or_password_is_null(101002, "账号密码不能为空"),
    jc_id_is_error(101003, "jcId不存在或已失效，请刷新页面重新登录"),
    max_error_login(101004, "账号密码错误次数大于%s次，请%s分钟后再试"),
    tenant_id_is_null(101005, "租户ID不允许为空"),
    param_is_null(101006, "参数错误"),
    method_is_error(101007, "当前接口未开放匿名访问"),
    /**
     * api模块错误码
     */
    api_verification_error(102001, "校验token失败，请确认token合法性"),
    agent_code_is_null(102001, "请输入agentCode"),
    api_token_is_null(102001, "请输入token"),
    app_id_is_null(102001, "请输入appId"),

    ;

    /**
     * 编号
     */
    private Integer code ;
    /**
     * 描述
     */
    private String desc ;

    // 构造函数，枚举类型只能为私有
    private BiAuthErrorDesc(Integer nCode, String nDesc) {
        this.code = nCode;
        this.desc = nDesc;
    }

    /**
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code 要设置的 code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc 要设置的 desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
