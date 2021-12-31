package com.jachai.map.dto.rest.response;

import com.jachai.map.dto.Location;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PlaceResponse implements Serializable {

    private Location location;
    private String address;
    private String address_bn;
    private String city;
    private String city_bn;
    private String area;
    private String area_bn;
    private String postCode;
    private String placeType;

}
