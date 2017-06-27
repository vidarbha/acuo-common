package com.acuo.common.model.product;

import com.acuo.common.model.AdjustableDate;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.TreeSet;

@Data
@EqualsAndHashCode(callSuper = false)
public class FXSwap implements Product {

    private Currency currency1;
    private Currency currency2;
    private String currency1Type;
    private String currency2Type;

    private final Set<FXSwapLeg> legs = new TreeSet<>();
    public Set<FXSwapLeg> getLegs() {
        return this.legs;
    }

    public void addLeg(FXSwapLeg leg) {
        this.legs.add(leg);
    }

    public void removeAllLegs() {
        this.legs.clear();
    }

    @Data
    public static class FXSwapLeg implements Comparable<FXSwapLeg> {

        private int id;
        private double position;
        private AdjustableDate maturityDate;
        private AdjustableDate settleDate;
        private double rate;
        private String spotSource;
        @Override
        public int compareTo(FXSwapLeg other) {
            return Integer.compare(this.getId(), other.getId());
        }
    }

}
