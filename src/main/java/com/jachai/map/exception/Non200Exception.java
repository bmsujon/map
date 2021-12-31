package com.jachai.map.exception;

public class Non200Exception extends JachaiException {
	public Non200Exception(String message, int statusCode) {
		super(message, statusCode);
	}
}
