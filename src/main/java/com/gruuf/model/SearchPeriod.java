package com.gruuf.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public enum SearchPeriod {

    ALL(null), THREE_MONTHS(3L), SIX_MONTHS(6L);

    private LocalDateTime date;

    SearchPeriod(Long months) {
        if (months == null) {
            this.date = LocalDateTime.of(0, Month.JANUARY, 1, 0, 0);
        } else {
            this.date = LocalDateTime.now().minusMonths(months);
        }
    }

    public String getKey() {
        return "general." + name().toLowerCase().replaceAll("-", "_");
    }

    public LocalDateTime getDate() {
        return date;
    }
}
