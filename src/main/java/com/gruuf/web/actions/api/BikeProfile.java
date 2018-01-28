package com.gruuf.web.actions.api;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;

import java.util.List;

public class BikeProfile {

    private final String id;
    private final String name;
    private final BikeMetadataProfile metadata;
    private final String vin;
    private final Long mileage;
    private final Long mth;

    public BikeProfile(Bike bike, Long mileage, Long mth) {
        this.id = bike.getId();
        this.name = bike.getName();
        this.metadata = new BikeMetadataProfile(bike.getBikeMetadata());
        this.vin = bike.getVin();
        this.mileage = mileage;
        this.mth = mth;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BikeMetadataProfile getMetadata() {
        return metadata;
    }

    public String getVin() {
        return vin;
    }

    public Long getMileage() {
        return mileage;
    }

    public Long getMth() {
        return mth;
    }
}
