package com.acuo.common.model.product;

import com.acuo.common.model.AdjustableDate;
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
public class FRA implements Product {

    private final Set<FRALeg> legs = new TreeSet<>();

    public Set<FRALeg> getLegs() {
        return this.legs;
    }

    public void addLeg(FRALeg leg) {
        this.legs.add(leg);
    }

    public void removeAllLegs() {
        this.legs.clear();
    }

    @Data
    public static class FRALeg implements Comparable<FRALeg> {

        private int id;
        private Currency currency;
        private PayReceive payReceive;
        private FRALegFixing fixing;
        private Double spread;
        private Double rate;
        private String type;
        private DayCount daycount;
        private Double notional;
        private String notionalxg;
        private AdjustableDate startDate;
        private AdjustableDate maturityDate;
        private AdjustableDate fixingDate;
        private AdjustableDate paymentDate;
        private RollConvention rollConvention;
        private boolean payAtStart;

        @Override
        public int compareTo(FRALeg other) {
            return Integer.compare(this.getId(), other.getId());
        }
    }

    @Data
    public static class FRALegFixing {
        private FloatingRateName floatingRateName;
        private Tenor tenor;
        private FixingRelativeTo fixingRelativeTo;
    }
}
