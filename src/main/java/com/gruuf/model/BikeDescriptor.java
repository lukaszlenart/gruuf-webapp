package com.gruuf.model;

import lombok.ToString;

@ToString
public class BikeDescriptor {

    private Bike bike;
    private Double totalCosts;

    public BikeDescriptor(Bike bike) {
        this(bike, null);
    }

    public BikeDescriptor(Bike bike, Double totalCosts) {
        this.bike = bike;
        this.totalCosts = totalCosts;
    }

    public String getId() {
        return bike.getId();
    }

    public String getBikeMetadataId() {
        return bike.getBikeMetadataId();
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

    public boolean isShowMileage() {
        return bike.isShowMileage();
    }

    public boolean isShowMth() {
        return bike.isShowMth();
    }

    public boolean isEditable() {
        return bike.getStatus() == BikeStatus.NORMAL;
    }

    public Double getTotalCosts() {
        return totalCosts;
    }

}
