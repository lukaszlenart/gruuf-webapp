package com.gruuf.services;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeTransfer;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class BikeTransfers extends Reindexable<BikeTransfer> {

    public BikeTransfers() {
        super(BikeTransfer.class);
    }

    @Override
    protected boolean shouldReindex() {
        return false;
    }

    public BikeTransfer register(Bike bike, String emailAddress) {
        BikeTransfer transfer = BikeTransfer.create(bike, emailAddress);
        return put(transfer);
    }
}
