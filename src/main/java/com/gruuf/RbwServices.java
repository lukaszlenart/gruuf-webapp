package com.gruuf;

import com.gruuf.web.RbwAuth;

public class RbwServices {
    public static final String USER_REGISTER = RbwAuth.generateUUID() + "-userstore";
    public static final String GARAGE = RbwAuth.generateUUID() + "-garage";
    public static final String BIKE_HISTORY = RbwAuth.generateUUID() + "-bike-history";
}
