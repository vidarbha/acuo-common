package com.acuo.common.model.product;

import com.acuo.common.model.AdjustableDate;
import com.acuo.common.model.AdjustableSchedule;
import com.acuo.common.model.CompoundingSchedule;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.date.DayCount;
import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.basics.index.FloatingRateName;
import com.opengamma.strata.basics.schedule.RollConvention;
import com.opengamma.strata.product.common.PayReceive;
import com.opengamma.strata.product.swap.FixingRelativeTo;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
        private DayCount daycount;
        private Double notional;
        private String notionalxg;
        private AdjustableDate startDate;
        private AdjustableDate maturityDate;
        private AdjustableSchedule paymentSchedule;
        private AdjustableSchedule calculationSchedule;
        private AdjustableSchedule resetSchedule;
        private RollConvention rollConvention;
        private Boolean variableCurrency;
        private Double futureNotional;
        private CompoundingSchedule compoundingSchedule;

        @Override
        public int compareTo(SwapLeg other) {
            return Integer.compare(this.getId(), other.getId());
        }
    }

    @Data
    public static class SwapLegFixing {
        private FloatingRateName floatingRateName;
        private Tenor tenor;
        private FixingRelativeTo fixingRelativeTo;
    }
}
