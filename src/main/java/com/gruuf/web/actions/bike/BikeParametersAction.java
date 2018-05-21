package com.gruuf.web.actions.bike;

import com.googlecode.objectify.Ref;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeMetadataDescriptor;
import com.gruuf.model.BikeParameter;
import com.gruuf.model.BikeParameterDescriptor;
import com.gruuf.model.Markdown;
import com.gruuf.services.BikeMetadataStore;
import com.gruuf.services.BikeParameters;
import com.gruuf.services.MailBox;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.List;
import java.util.stream.Collectors;

import static com.gruuf.web.GlobalResult.GARAGE;
import static com.opensymphony.xwork2.Action.INPUT;

@Results({
    @Result(name = BikeParametersAction.TO_INPUT,
        location = "bike-parameters",
        type = "redirectAction",
        params = {"bikeMetadataId", "${bikeMetadataId}"}
    ),
    @Result(name = INPUT, location = "bike/bike-parameters")
})
@InterceptorRef("defaultWithMessages")
public class BikeParametersAction extends BaseAction implements Preparable {

    public static final String TO_INPUT = "to-bike-parameters";

    private static Logger LOG = LogManager.getLogger(BikeParametersAction.class);

    @Inject
    private MailBox mailBox;
    @Inject
    private BikeMetadataStore store;
    @Inject
    private BikeParameters parameters;

    private String bikeMetadataId;
    private BikeMetadata bikeMetadata;

    private Markdown description;
    private String value;
    private String partNumber;

    @Override
    public void prepare() {
        LOG.debug("Fetching Bike Metadata related to [{}]", bikeMetadataId);
        bikeMetadata = store.get(bikeMetadataId);
    }

    @SkipValidation
    public String execute() {
        return SUCCESS;
    }

    @Action("request-bike-parameter")
    public String requestBikeParameter() {
        LOG.debug("Requesting new bike parameter");

        if (bikeMetadata != null) {
            BikeParameter parameter = BikeParameter.create(bikeMetadata)
                .withDescription(description)
                .withValue(value)
                .withPartNumber(partNumber)
                .withRequestedBy(currentUser)
                .build();

            parameter = parameters.put(parameter);

            LOG.debug("New bike parameter was created: {}", parameter);
            mailBox.notifyAdmin("New Bike Parameter request", "A new Bike Parameter was requested", parameter);
            addActionMessage(getText("general.newRequestSubmitted"));

            return TO_INPUT;
        } else {
            addActionError(getText("bike.bikeMetadataIdIsMissing"));
            return GARAGE;
        }
    }

    public BikeMetadataDescriptor getBikeMetadata() {
        return new BikeMetadataDescriptor(bikeMetadata);
    }

    public List<BikeParameterDescriptor> getBikeParameters() {
        return parameters
            .findByBikeMetadata(bikeMetadata)
            .stream()
            .filter(param -> param.isApproved() || param.getRequestedBy().equivalent(Ref.create(currentUser)))
            .map(BikeParameterDescriptor::new)
            .collect(Collectors.toList());
    }

    public String getBikeMetadataId() {
        return bikeMetadataId;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

    public Markdown getDescription() {
        return description;
    }

    @RequiredFieldValidator
    public void setDescription(Markdown description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    @RequiredStringValidator
    public void setValue(String value) {
        this.value = value;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

}
