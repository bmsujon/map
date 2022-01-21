package com.jachai.map.service;

import com.jachai.map.dto.Location;
import com.jachai.map.dto.rest.response.*;
import com.jachai.map.entity.Place;
import com.jachai.map.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private BariKoiRPCService bariKoiRPCService;

    public Place save(Place place) {
        return placeRepository.save(place);
    }

    public Place findByAddress(String address) {
        return placeRepository.findByAddress(address);
    }
    public List<PlaceResponse> findByKey(String key) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PlaceResponse> placeResponses =  placeRepository.findAllByKeyIgnoreCase(key, pageable);
        if ( placeResponses.getContent().size() > 0 ) return placeResponses.getContent();


        BariKoiSearchListResponseRest responseFromBariKoi = bariKoiRPCService.searchPlaces(key);
        List<PlaceResponse> ret = new ArrayList<>();
        for ( BariKoiSearchResponse bariKoiSearchResponse : responseFromBariKoi.getPlaces() ) {
            PlaceResponse placeResponse = PlaceResponse.builder()
                    .placeType(bariKoiSearchResponse.getPlaceType())
                    .address(bariKoiSearchResponse.getAddress())
                    .address_bn(bariKoiSearchResponse.getAddress_bn())
                    .area(bariKoiSearchResponse.getArea())
                    .area_bn(bariKoiSearchResponse.getArea_bn())
                    .city(bariKoiSearchResponse.getCity())
                    .city_bn(bariKoiSearchResponse.getCity_bn())
                    .postCode(bariKoiSearchResponse.getPostCode())
                    .location(new Location(bariKoiSearchResponse.getLatitude(), bariKoiSearchResponse.getLongitude()))
                    .build();
            ret.add(placeResponse);
        }
        addPlaces(ret, key);
        log.info("Return autocomplete result: " + ret.size());
        return ret;
    }

    public BariKoiGeoCodeResponseRest getAddress(Location location) {
        Place place = placeRepository.findFirstByGeoLocationNear(new Point(location.getLongitude(), location.getLatitude()), new Distance(.01, Metrics.KILOMETERS));

        BariKoiGeoCodeResponseRest response = null;
        if( place == null ) {
            log.info("Do not found in our map");
            response = bariKoiRPCService.getAddress(location);

            if (placeRepository.existsByAddress(response.getPlace().getAddress()) == false) {
                place = Place.builder()
                        .location(location)
                        .geoLocation(new GeoJsonPoint(location.getLongitude(), location.getLatitude()))
                        .city(response.getPlace().getCity())
                        .area(response.getPlace().getArea())
                        .address(response.getPlace().getAddress())
                        .build();
                placeRepository.save(place);
            }
        } else {
            log.info("Found in our map");
            response = new BariKoiGeoCodeResponseRest();
            BariKoiGeoCodeResponse bariKoiPlace = new BariKoiGeoCodeResponse();
            bariKoiPlace.setAddress(place.getAddress());
            bariKoiPlace.setArea(place.getArea());
            bariKoiPlace.setCity(place.getCity());
            response.setPlace(bariKoiPlace);
        }
        return response;
    }

    @Async
    public void addPlaces(List<PlaceResponse> placeResponses, String key) {
        for ( PlaceResponse placeResponse : placeResponses ) {
            Place place = placeRepository.findByAddress(placeResponse.getAddress());
            if (place == null ) {
                place = Place.builder()
                        .placeType(placeResponse.getPlaceType())
                        .address(placeResponse.getAddress())
                        .address_bn(placeResponse.getAddress_bn())
                        .area(placeResponse.getArea())
                        .area_bn(placeResponse.getArea_bn())
                        .city(placeResponse.getCity())
                        .city_bn(placeResponse.getCity_bn())
                        .key(key)
                        .postCode(placeResponse.getPostCode())
                        .location(placeResponse.getLocation())
                        .geoLocation(new GeoJsonPoint(placeResponse.getLocation().getLongitude(), placeResponse.getLocation().getLatitude()))
                        .build();

            } else {
                place.setAddress_bn(placeResponse.getAddress_bn());
                place.setArea_bn(placeResponse.getArea_bn());
                place.setCity_bn(placeResponse.getCity_bn());
                place.setPostCode(placeResponse.getPostCode());
                place.setKey(placeResponse.getAddress());
            }
            placeRepository.save(place);
        }

    }
}
