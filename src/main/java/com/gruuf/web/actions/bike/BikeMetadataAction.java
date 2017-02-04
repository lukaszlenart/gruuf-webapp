package com.gruuf.web.actions.bike;

import com.gruuf.model.BikeMetadata;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;

@InterceptorRefs({
        @InterceptorRef("gruufDefaultDev"),
        @InterceptorRef(value = "json")
})
@Result(type = "json", params = {"root", "list"})
public class BikeMetadataAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(BikeMetadataAction.class);

    private BikeMetadataStore bikeMetadataStore;

    @Action("bike-metadata")
    public String execute() {
        return SUCCESS;
    }

    public List<BikeMetadataOption> getList() {
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
