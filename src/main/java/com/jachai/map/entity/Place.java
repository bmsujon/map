package com.jachai.map.entity;

import com.jachai.map.dto.Location;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("places")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Location location;
    private String address;
    private String address_bn;
    private String city;
    private String city_bn;
    private String area;
    private String area_bn;
    private String postCode;
    private String placeType;

    private String key;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint geoLocation;
}
