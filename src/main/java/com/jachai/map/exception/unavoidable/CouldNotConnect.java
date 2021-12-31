package com.jachai.map.exception.unavoidable;

public class CouldNotConnect extends Exception {
    public CouldNotConnect(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotConnect(String message) {
        super(message);
    }
}