package com.gruuf;

import com.gruuf.web.GruufAuth;

public class RbwServices {
    public static final String USER_REGISTER = GruufAuth.generateUUID() + "-userstore";
    public static final String GARAGE = GruufAuth.generateUUID() + "-garage";
    public static final String BIKE_HISTORY = GruufAuth.generateUUID() + "-bike-history";
}
