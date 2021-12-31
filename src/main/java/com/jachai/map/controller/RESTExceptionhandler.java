package com.jachai.map.controller;

import com.jachai.map.dto.rest.error.InvalidInputErrorResponseREST;
import com.jachai.map.dto.rest.response.SimpleErrorResponseREST;
import com.jachai.map.exception.UnAuthorizeException;
import com.jachai.map.exception.rest.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.util.ArrayList;

@ControllerAdvice
@Slf4j
public class RESTExceptionhandler {

	@ExceptionHandler(AccessForbiddenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public SimpleErrorResponseREST handleAccessForbiddenException(Exception e) {
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.FORBIDDEN.value());
	}

	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public SimpleErrorResponseREST handleInvalidTokenException(Exception e) {
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
	}

	@ExceptionHandler(UnAuthorizeException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public SimpleErrorResponseREST handleUnAuthorizeException(Exception e) {
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
	}

	@ExceptionHandler(ModuleDownException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SimpleErrorResponseREST handleModuleDownException(Exception e) {
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public InvalidInputErrorResponseREST handleBadRequest(MethodArgumentNotValidException e) {
		String message = "Invalid inputs";
		ArrayList<String> fieldNames = new ArrayList<String>();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			fieldNames.add(fieldError.getField());
		}
		return new InvalidInputErrorResponseREST(message, fieldNames);
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public InvalidInputErrorResponseREST handleBadRequestParameter(BindException e) {
		String message = "Invalid inputs";
		ArrayList<String> fieldNames = new ArrayList<String>();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			fieldNames.add(fieldError.getField());
		}
		return new InvalidInputErrorResponseREST(message, fieldNames);
	}
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public InvalidInputErrorResponseREST handleIllegalState(IllegalStateException e) {
		String message = "Invalid inputs";
		ArrayList<String> fieldNames = new ArrayList<String>();
		fieldNames.add(e.getMessage());
		return new InvalidInputErrorResponseREST(message, fieldNames);
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public SimpleErrorResponseREST handleUnreadablerequest(HttpMessageNotReadableException e) {
		String message = "Invalid input: Non numeric value found at numeric field";
		return new SimpleErrorResponseREST(message, HttpStatus.BAD_REQUEST.value());

	}



	@ExceptionHandler(InvalidMobileNumberException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public SimpleErrorResponseREST handleInvalidMobileNumberException(Exception e) {
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(InvalidResourceTypeException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public SimpleErrorResponseREST handleInvalidResourceTypeException(Exception e) {
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(MultipartException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public SimpleErrorResponseREST handleMultipartException(Exception e) {
		log.error("Mulipart exception", e);
		return new SimpleErrorResponseREST(e.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

}
