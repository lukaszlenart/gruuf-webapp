package com.gruuf.web.actions.bike;

import com.gruuf.model.BikeMetadata;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.services.MailBox;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import static com.opensymphony.xwork2.Action.INPUT;

@Results({
        @Result(name = "to-input", location = "request-bike-metadata", type = "redirectAction"),
        @Result(name = INPUT, location = "bike/request-bike-metadata-input")
})
@InterceptorRef("defaultWithMessages")
public class RequestBikeMetadataAction extends BaseAction {

    private static Logger LOG = LogManager.getLogger(RequestBikeMetadataAction.class);

    private BikeMetadataStore bikeMetadataStore;
    private MailBox mailBox;

    private String manufacturer;
    private String model;
    private Integer productionStartYear;
    private Integer productionEndYear;

    @SkipValidation
    public String execute() {
        return INPUT;
    }

    @Action("request-bike-metadata-submit")
    public String updateBikeMetadata() {
        LOG.debug("Creating new bike metadata for {}-{}", manufacturer, model);

        BikeMetadata bikeMetadata = BikeMetadata.createRequest()
                .withManufacturer(manufacturer)
                .withModel(model)
                .withProductionStartYear(productionStartYear)
                .withProductionEndYear(productionEndYear)
                .withRequester(currentUser)
                .build();

        BikeMetadata result = bikeMetadataStore.put(bikeMetadata);

        mailBox.notifyAdmin("New Bike Metadata request", "A new Bike Metadata was requested", result);

        LOG.debug("New bike metadata created: {}", result);

        addActionMessage(getText("bikeMetadata.newRequestSubmitted"));

        return "to-input";
    }

    @Inject
    public void setBikeMetadataStore(BikeMetadataStore bikeMetadataStore) {
        this.bikeMetadataStore = bikeMetadataStore;
    }

    @Inject
    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
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