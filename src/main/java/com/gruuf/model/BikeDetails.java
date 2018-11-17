package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.gruuf.web.actions.bike.BikeMetadataOption;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BikeDetails {

    private Bike bike;
    private User currentUser;
    private List<BikeEventDescriptor> events;
    private Long mileage;
    private Long mth;
    private ArrayList<AttachmentDescriptor> attachments;

    public static BikeDetails create(Bike bike) {
        return new BikeDetails(bike);
    }

    private BikeDetails(Bike bike) {
        this.bike = bike;
    }

    public BikeDetails withUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public BikeDetails withHistory(UserLocale locale, List<BikeEvent> bikeEvents, Long currentMileage, Long currentMth) {
        mileage = currentMileage;
        mth = currentMth;

        events = new ArrayList<>();
        for (BikeEvent event : bikeEvents) {
            events.add(new BikeEventDescriptor(locale, event, currentMileage, currentMth));
        }
        return this;
    }

    public BikeDescriptor getBike() {
        Double totalCosts = events.stream()
            .filter(event -> event.getCost() != null)
            .map(BikeEventDescriptor::getCost)
            .reduce((cost, acc) -> acc + cost)
            .orElse(0.0);
        return new BikeDescriptor(bike, totalCosts);
    }

    public BikeMetadataOption getMetadata() {
        return new BikeMetadataOption(bike.getBikeMetadata());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<BikeEventDescriptor> getEvents() {
        return events;
    }

    public Long getMileage() {
        return mileage;
    }

    public Long getMth() {
        return mth;
    }

    public boolean hasAttachments(Ref<BikeEvent> bikeEventRef) {
        return this.attachments.stream()
            .filter(att -> att.belongsTo(bikeEventRef))
            .collect(Collectors.toList())
            .size() > 0;
    }

    public ArrayList<AttachmentDescriptor> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "BikeDetails{" +
            "bike=" + bike +
            ", currentUser=" + currentUser +
            ", events=" + events +
            ", mileage=" + mileage +
            '}';
    }

    public BikeDetails withAttachments(String rootUrl, List<Attachment> attachments) {
        this.attachments = new ArrayList<>();
        for (Attachment attachment : attachments) {
            this.attachments.add(new AttachmentDescriptor(rootUrl, attachment));
        }
        return this;
    }
}
