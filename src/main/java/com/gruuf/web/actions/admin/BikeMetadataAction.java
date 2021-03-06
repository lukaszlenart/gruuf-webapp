package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeMetadata;
import com.gruuf.web.actions.BaseBikeMetadataAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import java.util.List;

@Tokens(Token.ADMIN)
public class BikeMetadataAction extends BaseBikeMetadataAction {

    private static Logger LOG = LogManager.getLogger(BikeMetadataAction.class);

    @Action("bike-metadata")
    public String execute() {
        return SUCCESS;
    }

    public List<BikeMetadata> getList() {
        List<BikeMetadata> metaData = bikeMetadataStore.list();
        LOG.debug("Found meta data {}", metaData);
        return metaData;
    }

}
