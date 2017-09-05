package com.acuo.common.model.agreements;
import com.acuo.common.model.margin.Types;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Data
public class Agreement {
    private String id;
    private Currency accountBaseCurrency;
    private Currency accountOperatingCurrency;
    private LocalDate activeDate;
    private Set<BilateralDetails> bilateralDetails;
    private String longName;
    private String agreementShortName;
    private Types.AgreementType agreementType;
    private Integer agreementVersion;
    private boolean allowMultipleMarginCallsPerDay;
    private String ampId;
    private Types.AgreementBusinessState businessState;
    private String createdBy;
    private String counterpartyAmpId;
    private String counterpartyLabel;
    private LocalDateTime createDate;
    private String createUserAmpId;
    private String direction;
    private String externalUsername;
    private boolean interestStatementEnabled;
    private String lastModifiedCounterparty;
    private String localCounterpartyAmpId;
    private String localCounterpartyLabel;
    private boolean marginCallEnabled;
    private LocalDateTime modifyDate;
    private String modifyUserAmpId;
    private LocalDateTime requestedActiveDate;
    private boolean substitutionEnabled;
    private String timeZone;
    private Double threshold;
    private String valuationAgentAmpId;
    private boolean soleCalculation;
    private boolean initialCallsEnabled;
    private boolean separateInitialCallsByCurrency;
    private boolean variationCallsEnabled;
    private boolean separateVariationCallsByCurrency;
    private boolean creditCallsEnabled;
    private InterestBenchmark interestBenchmark;
    private Set<Types.CallType> nettingCallTypes;
}


