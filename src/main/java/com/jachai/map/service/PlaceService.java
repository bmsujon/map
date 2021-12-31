package com.jachai.map.service;

import com.jachai.map.dto.Location;
import com.jachai.map.dto.rest.response.BariKoiSearchListResponseRest;
import com.jachai.map.dto.rest.response.BariKoiSearchResponse;
import com.jachai.map.dto.rest.response.PlaceResponse;
import com.jachai.map.entity.Place;
import com.jachai.map.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        Page<PlaceResponse> placeResponses =  placeRepository.findAllByKeyContainsIgnoreCase(key, pageable);
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
        return ret;
    }

    @Async
    public void addPlaces(List<PlaceResponse> placeResponses, String key) {
        for ( PlaceResponse placeResponse : placeResponses ) {
            if (placeRepository.existsByAddress(placeResponse.getAddress()) == false ) {
                Place place = Place.builder()
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
                placeRepository.save(place);
            }
        }

    }
}
