package com.gruuf.model;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;

public enum PolicyType {

    PRIVACY_POLICY;

    public static Set<PolicyType> all() {
        return Collections.unmodifiableSet(Sets.newHashSet(PRIVACY_POLICY));
    }

}
