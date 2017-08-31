package com.acuo.common.model.agreements;

import com.acuo.common.model.margin.Types;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.util.Set;

@Data
public class BilateralDetails {
    private Currency accountBaseCurrency;
    private Set<Types.CallType> callType;
    private Double marginTermDeliverMinimumTransferAmount;
    private Double marginTermDeliverRoundingAmount;
    private Double marginTermReturnMinimumTransferAmount;
    private Double marginTermReturnRoundingAmount;
}
