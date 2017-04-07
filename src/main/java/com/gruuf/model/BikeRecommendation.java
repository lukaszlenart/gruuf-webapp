package com.gruuf.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;

@Entity
public class BikeRecommendation {

    @Id
    private String id;
    private Ref<EventType> eventTypeId;
    @Index
    private Ref<BikeMetadata> bikeMetadataId;
    private String englishDescription;
    @Index
    private RecommendationSource source;
    @Index
    private boolean notify;
    private Integer mileagePeriod = null;
    private Integer monthPeriod = null;

    private BikeRecommendation() {
    }

    public String getId() {
        return id;
    }

    public EventType getEventType() {
        return eventTypeId.get();
    }

    public BikeMetadata getBikeMetadata() {
        return bikeMetadataId.get();
    }

    public String getEnglishDescription() {
        return englishDescription;
    }

    public RecommendationSource getSource() {
        return source;
    }

    public String getBikeMetadataId() {
        return bikeMetadataId.getKey().getName();
    }

    public String getEventTypeId() {
        return eventTypeId.getKey().getName();
    }

    public Integer getMileagePeriod() {
        return mileagePeriod;
    }

    public Integer getMonthPeriod() {
        return monthPeriod;
    }

    public boolean isNotify() {
        return notify;
    }

    public static BikeRecommendationBuilder create() {
        return new BikeRecommendationBuilder();
    }

    public static BikeRecommendationBuilder create(BikeRecommendation bikeRecommendation) {
        return new BikeRecommendationBuilder(bikeRecommendation.getId());
    }

    public static class BikeRecommendationBuilder {
        private BikeRecommendation target;

        public BikeRecommendationBuilder() {
            this(GruufAuth.generateUUID());
        }

        public BikeRecommendationBuilder(String id) {
            target = new BikeRecommendation();
            target.id = id;
        }

        public BikeRecommendationBuilder withBikeMetadataId(String bikeMetadataId) {
            target.bikeMetadataId = Ref.create(Key.create(BikeMetadata.class,bikeMetadataId));
            return this;
        }

        public BikeRecommendationBuilder withEventTypeId(String eventTypeId) {
            target.eventTypeId = Ref.create(Key.create(EventType.class, eventTypeId));
            return this;
        }

        public BikeRecommendationBuilder withEnglishDescription(String englishDescription) {
            target.englishDescription = englishDescription;
            return this;
        }

        public BikeRecommendationBuilder withSource(RecommendationSource source) {
            target.source = source;
            return this;
        }

        public BikeRecommendationBuilder withMonthPeriod(Boolean monthlyReview, Integer monthPeriod) {
            if (monthlyReview != null && monthlyReview) {
                target.monthPeriod = monthPeriod;
            } else {
                target.monthPeriod = null;
            }
            return this;
        }

        public BikeRecommendationBuilder withMileagePeriod(Boolean mileageReview, Integer mileagePeriod) {
            if (mileageReview != null && mileageReview) {
                target.mileagePeriod = mileagePeriod;
            } else {
                target.mileagePeriod = null;
            }
            return this;
        }

        public BikeRecommendationBuilder withNotify(boolean notify) {
            target.notify = notify;
            return this;
        }

        public BikeRecommendation build() {
            return target;
        }
    }
}
