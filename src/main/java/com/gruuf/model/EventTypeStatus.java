package com.gruuf.model;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;

public enum EventTypeStatus {
    NORMAL, IMPORTANT, MILEAGE, MTH;

    public static Set<EventTypeStatus> all() {
        return Collections.unmodifiableSet(Sets.newHashSet(NORMAL, IMPORTANT, MILEAGE, MTH));
    }

}