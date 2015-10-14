package me.rbw.web.interceptors;

import me.rbw.services.MotorbikeEventSource;

public interface MotorbikeEventsAware {

    void setMotorbikeEventSource(MotorbikeEventSource eventSource);

}
