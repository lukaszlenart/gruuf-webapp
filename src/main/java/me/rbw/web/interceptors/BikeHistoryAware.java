package me.rbw.web.interceptors;

import me.rbw.services.BikeHistory;

public interface BikeHistoryAware {

    void setMotorbikeEventSource(BikeHistory eventSource);

}
