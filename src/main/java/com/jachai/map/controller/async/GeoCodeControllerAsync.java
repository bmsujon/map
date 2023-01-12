package com.jachai.map.controller.async;

import com.jachai.map.dto.Location;
import com.jachai.map.dto.rest.response.BariKoiGeoCodeResponse;
import com.jachai.map.dto.rest.response.BariKoiGeoCodeResponseRest;
import com.jachai.map.dto.rest.response.SimpleMessageResponseREST;
import com.jachai.map.exception.NotFoundException;
import com.jachai.map.service.BariKoiRPCService;
import com.jachai.map.service.PlaceService;
import com.jachai.map.util.MapSays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@EnableAsync
public class GeoCodeControllerAsync {
    @Autowired
    private PlaceService placeService;

    @Async
    public void getAddress(DeferredResult<ResponseEntity<SimpleMessageResponseREST>> result, Location location) {
        BariKoiGeoCodeResponseRest response = placeService.getAddress(location);
        if(response.statusCode == 500 && response.getPlace() == null) {
            throw new NotFoundException("No Place found with this GeoLocation.");
//            response = new BariKoiGeoCodeResponseRest();
//            response.message = "No Place found with this GeoLocation.";
//            response.statusCode = 404;
//            response.setPlace(new BariKoiGeoCodeResponse("N/A", "N/A", "N/A"));
        }
        MapSays.ok(result, response);
    }
}
