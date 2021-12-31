package com.jachai.map.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BariKoiSearchResponse {
    private Double latitude;
    private Double longitude;
    private String address;
    private String address_bn;
    private String city;
    private String city_bn;
    private String area;
    private String area_bn;
    private String postCode;
    private String placeType;
}
