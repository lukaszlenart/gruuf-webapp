package com.gruuf.services;

import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeRecommendation;

import java.util.List;

public class Recommendations extends Reindexable<BikeRecommendation> {

    public Recommendations() {
        super(BikeRecommendation.class);
    }

    public List<BikeRecommendation> findByBikeMetadata(BikeMetadata bikeMetadata) {
        return findBy("bikeMetadataId =", bikeMetadata);
    }

}
