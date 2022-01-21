package com.jachai.map.controller.rest;

import com.jachai.map.controller.async.GeoCodeControllerAsync;
import com.jachai.map.dto.Location;
import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/v1/geocode")
@Slf4j
public class GeoCodeController {
    @Autowired
    private GeoCodeControllerAsync geoCodeControllerAsync;

    @GetMapping()
    public DeferredResult<ResponseEntity<SimpleMessageResponseREST>> getAddress(
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude
    ) {
        log.info("Request geo-code for location: {}, {}",latitude, longitude );
        DeferredResult<ResponseEntity<SimpleMessageResponseREST>> result = new DeferredResult<>();
        geoCodeControllerAsync.getAddress(result, new Location(latitude, longitude));
        return result;
    }

}
