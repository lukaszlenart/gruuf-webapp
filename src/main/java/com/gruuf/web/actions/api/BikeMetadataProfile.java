package com.gruuf.web.actions.api;

import com.gruuf.model.BikeMetadata;

public class BikeMetadataProfile {
    private final String manufacturer;
    private final String model;
    private final Integer productionStartYear;
    private final Integer productionEndYear;

    public BikeMetadataProfile(BikeMetadata bikeMetadata) {
        this.manufacturer = bikeMetadata.getManufacturer();
        this.model = bikeMetadata.getModel();
        this.productionStartYear = bikeMetadata.getProductionStartYear();
        this.productionEndYear = bikeMetadata.getProductionEndYear();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Integer getProductionStartYear() {
        return productionStartYear;
    }

    public Integer getProductionEndYear() {
        return productionEndYear;
    }
}
