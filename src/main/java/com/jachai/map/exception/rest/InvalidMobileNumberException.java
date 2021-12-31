package com.jachai.map.exception.rest;


public class InvalidMobileNumberException extends RuntimeException {
    public InvalidMobileNumberException() {
        super("Invalid Phone Number");
    }
}
