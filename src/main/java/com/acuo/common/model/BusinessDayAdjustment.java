package com.acuo.common.model;

import com.acuo.common.model.proxy.BusinessDayConventionProxy;
import com.opengamma.strata.basics.date.HolidayCalendarId;
import lombok.Data;

import java.util.Set;

@Data
public class BusinessDayAdjustment {

    private BusinessDayConventionProxy businessDayConvention;
    private Set<HolidayCalendarId> holidays;
}
