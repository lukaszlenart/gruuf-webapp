package com.gruuf.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.AlsoLoad;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;
import org.apache.commons.lang3.StringUtils;

@Entity
public class BikeRecommendation {

    @Id
    private String id;
    private Ref<EventType> eventTypeId;
    @Index
    private Ref<BikeMetadata> bikeMetadataId;
    private Markdown description;
    @Index
    private RecommendationSource source;
    @Index
    private boolean notify;
    private Integer mileagePeriod = null;
    private Integer monthPeriod = null;
    private Integer mthPeriod = null;
    @Index
    private Country country = null;

    @Index
    private boolean approved = false;
    private Ref<User> requestedBy;

    private BikeRecommendation() {
    }

/*
    public void migrate(@AlsoLoad("englishDescription") String englishDescription) {
        if (description == null || StringUtils.isBlank(description.getContent())) {
            description = Markdown.of(englishDescription);
        }
    }
*/

    public String getId() {
        return id;
    }

    public EventType getEventType() {
        return eventTypeId.get();
    }

    public BikeMetadata getBikeMetadata() {
        return bikeMetadataId.get();
    }

    public Markdown getDescription() {
        return description;
    }

    public RecommendationSource getSource() {
        return source;
    }

    public String getBikeMetadataId() {
        if (bikeMetadataId == null) {
            return null;
        }
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

    public Integer getMthPeriod() {
        return mthPeriod;
    }

    public boolean isNotify() {
        return notify;
    }

    public boolean isApproved() {
        return approved;
    }

    public Ref<User> getRequestedBy() {
        return requestedBy;
    }

    public User getRequestedByUser() {
        return requestedBy == null ? null : requestedBy.get();
    }

    @Override
    public String toString() {
        return "BikeRecommendation{" +
                "id='" + id + '\'' +
                ", eventTypeId=" + eventTypeId +
                ", bikeMetadataId=" + bikeMetadataId +
                ", description='" + description + '\'' +
                ", source=" + source +
                ", notify=" + notify +
                ", mileagePeriod=" + mileagePeriod +
                ", monthPeriod=" + monthPeriod +
                ", approved=" + approved +
                ", requestedBy=" + requestedBy +
                '}';
    }

    public static BikeRecommendationBuilder create() {
        return new BikeRecommendationBuilder();
    }

    public static BikeRecommendationBuilder create(BikeRecommendation original) {
        return new BikeRecommendationBuilder(original);
    }

    public boolean isMileagePeriod() {
        return mileagePeriod != null;
    }

    public boolean isMonthPeriod() {
        return monthPeriod != null;
    }

    public boolean isMthPeriod() {
        return mthPeriod != null;
    }

    public Country getCountry() {
        return country;
    }

    public static class BikeRecommendationBuilder {
        private BikeRecommendation target;

        public BikeRecommendationBuilder() {
            target = new BikeRecommendation();
            target.id = GruufAuth.generateUUID();
        }

        public BikeRecommendationBuilder(BikeRecommendation original) {
            target = original;
        }

        public BikeRecommendationBuilder withBikeMetadataId(String bikeMetadataId) {
            if (StringUtils.isNotBlank(bikeMetadataId)) {
                target.bikeMetadataId = Ref.create(Key.create(BikeMetadata.class, bikeMetadataId));
            } else {
                target.bikeMetadataId = null;
            }
            return this;
        }

        public BikeRecommendationBuilder withEventTypeId(String eventTypeId) {
            target.eventTypeId = Ref.create(Key.create(EventType.class, eventTypeId));
            return this;
        }

        public BikeRecommendationBuilder withDescription(String description) {
            target.description = Markdown.of(description);
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

        public BikeRecommendationBuilder withMthPeriod(Boolean mthReview, Integer mthPeriod) {
            if (mthReview != null && mthReview) {
                target.mthPeriod = mthPeriod;
            } else {
                target.mthPeriod = null;
            }
            return this;
        }

        public BikeRecommendationBuilder withNotify(boolean notify) {
            target.notify = notify;
            return this;
        }

        public BikeRecommendationBuilder withRequestedBy(User user) {
            target.requestedBy = Ref.create(user);
            return this;
        }

        public BikeRecommendationBuilder withApproved() {
            target.approved = true;
            return this;
        }

        public BikeRecommendationBuilder withRequestedByIfNull(User user) {
            if (target.requestedBy == null) {
                target.requestedBy = Ref.create(user);
            }
            return this;
        }

        public BikeRecommendationBuilder withCountry(Country country) {
            target.country = country;
            return this;
        }

        public BikeRecommendation build() {
            return target;
        }
    }
}
