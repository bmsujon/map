package com.jachai.map.dto.rest.error;

import java.io.Serializable;
import java.util.ArrayList;

public class InvalidInputErrorResponseREST implements Serializable {
    private static final long serialVersionUID = 1L;
    public String message;
    public ArrayList<String> errorFieldNames;


    public InvalidInputErrorResponseREST(String error, ArrayList<String> fieldNames) {
        this.message = error;
        this.errorFieldNames = fieldNames;
    }
}
