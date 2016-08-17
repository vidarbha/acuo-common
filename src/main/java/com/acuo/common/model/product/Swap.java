package com.acuo.common.model.product;

import com.acuo.common.model.AdjustableDate;
import com.acuo.common.model.AdjustableSchedule;
import com.acuo.common.model.PayReceive;
import com.acuo.common.model.proxy.BusinessDayConventionProxy;
import com.acuo.common.model.proxy.DayCountProxy;
import com.acuo.common.model.proxy.RollConventionProxy;
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
public class Swap implements Product {

    private final Set<SwapLeg> legs = new TreeSet<>();

    public Set<SwapLeg> getLegs() {
        return this.legs;
    }

    public void addLeg(SwapLeg leg) {
        this.legs.add(leg);
    }

    public void removeAllLegs() {
        this.legs.clear();
    }

    @Data
    public static class SwapLeg implements Comparable<SwapLeg> {

        private int id;
        private Currency currency;
        private PayReceive payReceive;
        private SwapLegFixing fixing;
        private Double spread;
        private Double rate;
        private String type;
        private DayCountProxy daycount;
        private Double notional;
        private String notionalxg;
        private AdjustableDate startDate;
        private AdjustableDate maturityDate;
        private AdjustableSchedule paymentSchedule;
        private AdjustableSchedule calculationSchedule;
        private RollConventionProxy rollConvention;

        @Override
        public int compareTo(SwapLeg other) {
            return Integer.compare(this.getId(), other.getId());
        }
    }

    @Data
    public static class SwapLegFixing {
        private String name;
        private String term;
        private boolean arrears;
    }
}
