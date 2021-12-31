package com.jachai.map.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessageResponseREST implements Serializable {
    public String message;
    public Integer statusCode;
}
