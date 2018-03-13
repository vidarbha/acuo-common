package com.acuo.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompoundingSchedule extends AdjustableSchedule {

    // TODO change to an enumeration, values are:
    // Average, Weighted Average, Compound, Power, Annualized, Annualized Product,
    // Spread Exclusive Compounding, Straight Compounding, Flat Compounding, TBill91
    private String compoundingRule;

}