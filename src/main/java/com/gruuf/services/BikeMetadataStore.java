package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.User;

import java.util.List;

public class BikeMetadataStore extends Storable<BikeMetadata> {

    public BikeMetadataStore() {
        super(BikeMetadata.class);
    }

    public List<BikeMetadata> listApproved(User currentUser) {
        List<BikeMetadata> approved = filter("approved", Boolean.TRUE).list();
        List<BikeMetadata> unapproved = filter("approved", Boolean.FALSE).list();

        for (BikeMetadata metadata : unapproved) {
            if (metadata.getRequestedBy().equivalent(Ref.create(currentUser))) {
                approved.add(metadata);
            }
        }

        return approved;
    }
}
