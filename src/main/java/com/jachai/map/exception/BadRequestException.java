package com.jachai.map.exception;

public class BadRequestException extends JachaiException {
    public BadRequestException(String message) {
        super(message, 400);
    }
}
