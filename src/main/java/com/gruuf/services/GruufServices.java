package com.gruuf.services;

import com.gruuf.web.GruufAuth;

public class GruufServices {
    public static final String USER_REGISTER = GruufAuth.generateUUID() + "-userstore";
    public static final String GARAGE = GruufAuth.generateUUID() + "-garage";
    public static final String BIKE_HISTORY = GruufAuth.generateUUID() + "-bike-history";
}
