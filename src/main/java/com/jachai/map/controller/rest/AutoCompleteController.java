package com.jachai.map.controller.rest;

import com.jachai.map.controller.async.AutoCompleteControllerAsync;
import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/v1/autocomplete")
public class AutoCompleteController {
    @Autowired
    private AutoCompleteControllerAsync autoCompleteControllerAsync;

    @GetMapping()
    public DeferredResult<ResponseEntity<SimpleMessageResponseREST>> search(
            @RequestParam(value = "key") String key
    ) {
        DeferredResult<ResponseEntity<SimpleMessageResponseREST>> result = new DeferredResult<>();
        autoCompleteControllerAsync.searchPlaces(result, key);
        return result;
    }
}
