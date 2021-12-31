package com.jachai.map.exception;

import org.apache.http.HttpStatus;

public class InternalIOException extends JachaiException {

    public InternalIOException()
    {
        super("Internal IO Exception", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        this.statusCode= HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

	public InternalIOException(String message){
		super(message, HttpStatus.SC_INTERNAL_SERVER_ERROR);
		this.statusCode= HttpStatus.SC_INTERNAL_SERVER_ERROR;
	}
}
