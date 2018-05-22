package com.gruuf.services;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeTransfer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BikeTransfers extends Reindexable<BikeTransfer> {

    private static final Logger LOG = LogManager.getLogger(BikeTransfers.class);

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

    public List<BikeTransfer> list(String emailAddress) {
        return findBy("emailAddress", emailAddress)
            .stream()
            .peek(transfer -> LOG.debug("Found transfer {}", transfer))
            .filter(transfer -> transfer.getTransferDate() == null)
            .collect(Collectors.toList());
    }
}
