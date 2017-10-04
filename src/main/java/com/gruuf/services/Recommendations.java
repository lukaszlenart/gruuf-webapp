package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.User;

import java.util.List;

public class Recommendations extends Reindexable<BikeRecommendation> {

    public Recommendations() {
        super(BikeRecommendation.class);
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return true;
    }

    public List<BikeRecommendation> listApprovedByBikeMetadata(User currentUser, BikeMetadata bikeMetadata) {
        List<BikeRecommendation> approvedForAll = filter("bikeMetadataId =", null).filter("approved =", Boolean.TRUE).list();
        List<BikeRecommendation> approved = filter("bikeMetadataId =", bikeMetadata).filter("approved =", Boolean.TRUE).list();
        List<BikeRecommendation> unapproved = filter("bikeMetadataId =", bikeMetadata).filter("approved =", Boolean.FALSE).list();

        approved.addAll(approvedForAll);

        for (BikeRecommendation recommendation : unapproved) {
            if (recommendation.getRequestedBy().equivalent(Ref.create(currentUser))) {
                approved.add(recommendation);
            }
        }

        return approved;
    }

    public List<BikeRecommendation> listAllBy(BikeMetadata bikeMetadata) {
        return filter("bikeMetadataId =", bikeMetadata).list();
    }

    public List<BikeRecommendation> listForAllBikes() {
        return filter("bikeMetadataId =", null).list();
    }
}
