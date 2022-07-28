package com.jachai.map.controller.async;

import com.jachai.map.dto.rest.response.PlaceResponseListResponseRest;
import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import com.jachai.map.service.PlaceService;
import com.jachai.map.util.MapSays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@Slf4j
@EnableAsync
public class AutoCompleteControllerAsync {
    @Autowired
    private PlaceService placeService;


    @Async
    public void searchPlaces(DeferredResult<ResponseEntity<SimpleMessageResponseREST>> result, String key) {
        PlaceResponseListResponseRest response = new PlaceResponseListResponseRest();
        response.setPlaces(placeService.findByKey(key));
        MapSays.ok(result, response);
    }
}
