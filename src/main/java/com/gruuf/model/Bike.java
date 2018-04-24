package com.gruuf.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;
import org.apache.commons.lang3.StringUtils;

@Entity
public class Bike {

    @Id
    private String id;
    private String name;
    @Index
    private Ref<User> owner;
    @Index
    private String vin;
    private Integer modelYear;
    private Country registrationCountry;
    private String registrationPlate;

    private boolean showMileage = true;
    private boolean showMth = true;

    private Ref<BikeMetadata> bikeMetadata;

    private Long spaceUsed;

    private Bike() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVin() {
        return vin;
    }

    public User getOwner() {
        return owner.get();
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public Country getRegistrationCountry() {
        return registrationCountry;
    }

    public BikeMetadata getBikeMetadata() {
        if (bikeMetadata == null) {
            return null;
        }
        return bikeMetadata.get();
    }

    public boolean isShowMileage() {
        return showMileage;
    }

    public boolean isShowMth() {
        return showMth;
    }

    public Long getSpaceUsed() {
        return spaceUsed;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static BikeBuilder create(User owner) {
        return new BikeBuilder(owner);
    }

    public static BikeBuilder clone(Bike bike) {
        return new BikeBuilder(bike);
    }

    public String getBikeMetadataId() {
        if (bikeMetadata != null) {
            return bikeMetadata.getKey().getName();
        } else {
            return null;
        }
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public static class BikeBuilder {
        private final Bike target;

        public BikeBuilder(User owner) {
            target = new Bike();
            target.id = GruufAuth.generateUUID();
            target.owner = Ref.create(owner);
        }

        public BikeBuilder(Bike bike) {
            target = new Bike();
            target.owner = bike.owner;
            target.id = bike.id;
            target.name = bike.name;
            target.vin = bike.vin;
            target.modelYear = bike.modelYear;
            target.registrationCountry = bike.registrationCountry;
            target.registrationPlate = bike.registrationPlate;
            target.bikeMetadata = bike.bikeMetadata;
            target.showMileage = bike.showMileage;
            target.showMth = bike.showMth;
            target.spaceUsed = bike.spaceUsed;
        }

        public BikeBuilder withFriendlyName(String friendlyName) {
            target.name = friendlyName;
            return this;
        }

        public BikeBuilder withVIN(String vin) {
            target.vin = vin;
            return this;
        }

        public BikeBuilder withModelYear(Integer modelYear) {
            target.modelYear = modelYear;
            return this;
        }

        public BikeBuilder withBikeMetadataId(String bikeMetadataId) {
            if (StringUtils.isNoneEmpty(bikeMetadataId)) {
                target.bikeMetadata = Ref.create(Key.create(BikeMetadata.class, bikeMetadataId));
            }
            return this;
        }

        public BikeBuilder withShowMileage(boolean showMileage) {
            target.showMileage = showMileage;
            return this;
        }

        public BikeBuilder withShowMth(boolean showMth) {
            target.showMth = showMth;
            return this;
        }

        public BikeBuilder addSpaceUsed(Long space) {
            if (target.spaceUsed == null) {
                target.spaceUsed = space;
            } else {
                target.spaceUsed = target.spaceUsed + space;
            }
            return this;
        }

        public BikeBuilder withRegistrationCountry(Country country) {
            target.registrationCountry = country;
            return this;
        }

        public BikeBuilder withRegistrationPlate(String registrationPlate) {
            target.registrationPlate = registrationPlate;
            return this;
        }

        public Bike build() {
            return target;
        }
    }
}
