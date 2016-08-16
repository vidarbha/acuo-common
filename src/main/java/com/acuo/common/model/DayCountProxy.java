package com.acuo.common.model;

import com.acuo.common.util.ArgChecker;
import com.opengamma.strata.basics.date.DayCount;

import java.time.LocalDate;

public class DayCountProxy implements DayCount {

    private final DayCount delegate;

    public DayCountProxy(DayCount toProxy) {
        ArgChecker.notNull(toProxy, "toProxy");
        this.delegate = toProxy;
    }

    @Override
    public double yearFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        return delegate.yearFraction(firstDate, secondDate, scheduleInfo);
    }

    @Override
    public int days(LocalDate firstDate, LocalDate secondDate) {
        return delegate.days(firstDate, secondDate);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
