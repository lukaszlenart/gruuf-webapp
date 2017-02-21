package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.services.Recommendations;
import com.gruuf.web.actions.BaseBikeMetadataAction;
import com.gruuf.web.actions.bike.BikeMetadataOption;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import java.util.ArrayList;
import java.util.List;

@Tokens(Token.ADMIN)
public class RecommendationsAction extends BaseBikeMetadataAction {

    private static Logger LOG = LogManager.getLogger(RecommendationsAction.class);

    private Recommendations recommendations;

    private String bikeMetadataId;

    @Action("recommendations")
    public String execute() {
        return SUCCESS;
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

    public String getBikeMetadataId() {
        return bikeMetadataId;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

}
