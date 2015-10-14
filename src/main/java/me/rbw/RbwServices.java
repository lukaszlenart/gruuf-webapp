package me.rbw;

import me.rbw.web.RbwAuth;

public class RbwServices {
    public static final String USER_REGISTER = RbwAuth.generateUUID() + "-userstore";
    public static final String GARAGE = RbwAuth.generateUUID() + "-garage";
    public static final String MOTORBIKE_EVENT_SOURCE = RbwAuth.generateUUID() + "-motorbike-event-source";
}
