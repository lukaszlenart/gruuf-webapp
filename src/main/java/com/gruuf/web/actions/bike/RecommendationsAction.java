package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;

@BikeRestriction
public class RecommendationsAction extends BaseBikeAction {

    public String execute() throws Exception {
        return SUCCESS;
    }

}
