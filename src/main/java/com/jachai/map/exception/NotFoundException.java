package com.jachai.map.exception;


public class NotFoundException extends JachaiException {
    public NotFoundException(String message) {
        super(message, 404);
    }
}
