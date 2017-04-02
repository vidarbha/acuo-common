package com.acuo.common.model.margin;

import com.acuo.common.model.margin.Types.SecurityIdType;
import com.acuo.common.util.Utils;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Pledge implements Comparable<Pledge>{

    private String callAmpId;
    private String pledgeAmpId;
    private String securityId;
    private SecurityIdType securityIdType;
    private String deliveryType;
    private double quantity;
    private Currency FXCurrency;
    private double currentMarketValue;
    private double adjustedCollateralValue;
    private LocalDate settlementDate;
    private double FXRate;
    private double price;
    private double haircut;
    private String externalUsername;
    private int rejectReasonCode;
    private int version;

    @Override
    public int compareTo(Pledge other) {
        return Utils.nullSafeStringComparator.compare(this.callAmpId, other.callAmpId);
    }
}
