package com.jachai.map.controller;

import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import com.jachai.map.exception.JachaiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.lang.reflect.Method;

@Slf4j
public class BabyDontCrash implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable exception, Method method, Object... params) {

        log.error("Auth angry: " + exception.getMessage());

        DeferredResult<ResponseEntity<SimpleMessageResponseREST>> errorResult = (DeferredResult<ResponseEntity<SimpleMessageResponseREST>>) params[0];


        if (exception instanceof JachaiException) {
            processJachaiException((JachaiException) exception, errorResult);
        }
        else {
            log.error("Hate Hariken", exception);
            processHateHarikenException((Exception) exception, errorResult);
        }
    }

    private void processJachaiException(JachaiException exception, DeferredResult<ResponseEntity<SimpleMessageResponseREST>> errorResult) {
        SimpleMessageResponseREST errorResponseRest = new SimpleMessageResponseREST();
        errorResponseRest.message = exception.getMessage();
        errorResponseRest.statusCode = exception.statusCode;
        ResponseEntity<SimpleMessageResponseREST> entity = new ResponseEntity<>(errorResponseRest, HttpStatus.valueOf(exception.statusCode));
        errorResult.setResult(entity);
    }

    private void processHateHarikenException(Exception exception, DeferredResult<ResponseEntity<SimpleMessageResponseREST>> errorResult) {
        SimpleMessageResponseREST errorResponseRest = new SimpleMessageResponseREST();
        errorResponseRest.message = exception.getMessage();
        errorResponseRest.statusCode = 500;
        ResponseEntity<SimpleMessageResponseREST> entity = new ResponseEntity<>(errorResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        errorResult.setResult(entity);
    }

}