package com.gruuf.model;

public enum BikeEventStatus {
    NEW(true), SYSTEM(false), DELETED(false);

    BikeEventStatus(boolean deletable) {
        this.deletable = deletable;
    }

    private boolean deletable;

    public boolean isDeletable() {
        return deletable;
    }
}
