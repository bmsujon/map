package com.jachai.map.exception.rest;

import com.jachai.map.exception.JachaiException;
import org.apache.http.HttpStatus;


public class InvalidInputException extends JachaiException {
	public InvalidInputException(String message){
		super(message);
		statusCode = HttpStatus.SC_BAD_REQUEST;
	}
}
