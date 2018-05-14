package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeParameter;

import java.util.List;

public class BikeParameters extends Reindexable<BikeParameter> {

    public BikeParameters() {
        super(BikeParameter.class);
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return false;
    }

    public List<BikeParameter> findByBikeMetadata(BikeMetadata bikeMetadata) {
        return findBy("bikeMetadata", Ref.create(bikeMetadata));
    }
}
