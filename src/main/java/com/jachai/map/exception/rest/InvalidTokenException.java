package com.jachai.map.exception.rest;

import com.jachai.map.exception.JachaiException;
import org.apache.http.HttpStatus;

public class InvalidTokenException extends JachaiException {

	public InvalidTokenException(String msg) {
		super(msg);
		statusCode = HttpStatus.SC_UNAUTHORIZED;
	}

}
