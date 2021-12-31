package com.jachai.map.util;

import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
public class MapSays {

    public static void prepareSuccessResponse(String statusMessage, SimpleMessageResponseREST message) {
        message.message = statusMessage;
        message.statusCode = 200;
        log.debug("JaChai Map says: " + statusMessage);
    }

    public static void ok(DeferredResult<ResponseEntity<SimpleMessageResponseREST>> result, SimpleMessageResponseREST message, String responseMessage) {
        prepareSuccessResponse(responseMessage, message);
        ResponseEntity<SimpleMessageResponseREST> response = new ResponseEntity<>(message, HttpStatus.OK);
        result.setResult(response);
    }

    public static void ok(DeferredResult<ResponseEntity<SimpleMessageResponseREST>> result, SimpleMessageResponseREST message) {
        ok(result, message, "Operation Successful");
    }
}
