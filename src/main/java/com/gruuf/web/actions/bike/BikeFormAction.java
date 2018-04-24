package com.gruuf.web.actions.bike;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.Country;
import com.gruuf.web.GruufActions;
import com.gruuf.web.actions.BaseBikeMetadataAction;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Collections;
import java.util.List;

import static com.opensymphony.xwork2.Action.INPUT;

@Result(name = INPUT, location = "bike/bike-form")
@BikeRestriction
public class BikeFormAction extends BaseBikeMetadataAction implements Validateable {

    private static final Logger LOG = LogManager.getLogger(BikeFormAction.class);

    @SkipValidation
    public String execute() {
        if (selectedBike != null) {
            friendlyName = selectedBike.getName();
            bikeMetadataId = selectedBike.getBikeMetadataId();
            vin = selectedBike.getVin();
            modelYear = selectedBike.getModelYear();
            registrationCountry = selectedBike.getRegistrationCountry();
            if (registrationCountry == null) {
                registrationCountry = Country.fromLocale(currentUser.getUserLocale());
            }
            registrationPlate = selectedBike.getRegistrationPlate();
            mileage = bikeHistory.findCurrentMileage(selectedBike);
            mth = bikeHistory.findCurrentMth(selectedBike);
            currentMileage = mileage;
            currentMth = mth;
            showMileage = selectedBike.isShowMileage();
            showMth = selectedBike.isShowMth();
        }
        return INPUT;
    }

    @Action("bike-form-submit")
    public String bikeFormSubmit() {
        if (selectedBike == null) {
            LOG.debug("Creating new bike with name {} and vin {}", friendlyName, vin);
            Bike bike = Bike
                    .create(currentUser)
                    .withFriendlyName(friendlyName)
                    .withBikeMetadataId(bikeMetadataId)
                    .withVIN(vin)
                    .withModelYear(modelYear)
                    .withShowMileage(showMileage)
                    .withShowMth(showMth)
                    .withRegistrationCountry(registrationCountry)
                    .withRegistrationPlate(registrationPlate)
                    .build();

            selectedBike = garage.put(bike);
        } else {
            LOG.debug("Updating existing bike {}", selectedBike.getId());
            Bike bike = Bike.clone(selectedBike)
                    .withFriendlyName(friendlyName)
                    .withBikeMetadataId(bikeMetadataId)
                    .withVIN(vin)
                    .withModelYear(modelYear)
                    .withShowMileage(showMileage)
                    .withShowMth(showMth)
                    .withRegistrationCountry(registrationCountry)
                    .withRegistrationPlate(registrationPlate)
                    .build();

            selectedBike = garage.put(bike);
        }

        if (hasMileageChanged()) {
            LOG.debug("Updating mileage {} for bike {}", mileage, selectedBike);
            updateMileage(selectedBike);
        }
        if (hasMthChanged()) {
            LOG.debug("Updating mth {} for bike {}", mth, selectedBike);
            updateMth(selectedBike);
        }
        return GruufActions.GARAGE;
    }

    private boolean hasMileageChanged() {
        LOG.debug("Checking if provided mileage {} is greater than current mileage {}", mileage, currentMileage);
        return (currentMileage == null && mileage != null) ||
                (currentMileage != null  && mileage != null && mileage.compareTo(currentMileage) > 0);
    }

    private boolean hasMthChanged() {
        LOG.debug("Checking if provided mth {} is greater than current mth {}", mth, currentMth);
        return (currentMth == null && mth != null) ||
                (currentMth != null  && mth != null && mth.compareTo(currentMth) > 0);
    }

    private void updateMileage(Bike bike) {
        BikeEvent bikeEvent = BikeEvent.create(bike, currentUser)
                .withMileage(mileage)
                .withMth(mth)
                .withEventTypeId(Collections.singleton(eventTypes.getMileageEventType().getId()))
                .withDescription(getText("bike.systemMileageUpdate"))
                .withRegisterDate(DateTime.now().withTimeAtStartOfDay().toDate())
                .markAsSystem()
                .build();

        bikeHistory.put(bikeEvent);
    }

