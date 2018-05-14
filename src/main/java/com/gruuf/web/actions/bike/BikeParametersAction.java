package com.gruuf.web.actions.bike;

import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeMetadataDescriptor;
import com.gruuf.model.BikeParameterDescriptor;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.services.BikeParameters;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class BikeParametersAction extends BaseAction {

    private String bikeMetadataId;
    private BikeMetadata bikeMetadata;

    @Inject
    private BikeMetadataStore store;
    @Inject
    private BikeParameters parameters;

    public String execute() {
        bikeMetadata = store.get(bikeMetadataId);
        return SUCCESS;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

    public BikeMetadataDescriptor getBikeMetadata() {
        return new BikeMetadataDescriptor(bikeMetadata);
    }

    public List<BikeParameterDescriptor> getBikeParameters() {
        return parameters
            .findByBikeMetadata(bikeMetadata)
            .stream()
            .map(BikeParameterDescriptor::new)
            .collect(Collectors.toList());
    }
}
