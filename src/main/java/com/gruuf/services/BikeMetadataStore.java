package com.gruuf.services;

import com.gruuf.model.BikeMetadata;

import java.util.List;

public class BikeMetadataStore extends Storable<BikeMetadata> {

    public BikeMetadataStore() {
        super(BikeMetadata.class);
    }

    public List<BikeMetadata> listApproved() {
        return filter("approved", Boolean.TRUE).list();
    }
}
