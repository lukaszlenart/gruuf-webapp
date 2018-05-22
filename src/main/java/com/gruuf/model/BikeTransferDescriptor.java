package com.gruuf.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BikeTransferDescriptor {

    private String id;
    private BikeDescriptor bike;

    public BikeTransferDescriptor(BikeTransfer bikeTransfer) {
        id = bikeTransfer.getId();
        bike = new BikeDescriptor(bikeTransfer.getBike().get());
    }

}
