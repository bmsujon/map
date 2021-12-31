package com.jachai.map.exception;

public class DuplicateUserException extends JachaiException {
    public DuplicateUserException(String message) {
        super(message, 400);
    }
}
