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
    private Integer trigger;
    private Types.MarginType marginType;
    private Currency[] eligibleCurrency;
    private Currency terminateCurrency;
    private Currency settlementCurrency;
    private Currency[] majorCurrency;
    private Currency baseCurrency;
    private Currency accountBaseCurrency;
    private LocalDate activeDate;
    private Set<BilateralDetails> bilateralDetails;
    private Set<DefaultValues> defaultValues;
    private Set<StpConfigurations> stpConfigurations;
    private Set<LocalDetails> localDetails;
    private String longName;
    private String acceptedBy;
    private String agreementShortName;
    private Types.AgreementType agreementType;
    private Integer agreementVersion;
    private boolean allowMultipleMarginCallsPerDay;
    private String ampId;
    private Types.AgreementBusinessState businessState;
    private String createdBy;
    private String createUserAmpId;
    private String counterpartyAmpId;
    private String counterpartyLabel;
    private LocalDateTime createDate;
    private LocalDateTime discontinueDate;
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
    private LocalDateTime requestedDiscontinueDate;
    private boolean substitutionEnabled;
    private String timeZone;
    private Double threshold;
    private String valuationAgentAmpId;
    private boolean soleCalculation;
    private boolean initialCallsEnabled;
    private String initialCallType;
    private String requestInitialCallType;
    private boolean separateInitialCallsByCurrency;
    private boolean variationCallsEnabled;
    private boolean separateVariationCallsByCurrency;
    private boolean creditCallsEnabled;
    private Set<InterestBenchmark> interestBenchmark;
    private Set<Types.NettingCallType> nettingCallTypes;

    private double MTA1;
    private double MTA2;
    private String thresholdTreatment;
}


