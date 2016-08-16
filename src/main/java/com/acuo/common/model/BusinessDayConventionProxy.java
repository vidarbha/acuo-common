package com.acuo.common.model;

import com.opengamma.strata.basics.date.BusinessDayConvention;
import com.opengamma.strata.basics.date.HolidayCalendar;

import java.time.LocalDate;

public class BusinessDayConventionProxy implements BusinessDayConvention {

    private final BusinessDayConvention delegate;

    public BusinessDayConventionProxy(BusinessDayConvention delegate) {
        this.delegate = delegate;
    }

    @Override
    public LocalDate adjust(LocalDate date, HolidayCalendar calendar) {
        return delegate.adjust(date, calendar);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
