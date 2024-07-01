package com.example.rpcclient.enums;

public class NonePrintException extends BaseException {
    public NonePrintException(Integer code, String msg) {
        super(code, msg);
    }

    public NonePrintException(String msg) {
        super(msg);
    }
}
