package com.gruuf.web.actions;

import com.gruuf.model.Bike;
import com.gruuf.model.BikeDetails;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeEventStatus;
import com.gruuf.model.BikeTransferDescriptor;
import com.gruuf.model.SearchPeriod;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.BikeTransfers;
import com.gruuf.services.Garage;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.InterceptorRef;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@InterceptorRef("defaultWithMessages")
public class GarageAction extends BaseAction implements Preparable {

    private static Logger LOG = LogManager.getLogger(GarageAction.class);

    @Inject
    private Garage garage;
    @Inject
    private BikeHistory bikeHistory;
    @Inject
    private BikeTransfers bikeTransfers;

    private List<BikeDetails> bikesDetails;
    private List<BikeTransferDescriptor> transfers;

    @Override
    public void prepare() {
        transfers = loadBikeTransfers();
        LOG.debug("Existing transfers: {}", transfers);
        bikesDetails = loadBikeDetails();
        LOG.debug("Found {} bikes assigned to the current user", bikesDetails.size());
    }

    public List<BikeDetails> getBikeDetails() {
        return bikesDetails;
    }

    public List<BikeTransferDescriptor> getBikeTransfers() {
        return transfers;
    }

    public boolean isShowShortcuts() {
        return bikesDetails.size() > 2;
    }

    private List<BikeTransferDescriptor> loadBikeTransfers() {
        LOG.debug("Searching for bike transfers assigned to {}", currentUser.getEmail());

        return bikeTransfers.list(currentUser.getEmail())
            .stream()
            .map(BikeTransferDescriptor::new)
            .peek(transfer -> LOG.debug("Found transfer {} related to bike {}", transfer.getId(), transfer.getBike()))
            .collect(Collectors.toList());
    }

    private List<BikeDetails> loadBikeDetails() {
        List<BikeDetails> bikeDetails = new ArrayList<>();
        List<Bike> bikes = garage.findByOwner(currentUser);

        for (Bike bike : bikes) {
            bikeDetails.add(
                loadBikeDetails(bike, SearchPeriod.ALL, BikeEventStatus.NEW, BikeEventStatus.SYSTEM)
            );
        }

        return bikeDetails;
    }

}
