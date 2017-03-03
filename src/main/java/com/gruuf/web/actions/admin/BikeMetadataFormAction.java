package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.model.BikeMetadata;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = "to-bike-metadata", location = "bike-metadata", type = "redirectAction"),
        @Result(name = INPUT, location = "admin/bike-metadata-input")
})
@Tokens(Token.ADMIN)
public class BikeMetadataFormAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(BikeMetadataFormAction.class);

    private BikeMetadataStore bikeMetadataStore;

    private String bikeMetadataId;

    private String manufacturer;
    private String model;
    private Integer productionStartYear;
    private Integer productionEndYear;

    @SkipValidation
    public String execute() {
        if (StringUtils.isEmpty(bikeMetadataId)) {
            LOG.debug("Showing bike metadata create form");
        } else {
            LOG.debug("Showing bike metadata edit form");
            BikeMetadata bikeMetadata = bikeMetadataStore.get(bikeMetadataId);
            manufacturer = bikeMetadata.getManufacturer();
            model = bikeMetadata.getModel();
            productionStartYear = bikeMetadata.getProductionStartYear();
            productionEndYear = bikeMetadata.getProductionEndYear();
        }
        return INPUT;
    }

    @Action("update-bike-metadata")
    public String updateBikeMetadata() {
        if (StringUtils.isEmpty(bikeMetadataId)) {
            LOG.debug("Creating new bike metadata for {}-{}", manufacturer, model);

            BikeMetadata bikeMetadata = BikeMetadata.create()
                    .withManufacturer(manufacturer)
                    .withModel(model)
                    .withProductionStartYear(productionStartYear)
                    .withProductionEndYear(productionEndYear)
                    .withRequester(currentUser)
                    .build();

            BikeMetadata result = bikeMetadataStore.put(bikeMetadata);
            LOG.debug("New bike metadata created: {}", result);
        } else {
            LOG.debug("Updating existing bike metadata {}-{}", manufacturer, model);

            BikeMetadata bikeMetadata = bikeMetadataStore.get(bikeMetadataId);
            bikeMetadata = BikeMetadata.create(bikeMetadata)
                    .withManufacturer(manufacturer)
                    .withModel(model)
                    .withProductionStartYear(productionStartYear)
                    .withProductionEndYear(productionEndYear)
                    .withRequester(currentUser)
                    .build();
            bikeMetadataStore.put(bikeMetadata);
        }

        return "to-bike-metadata";
    }

    @Inject
    public void setBikeMetadataStore(BikeMetadataStore bikeMetadataStore) {
        this.bikeMetadataStore = bikeMetadataStore;
    }

    public String getBikeMetadataId() {
        return bikeMetadataId;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @RequiredStringValidator
    @StringLengthFieldValidator(minLength = "2")
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    @RequiredStringValidator
    @StringLengthFieldValidator(minLength = "2")
    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionStartYear() {
        return productionStartYear;
    }

    @RequiredFieldValidator
    public void setProductionStartYear(Integer productionStartYear) {
        this.productionStartYear = productionStartYear;
    }

    public Integer getProductionEndYear() {
        return productionEndYear;
    }

    @RequiredFieldValidator
    public void setProductionEndYear(Integer productionEndYear) {
        this.productionEndYear = productionEndYear;
    }

}