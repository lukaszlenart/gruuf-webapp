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

    private Ref<BikeMetadata> bikeMetadata;

    private Bike() {
    }

    public Bike(User owner) {
        this.owner = Ref.create(owner);
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

    public BikeMetadata getBikeMetadata() {
        if (bikeMetadata == null) {
            return null;
        }
        return bikeMetadata.get();
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", vin='" + vin + '\'' +
                ", modelYear=" + modelYear +
                ", bikeMetadata=" + bikeMetadata +
                '}';
    }

    public static BikeBuilder create(User owner) {
        return new BikeBuilder(owner);
    }

    public static BikeBuilder clone(Bike bike) {
        return new BikeBuilder(bike);
    }

    public static class BikeBuilder {
        private final Bike target;

        public BikeBuilder(User owner) {
            target = new Bike(owner);
            target.id = GruufAuth.generateUUID();
        }

        public BikeBuilder(Bike bike) {
            target = new Bike(bike.getOwner());
            target.id = bike.id;
            target.name = bike.name;
            target.vin = bike.vin;
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

        public Bike build() {
            return target;
        }
    }
}
