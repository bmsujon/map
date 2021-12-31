package com.jachai.map.service;

import com.jachai.map.dto.rest.response.BariKoiSearchListResponseRest;
import com.jachai.map.util.InternalRESTProvider;
import com.jachai.map.util.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class BariKoiRPCService {
    @Value("${bari.koi.base.url}")
    private String bariKoiBaseUrl;
    @Value("${bari.koi.api.key}")
    private String apiKey;
    private final static String AUTOCOMPLETE = "/autocomplete/";


    public BariKoiSearchListResponseRest searchPlaces(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("q", key);
        params.put("bangla", "true");

        return (BariKoiSearchListResponseRest) InternalRESTProvider.doRest(
                HttpMethod.GET,
                RestUtils.getFullURI(bariKoiBaseUrl + AUTOCOMPLETE + apiKey + "/place", params),
                null,
                BariKoiSearchListResponseRest.class
        );
    }

}
