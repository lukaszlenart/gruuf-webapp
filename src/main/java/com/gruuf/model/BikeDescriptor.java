package com.gruuf.model;

import java.util.Objects;

public class BikeDescriptor {

    private Bike bike;

    public BikeDescriptor(Bike bike) {
        this.bike = bike;
    }

    public String getName() {
        return bike.getName();
    }

    public String getVin() {
        return bike.getVin();
    }

    public User getOwner() {
        return bike.getOwner();
    }

    public Integer getModelYear() {
        return bike.getModelYear();
    }

    public String getProducerAndMake() {
        BikeMetadata metadata = bike.getBikeMetadata();
        return String.format("%s %s (%d - %s)",
                metadata.getManufacturer(),
                metadata.getModel(),
                metadata.getProductionStartYear(),
                Objects.toString(metadata.getProductionEndYear(), "")
        );
    }
}
