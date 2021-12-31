package com.jachai.map.exception;

public class UnAuthorizeException extends JachaiException {
    public UnAuthorizeException(String message) {
        super(message, 401);
    }
}
