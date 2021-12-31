package com.jachai.map.dto.rest.response;


public class SimpleErrorResponseREST extends ResponseREST {

    private static final long serialVersionUID = 1L;
    public String message;
    public Integer status;

    public SimpleErrorResponseREST(String error, Integer status) {
        this.message = error;
        this.status = status;
    }
}
