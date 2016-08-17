package com.acuo.common.model.proxy;

import com.opengamma.strata.basics.schedule.Frequency;
import com.opengamma.strata.basics.schedule.RollConvention;

import java.time.LocalDate;

public class RollConventionProxy implements RollConvention {

    private final RollConvention delegate;

    public RollConventionProxy(RollConvention delegate) {
        this.delegate = delegate;
    }

    public static RollConventionProxy of(RollConvention toProxy) {
        return new RollConventionProxy(toProxy);
    }

    public static RollConventionProxy of(String name) {
        return new RollConventionProxy(RollConvention.of(name));
    }

    @Override
    public LocalDate adjust(LocalDate date) {
        return delegate.adjust(date);
    }

    @Override
    public boolean matches(LocalDate date) {
        return delegate.matches(date);
    }

    @Override
    public LocalDate next(LocalDate date, Frequency periodicFrequency) {
        return delegate.next(date, periodicFrequency);
    }

    @Override
    public LocalDate previous(LocalDate date, Frequency periodicFrequency) {
        return delegate.previous(date,periodicFrequency);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
