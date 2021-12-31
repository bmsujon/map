package com.jachai.map.exception.rest;


import com.jachai.map.exception.JachaiException;

public class InvalidFormattedDataException extends JachaiException {

    public InvalidFormattedDataException() {
        this("Invalid formatted data");
    }

    public InvalidFormattedDataException(String message) {
        super(message);
    }
}