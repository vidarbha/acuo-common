package com.acuo.common.model;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.date.HolidayCalendarId;
import com.opengamma.strata.basics.schedule.Frequency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Data
@EqualsAndHashCode(callSuper = false)
public class IrSwap extends BaseTrade {

    private final Set<IrSwapLeg> legs = new TreeSet<>();

    public Set<IrSwapLeg> getLegs() {
        return this.legs;
    }

    public void addLeg(IrSwapLeg leg) {
        this.legs.add(leg);
    }

    public void removeAllLegs() {
        this.legs.clear();
    }

    @Data
    public static class IrSwapLeg implements Comparable<IrSwapLeg>{

        private int id;
        private Currency currency;
        private IrSwapLegFixing fixing;
        private Double spread;
        private Double rate;
        private String type;
        private DayCountProxy daycount;
        private Double notional;
        private String notionalxg;
        private IrSwapLegPayDates paydates;

        @Override
        public int compareTo(IrSwapLeg other) {
            return Integer.compare(this.getId(), other.getId());
        }
    }

    @Data
    public static class IrSwapLegFixing {
        private String name;
        private String term;
        private boolean arrears;
    }

    @Data
    public static class IrSwapLegPayDates {
        private LocalDate startDate;
        private Frequency frequency;
        private LocalDate enddate;
        private BusinessDayConventionProxy businessDayConvention;
        private Set<HolidayCalendarId> holidayCalendarIds;
        private boolean adjust;
        private boolean eom;
    }
}