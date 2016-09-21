package com.acuo.common.model;

import com.opengamma.strata.basics.date.BusinessDayConvention;
import com.opengamma.strata.basics.date.HolidayCalendarId;
import lombok.Data;

import java.util.Set;

@Data
public class BusinessDayAdjustment {

    private BusinessDayConvention businessDayConvention;
    private Set<HolidayCalendarId> holidays;
}
