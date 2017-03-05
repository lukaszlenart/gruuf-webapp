package com.gruuf.web.actions.bike;

import com.gruuf.model.BikeMetadata;

public class BikeMetadataOption {

    private String id;
    private String name;

    public BikeMetadataOption(BikeMetadata metadata) {
        if (metadata == null) {
            this.id = null;
            this.name = "";
        } else {
            this.id = metadata.getId();
            this.name = metadata.getManufacturer() + " " + metadata.getModel() + " (" + metadata.getProductionStartYear() + "-" + metadata.getProductionEndYear() + ")";
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
