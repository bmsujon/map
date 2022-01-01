package com.jachai.map.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BariKoiGeoCodeResponseRest extends SimpleMessageResponseREST{
    private BariKoiGeoCodeResponse place;
}
