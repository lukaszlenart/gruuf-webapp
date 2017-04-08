package com.gruuf.web.actions;

import com.gruuf.model.BikeMetadata;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.web.actions.bike.BaseBikeAction;
import com.gruuf.web.actions.bike.BikeMetadataOption;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;

import static com.gruuf.web.actions.BaseAction.JSON;

@Result(name = JSON, type = "json", params = {"root", "metadataOptions"})
abstract public class BaseBikeMetadataAction extends BaseBikeAction {

    private static final Logger LOG = LogManager.getLogger(BaseBikeMetadataAction.class);

    protected BikeMetadataStore bikeMetadataStore;

    @Action("bike-metadata-json")
    public String execute() {
        return JSON;
    }

    public List<BikeMetadataOption> getMetadataOptions() {
        List<BikeMetadata> metaData = bikeMetadataStore.list();

        LOG.debug("Found meta data {}", metaData);

        List<BikeMetadataOption> result = new ArrayList<>();
        for (BikeMetadata metaDatum : metaData) {
            result.add(new BikeMetadataOption(metaDatum));
        }
        return result;
    }

    @Inject
    public void setBikeMetadataStore(BikeMetadataStore bikeMetadataStore) {
        this.bikeMetadataStore = bikeMetadataStore;
    }

}
