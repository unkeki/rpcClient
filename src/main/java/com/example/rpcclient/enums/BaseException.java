package com.example.rpcclient.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseException extends RuntimeException {
    public static final Integer system_error = -1;
    private static final long serialVersionUID = 6937304029901546157L;
    private String errMsg = "";
    private Integer errCode;

    public BaseException(Exception exception) {
        this(exception,true);
    }

    public BaseException(Integer code, String msg) {
        this(code,msg,true);
    }

    public BaseException(String msg) {
        this(system_error,msg,true);
    }

    public BaseException(Integer code, String msg, boolean isShowErrorDetail) {
        super(msg, null, false, isShowErrorDetail);
        this.errMsg = msg;
        this.errCode = code;
        if(this.getClass().isAssignableFrom(BaseException.class)){
            log.error(msg,this);
        }
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e, false, true);
        this.errMsg = msg;
        this.errCode = system_error;
        log.error(msg,e);
    }

    public BaseException(Integer code, String msg, Throwable e) {
        super(msg, e, false, true);
        this.errMsg = msg;
        this.errCode = code;
        log.error(msg,e);
    }

    public BaseException(Exception e, Boolean isShowErrorDetail) {
        super(e.getMessage(), (Throwable)null, false, isShowErrorDetail);
        this.errCode = system_error;
        this.errMsg = e.getMessage();
        log.error(e.getMessage(),e);
    }
    public String getErrMsg() {
        return this.errMsg;
    }

    public Integer getErrCode() {
        return this.errCode;
    }
}

