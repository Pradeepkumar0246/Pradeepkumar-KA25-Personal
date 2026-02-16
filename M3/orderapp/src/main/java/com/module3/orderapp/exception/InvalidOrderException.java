package com.module3.orderapp.exception;

public class InvalidOrderException extends RuntimeException {

    private final String code;

    public InvalidOrderException(String message) {
        super(message);
        this.code = "INVALID_ORDER";
    }

    public String getCode() {
        return code;
    }
}