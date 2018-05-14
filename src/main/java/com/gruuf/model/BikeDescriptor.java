package com.gruuf.model;

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
        return new BikeMetadataDescriptor(metadata).getFullName();
    }
}
