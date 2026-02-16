package com.module3.orderapp.exception;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<?> handleInvalid(InvalidOrderException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getCode() + ": " + ex.getMessage());
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<?> handlePayment(PaymentFailedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getCode() + ": " + ex.getMessage());
    }
}
