package com.jachai.map.exception.rest;

public class BadLoginException extends RuntimeException {
	public BadLoginException(String msg) {
		super(msg);
	}
}