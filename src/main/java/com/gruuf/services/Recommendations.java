package com.gruuf.services;

import com.googlecode.objectify.Ref;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.User;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

public class Recommendations extends Reindexable<BikeRecommendation> {

    public Recommendations() {
        super(BikeRecommendation.class);
    }

    public static boolean matchesPeriod(BikeEvent bikeEvent, BikeRecommendation recommendation, List<BikeEvent> bikeEvents) {
        boolean result = false;

        if (recommendation.isMonthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId())) {

                    DateTime from = new DateTime(bikeEvent.getRegisterDate());
                    DateTime to = from.plusMonths(recommendation.getMonthPeriod());

                    result = to.isAfterNow();

                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMileagePeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && bikeEvent.isMileage() && event.isMileage()) {
                    result = (bikeEvent.getMileage() - event.getMileage()) <= recommendation.getMileagePeriod();
                    if (result) {
                        break;
                    }
                }
            }
        }

        if (recommendation.isMthPeriod()) {
            for (BikeEvent event : bikeEvents) {
                if (!event.getId().equals(bikeEvent.getId()) && bikeEvent.isMth() && event.isMth()) {
                    result = (bikeEvent.getMth() - event.getMth()) <= recommendation.getMthPeriod();
                    if (result) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    @Override
    protected boolean shouldReindex() {
        // change to true when migrating data
        return false;
    }

    public List<BikeRecommendation> listApprovedByBike(User currentUser, Bike bike) {
        List<BikeRecommendation> approvedForAll;
        if (bike.getRegistrationCountry() == null) {
            approvedForAll = filter("bikeMetadataId =", null)
                    .filter("approved =", Boolean.TRUE)
                    .list();
        } else {
            approvedForAll = filter("bikeMetadataId =", null)
                    .filter("country = ", bike.getRegistrationCountry())
                    .filter("approved =", Boolean.TRUE)
                    .list();
        }
        List<BikeRecommendation> approved = filter("bikeMetadataId =", bike.getBikeMetadata()).filter("approved =", Boolean.TRUE).list();
        List<BikeRecommendation> unapproved = filter("bikeMetadataId =", bike.getBikeMetadata()).filter("approved =", Boolean.FALSE).list();

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

    public List<BikeRecommendation> listFor(BikeMetadata bikeMetadata) {
        List<BikeRecommendation> approvedForAll = filter("bikeMetadataId =", null).filter("approved =", Boolean.TRUE).list();
        List<BikeRecommendation> approved = filter("bikeMetadataId =", bikeMetadata).filter("approved =", Boolean.TRUE).list();

        approvedForAll.addAll(approved);

        return approvedForAll;
    }
}
