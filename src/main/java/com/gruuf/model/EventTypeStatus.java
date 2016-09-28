package com.gruuf.model;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;

public enum EventTypeStatus {
    NORMAL, IMPORTANT;

    public static Set<EventTypeStatus> all() {
        return Collections.unmodifiableSet(Sets.newHashSet(NORMAL, IMPORTANT));
    }

}