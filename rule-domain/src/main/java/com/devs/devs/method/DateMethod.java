package com.devs.devs.method;

import java.util.Date;

public class DateMethod {
    private final static long ONE_DAY_MS = 1000 * 60 * 60 * 24;
    private final static long ONE_HOUR_MS = 1000 * 60 * 60;

    private final static long ONE_YEAR_MS = ONE_DAY_MS * 365;

    public double duringHour(Date a, Date b) {
        long startTime = a.getTime();
        long endTime = b.getTime();
        return Math.ceil(Math.abs(endTime - startTime) * 1.0 / ONE_HOUR_MS * 100) / 100.0;
    }

    public double duringDay(Date a, Date b) {
        long startTime = a.getTime();
        long endTime = b.getTime();
        return Math.abs(endTime - startTime) * 1.0 / ONE_DAY_MS;
    }

    public double duringYear(Date a, Date b) {
        long startTime = a.getTime();
        long endTime = b.getTime();
        return Math.abs(endTime - startTime) * 1.0 / ONE_YEAR_MS;
    }

}
