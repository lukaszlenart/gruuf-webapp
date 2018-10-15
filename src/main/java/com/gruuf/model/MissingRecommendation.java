package com.gruuf.model;

import com.gruuf.web.actions.tasks.DailyRecommendationCheckTask;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.joda.time.DateTime;

public class MissingRecommendation {

    private final BikeRecommendation recommendation;
    private final DailyRecommendationCheckTask.PeriodResult result;

    public static MissingRecommendation of(BikeRecommendation recommendation, DailyRecommendationCheckTask.PeriodResult result) {
        return new MissingRecommendation(recommendation, result);
    }

    public MissingRecommendation(BikeRecommendation recommendation, DailyRecommendationCheckTask.PeriodResult result) {
        this.recommendation = recommendation;
        this.result = result;
    }

    public boolean isNotify() {
        return recommendation.isNotify();
    }

    public EventType getEventType() {
        return recommendation.getEventType();
    }

    public Markdown getDescription() {
        return recommendation.getDescription();
    }

    public boolean isDateExpiration() {
        return result.getExpirationDate() != null;
    }

    public DateTime getExpirationDate() {
        return result.getExpirationDate();
    }

    public boolean isMileageExpiration() {
        return result.getExpirationMileage() != null;
    }

    public Long getExpirationMileage() {
        return result.getExpirationMileage();
    }

    public boolean isMthExpiration() {
        return result.getExpirationMth() != null;
    }

    public Long getExpirationMth() {
        return result.getExpirationMth();
    }
}
