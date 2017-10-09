package com.acuo.common.model.agreements;

import com.acuo.common.model.margin.Types;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class LocalDetails {
    private String ampId;
    private boolean callIssuanceManual;
    private boolean callIssuanceMultipleCallsPerDayCheck;
    private double callIssuanceOwnVsCptyTolerance;
    private double callIssuancePrimaryToValidatorTolerance;
    private double callIssuanceSchedule;
    private String marginTermCalculationType;
    private String marginTermPrimary;
}
