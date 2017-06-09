package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.BikeRecommendationDescriptor;
import com.gruuf.services.Recommendations;
import com.gruuf.web.actions.BaseBikeMetadataAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;

@Result(
        name = RecommendationsAction.TO_RECOMMENDATIONS,
        location = "recommendations", type = "redirectAction",
        params = {"bikeMetadataId", "%{bikeMetadataId}"})
@Tokens(Token.ADMIN)
public class RecommendationsAction extends BaseBikeMetadataAction {

    private static Logger LOG = LogManager.getLogger(RecommendationsAction.class);

    public static final String TO_RECOMMENDATIONS = "to-recommendations";

    private Recommendations recommendations;

    private String bikeMetadataId;
    private String bikeRecommendationId;

    @Action("recommendations")
    public String execute() {
        return SUCCESS;
    }

    @Action("approve-recommendation")
    public String approveRecommendation() {
        if (StringUtils.isEmpty(bikeRecommendationId)) {
            LOG.debug("Nothing to approve, redirecting back to list");
        } else {
            LOG.debug("Approving recommendation {}", bikeRecommendationId);
            BikeRecommendation bikeRecommendation = recommendations.get(bikeRecommendationId);
            bikeRecommendation = BikeRecommendation.create(bikeRecommendation).withApproved().build();
            recommendations.put(bikeRecommendation);
        }

        return TO_RECOMMENDATIONS;
    }

    public List<BikeRecommendationDescriptor> getList() {
        List<BikeRecommendationDescriptor> result = new ArrayList<>();

        if (StringUtils.isNoneEmpty(bikeMetadataId)) {
            BikeMetadata bikeMetadata = bikeMetadataStore.get(bikeMetadataId);
            for (BikeRecommendation recommendation : recommendations.listAllBy(bikeMetadata)) {
                result.add(new BikeRecommendationDescriptor(currentUser.getUserLocale(), recommendation));
            }
        }

        return result;
    }

    @Inject
    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

    public String getBikeMetadataId() {
        return bikeMetadataId;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

    public void setBikeRecommendationId(String bikeRecommendationId) {
        this.bikeRecommendationId = bikeRecommendationId;
    }
}
