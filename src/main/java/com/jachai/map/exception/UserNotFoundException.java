package com.jachai.map.exception;

public class UserNotFoundException extends JachaiException {
    public UserNotFoundException(String message) {
        super(message, 404);
    }
}
