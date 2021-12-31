package com.jachai.map.dto.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BariKoiSearchListResponseRest extends SimpleMessageResponseREST{
    private List<BariKoiSearchResponse> places;
}
