package com.gruuf.model;

import java.util.Objects;

public class BikeMetadataDescriptor {

    private BikeMetadata bikeMetadata;

    public BikeMetadataDescriptor(BikeMetadata bikeMetadata) {
        this.bikeMetadata = bikeMetadata;
    }

    public String getFullName() {
        return String.format("%s %s (%d - %s)",
            bikeMetadata.getManufacturer(),
            bikeMetadata.getModel(),
            bikeMetadata.getProductionStartYear(),
            Objects.toString(bikeMetadata.getProductionEndYear(), "")
        );
    }
}
