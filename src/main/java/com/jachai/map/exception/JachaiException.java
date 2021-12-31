package com.jachai.map.exception;


public class JachaiException extends RuntimeException{
    public int statusCode;
    public JachaiException(String message, int statusCode) {
        super((message));
        this.statusCode = statusCode;
    }
    public JachaiException(String message) {
        super((message));
    }
}
