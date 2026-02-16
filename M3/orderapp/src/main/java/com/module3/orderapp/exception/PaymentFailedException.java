package com.module3.orderapp.exception;

public class PaymentFailedException extends Exception {

    private final int code;

    public PaymentFailedException(String message) {
        super(message);
        this.code = 7001;
    }

    public int getCode() {
        return code;
    }
}