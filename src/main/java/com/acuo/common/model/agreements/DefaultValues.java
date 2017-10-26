package com.acuo.common.model.agreements;

import com.acuo.common.model.margin.Types;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DefaultValues {
    private String ampId;
    private Set<Types.CallType> callType;
    private boolean autoAgreeLowerTotalCallAmount;
    private boolean autoAgreeWithinTolerance;
    private boolean autoFullDisputeForNoAction;
    private boolean autoPartialDisputeMismatched;
    private double autoPledgeTolerancePercentage;
    private Currency currency;
    private Double deliverMinimumTransferAmount;
    private Double deliverRoundingAmount;
    private Double returnMinimumTransferAmount;
    private Double returnRoundingAmount;
}
