package com.jachai.map.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BariKoiGeoCodeResponse {
    private String address;
    private String area;
    private String city;
}
