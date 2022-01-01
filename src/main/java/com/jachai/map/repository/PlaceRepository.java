package com.jachai.map.repository;

import com.jachai.map.dto.rest.response.PlaceResponse;
import com.jachai.map.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {
    Place findByAddress(String address);
    Page<PlaceResponse> findAllByKeyIgnoreCase(String key, Pageable pageable);
    Boolean existsByAddress(String address);
}