    private void updateMth(Bike bike) {
        BikeEvent bikeEvent = BikeEvent.create(bike, currentUser)
                .withMileage(mileage)
                .withMth(mth)
                .withEventTypeId(Collections.singleton(eventTypes.getMthEventType().getId()))
                .withDescription(getText("bike.systemMthUpdate"))
                .withRegisterDate(DateTime.now().withTimeAtStartOfDay().toDate())
                .markAsSystem()
                .build();

        bikeHistory.put(bikeEvent);
    }

    public void validateBikeFormSubmit() throws Exception {
        Bike existingBike = garage.findUniqueBy("vin", vin);

        if (selectedBike == null && existingBike != null) {
            LOG.debug("Creating new bike with VIN {} that was already registered as {}", vin, existingBike);
            addFieldError("vin", getText("bike.vinAlreadyUsed"));
        }

        if (selectedBike != null && existingBike != null
                && !selectedBike.getId().equals(existingBike.getId())) {

            LOG.debug("Updating existing bike {} with new VIN {} that was already registered {}",
                    existingBike, vin, selectedBike);
            addFieldError("vin", getText("bike.vinAlreadyUsed"));
        }

        if (currentMileage != null && mileage != null && currentMileage.compareTo(mileage) > 0) {
            LOG.debug("New mileage {} is less than current mileage {}", mileage, currentMileage);
            addFieldError("mileage", getText("bike.providedMileageIsLowerThanActual"));
        }

        if (currentMth != null && mth != null && currentMth.compareTo(mth) > 0) {
            LOG.debug("New mth {} is less than current mth {}", mth, currentMth);
            addFieldError("mth", getText("bike.providedMthIsLowerThanActual"));
        }
    }

    private String friendlyName;
    private String bikeMetadataId;
    private String vin;
    private Integer modelYear;
    private Country registrationCountry;
    private String registrationPlate;
    private Long mileage;
    private Long mth;
    private Long currentMileage;
    private Long currentMth;
    private boolean showMileage;
    private boolean showMth;

    public String getFriendlyName() {
        return friendlyName;
    }

    @RequiredStringValidator(key = "bike.friendlyNameIsRequired")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getBikeMetadataId() {
        return bikeMetadataId;
    }

    public void setBikeMetadataId(String bikeMetadataId) {
        this.bikeMetadataId = bikeMetadataId;
    }

    public String getVin() {
        return vin;
    }

    @RequiredStringValidator(key = "bike.vinIsRequired")
    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Country getRegistrationCountry() {
        return registrationCountry;
    }

    @RequiredFieldValidator(key = "bike.registrationCountryIsRequired")
    public void setRegistrationCountry(Country registrationCountry) {
        this.registrationCountry = registrationCountry;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public Country[] getAllCountries() {
        return Country.values();
    }

    public Long getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(Long currentMileage) {
        this.currentMileage = currentMileage;
    }

    public Long getMth() {
        return mth;
    }

    public void setMth(Long mth) {
        this.mth = mth;
    }

    public Long getCurrentMth() {
        return currentMth;
    }

    public void setCurrentMth(Long currentMth) {
        this.currentMth = currentMth;
    }

    public boolean isShowMileage() {
        return showMileage;
    }

    public void setShowMileage(boolean showMileage) {
        this.showMileage = showMileage;
    }

    public boolean isShowMth() {
        return showMth;
    }

    public void setShowMth(boolean showMth) {
        this.showMth = showMth;
    }

    public List<BikeMetadataOption> getBikeMetadata() {
        List<BikeMetadataOption> result = Collections.emptyList();

        if (selectedBike != null) {
            BikeMetadata bikeMetadata = selectedBike.getBikeMetadata();
            if (bikeMetadata != null) {
                result = Collections.singletonList(new BikeMetadataOption(bikeMetadata));
            }
        }

        return result;
    }

}
