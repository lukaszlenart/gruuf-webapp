package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.EventTypeDescriptor;
import com.gruuf.model.RecommendationSource;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Recommendations;
import com.gruuf.web.actions.BaseBikeMetadataAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = RecommendationFormAction.TO_RECOMMENDATIONS, location = "recommendations", type = "redirectAction", params = {
                "bikeMetadataId", "${bikeMetadataId}"
        }),
        @Result(name = INPUT, location = "admin/recommendation-input")
})
@Tokens(Token.ADMIN)
public class RecommendationFormAction extends BaseBikeMetadataAction {

    public static final String TO_RECOMMENDATIONS = "to-recommendations";

    private static Logger LOG = LogManager.getLogger(RecommendationFormAction.class);

    private Recommendations recommendations;
    private EventTypes eventTypes;

    private String recommendationId;

    private String bikeMetadataId;
    private String eventTypeId;
    private String description;
    private RecommendationSource source;
    private boolean notify;
    private Boolean monthlyReview = false;
    private Integer monthPeriod = 12;
    private Boolean mileageReview = false;
    private Integer mileagePeriod = 10000;
    private Boolean mthReview = false;
    private Integer mthPeriod = 40;

    @SkipValidation
    public String execute() {
        if (StringUtils.isEmpty(recommendationId)) {
            LOG.debug("Showing recommendation create form");
        } else {
            LOG.debug("Showing recommendation edit form for id = {}", recommendationId);
            BikeRecommendation recommendation = recommendations.get(recommendationId);
            bikeMetadataId = recommendation.getBikeMetadataId();
            eventTypeId = recommendation.getEventTypeId();
            description = recommendation.getDescription().getContent();
            source = recommendation.getSource();
            notify = recommendation.isNotify();
            monthlyReview = recommendation.getMonthPeriod() != null;
            if (monthlyReview) {
                monthPeriod = recommendation.getMonthPeriod();
            }
            mileageReview = recommendation.getMileagePeriod() != null;
            if (mileageReview) {
                mileagePeriod = recommendation.getMileagePeriod();
            }
            mthReview = recommendation.getMthPeriod() != null;
            if (mthReview) {
                mthPeriod = recommendation.getMthPeriod();
            }
        }
        return INPUT;
    }

    @Action("update-recommendation")
    public String updateRecommendation() {
        BikeRecommendation.BikeRecommendationBuilder builder;
        if (StringUtils.isEmpty(recommendationId)) {
            LOG.debug("Creating new recommendation");
            builder = BikeRecommendation.create()
                    .withRequestedBy(currentUser)
                    .withApproved();
        } else {
            LOG.debug("Updating existing recommendation id = {}", recommendationId);
            BikeRecommendation recommendation = recommendations.get(recommendationId);
            builder = BikeRecommendation.create(recommendation);
        }

        BikeRecommendation recommendation = builder
                .withBikeMetadataId(bikeMetadataId)
                .withEventTypeId(eventTypeId)
                .withDescription(description)
                .withSource(source)
                .withNotify(notify)
                .withMonthPeriod(monthlyReview, monthPeriod)
                .withMileagePeriod(mileageReview, mileagePeriod)
                .withMthPeriod(mthReview, mthPeriod)
                .withRequestedByIfNull(currentUser)
                .build();

        recommendations.put(recommendation);

        return TO_RECOMMENDATIONS;
    }

    public List<EventTypeDescriptor> getEventTypes() {
        return eventTypes.listApproved(currentUser);
    }

    public List<BikeRecommendation> getList() {
        List<BikeRecommendation> bikeRecommendations = recommendations.list();

        List<BikeRecommendation> result = new ArrayList<>();

        if (StringUtils.isNoneEmpty(bikeMetadataId)) {
            for (BikeRecommendation bikeRecommendation : bikeRecommendations) {
                if (bikeMetadataId.equals(bikeRecommendation.getBikeMetadata().getId())) {
                    result.add(bikeRecommendation);
                }
            }
        }

        return result;
    }

    @Inject
    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

    @Inject
    public void setEventTypes(EventTypes eventTypes) {
        this.eventTypes = eventTypes;
    }

    public RecommendationSource[] getAllSources() {
        return RecommendationSource.values();
    }

    public String getBikeMetadataId() {
        return bikeMetadataId;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecommendationSource getSource() {
        return source;
    }

    public void setSource(RecommendationSource source) {
        this.source = source;
    }

    public Boolean getMonthlyReview() {
        return monthlyReview;
    }

    public void setMonthlyReview(Boolean monthlyReview) {
        this.monthlyReview = monthlyReview;
    }

    public Integer getMonthPeriod() {
        return monthPeriod;
    }

    public void setMonthPeriod(Integer monthPeriod) {
        this.monthPeriod = monthPeriod;
    }

    public Boolean getMileageReview() {
        return mileageReview;
    }

    public void setMileageReview(Boolean mileageReview) {
        this.mileageReview = mileageReview;
    }

    public Integer getMileagePeriod() {
        return mileagePeriod;
    }

    public void setMileagePeriod(Integer mileagePeriod) {
        this.mileagePeriod = mileagePeriod;
    }

    public Boolean getMthReview() {
        return mthReview;
    }

    public void setMthReview(Boolean mthReview) {
        this.mthReview = mthReview;
    }

    public Integer getMthPeriod() {
        return mthPeriod;
    }

    public void setMthPeriod(Integer mthPeriod) {
        this.mthPeriod = mthPeriod;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
