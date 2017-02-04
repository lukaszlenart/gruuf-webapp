package com.gruuf.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;

@Entity
public class BikeMetadata {

    @Id
    private String id;
    @Index
    private String manufacturer;
    @Index
    private String model;
    private Integer productionStartYear;
    private Integer productionEndYear;

    public static BikeMetadataBuilder create() {
        return new BikeMetadataBuilder();
    }

    public static BikeMetadataBuilder create(BikeMetadata bikeMetadata) {
        BikeMetadataBuilder builder = new BikeMetadataBuilder();
        builder.target = bikeMetadata;
        return builder;
    }

    private BikeMetadata() {
    }

    public String getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Integer getProductionStartYear() {
        return productionStartYear;
    }

    public Integer getProductionEndYear() {
        return productionEndYear;
    }

    public static class BikeMetadataBuilder {

        private BikeMetadata target;

        public BikeMetadataBuilder() {
            target = new BikeMetadata();
            target.id = GruufAuth.generateUUID();
        }

        public BikeMetadataBuilder withManufacturer(String manufacturer) {
            target.manufacturer = manufacturer;
            return this;
        }

        public BikeMetadataBuilder withModel(String model) {
            target.model = model;
            return this;
        }

        public BikeMetadataBuilder withProductionStartYear(Integer productionStartYear) {
            target.productionStartYear = productionStartYear;
            return this;
        }

        public BikeMetadataBuilder withProductionEndYear(Integer productionEndYear) {
            target.productionEndYear = productionEndYear;
            return this;
        }

        public BikeMetadata build() {
            return target;
        }
    }

    @Override
    public String toString() {
        return "BikeMetadata{" +
                "id='" + id + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", productionStartYear=" + productionStartYear +
                ", productionEndYear=" + productionEndYear +
                '}';
    }
}
