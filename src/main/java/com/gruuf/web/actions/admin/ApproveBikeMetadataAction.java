package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeMetadata;
import com.gruuf.web.actions.BaseBikeMetadataAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;

@Tokens(Token.ADMIN)
@Result(name = ApproveBikeMetadataAction.TO_BIKE_METADATA, location = "bike-metadata", type = "redirectAction")
public class ApproveBikeMetadataAction extends BaseBikeMetadataAction {

    public static final String TO_BIKE_METADATA = "to-bike-metadata";

    private static Logger LOG = LogManager.getLogger(ApproveBikeMetadataAction.class);

    private String bikeMetadataId;

    public String execute() {
        if (StringUtils.isEmpty(bikeMetadataId)) {
            LOG.debug("Nothing to approve, redirecting back to list");
        } else {
            LOG.debug("Approving metadata {}", bikeMetadataId);
            BikeMetadata bikeMetadata = bikeMetadataStore.get(bikeMetadataId);
            bikeMetadata = BikeMetadata.create(bikeMetadata).build();
            bikeMetadataStore.put(bikeMetadata);
        }
        return TO_BIKE_METADATA;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }
}
