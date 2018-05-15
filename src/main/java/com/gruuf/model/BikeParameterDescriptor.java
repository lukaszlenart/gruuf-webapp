package com.gruuf.model;

public class BikeParameterDescriptor {

    private BikeParameter bikeParameter;

    public BikeParameterDescriptor(BikeParameter bikeParameter) {
        this.bikeParameter = bikeParameter;
    }

    public Markdown getDescription() {
        return bikeParameter.getDescription();
    }

    public String getValue() {
        return bikeParameter.getValue();
    }

    public String getPartNumber() {
        return bikeParameter.getPartNumber();
    }

    public String getApprovedKey() {
        return "boolean." + bikeParameter.isApproved();
    }

}
