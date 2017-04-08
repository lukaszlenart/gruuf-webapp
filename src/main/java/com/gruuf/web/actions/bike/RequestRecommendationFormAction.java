package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.EventType;
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
        @Result(name = RequestRecommendationFormAction.TO_RECOMMENDATIONS, location = "recommendations", type = "redirectAction", params = {
                "bikeId", "${bikeId}"
        }),
        @Result(name = INPUT, location = "bike/request-recommendation-input")
})
@BikeRestriction
public class RequestRecommendationFormAction extends BaseBikeMetadataAction {

    public static final String TO_RECOMMENDATIONS = "to-recommendations";

    private static Logger LOG = LogManager.getLogger(RequestRecommendationFormAction.class);

    private Recommendations recommendations;
    private EventTypes eventTypes;

    private String eventTypeId;
    private String englishDescription;
    private RecommendationSource source;
    private boolean notify;
    private Boolean monthlyReview = false;
    private Integer monthPeriod = 12;
    private Boolean mileageReview = false;
    private Integer mileagePeriod = 10000;

    @SkipValidation
    public String execute() {
        LOG.debug("Showing recommendation request form");
        return INPUT;
    }

    @Action("update-recommendation")
    public String updateRecommendation() {
        LOG.debug("Requesting new recommendation");

        BikeRecommendation recommendation = BikeRecommendation.create()
                .withBikeMetadataId(selectedBike.getBikeMetadataId())
                .withEventTypeId(eventTypeId)
                .withEnglishDescription(englishDescription)
                .withSource(source)
                .withNotify(notify)
                .withMonthPeriod(monthlyReview, monthPeriod)
                .withMileagePeriod(mileageReview, mileagePeriod)
                .withRequestedBy(currentUser)
                .build();

        recommendations.put(recommendation);

        addActionMessage(getText("recommendations.newRequestSubmitted"));

        return TO_RECOMMENDATIONS;
    }

    public List<EventType> getEventTypes() {
        return eventTypes.listAllowedEventTypes();
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

    public BikeMetadataOption getBikeMetadata() {
        return new BikeMetadataOption(selectedBike.getBikeMetadata());
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEnglishDescription() {
        return englishDescription;
    }

    public void setEnglishDescription(String englishDescription) {
        this.englishDescription = englishDescription;
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

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
