package com.acuo.common.model.margin;

import com.acuo.common.model.margin.Types.AgreementType;
import com.acuo.common.model.margin.Types.CallType;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Data
public class MarginCall {

    private String callAmpId;
    private Currency currency;
    private double collateralValue;
    private LocalDate callDate;
    private double deliverAmount;
    private double exposure;
    private double minimumTransferAmount;
    private double pendingCollateral;
    private double returnAmount;
    private double roundingAmount;
    private LocalDate settlementDate;
    private double totalCallAmount;
    private CallType callType;
    private LocalDate valuationDate;
    private String externalUsername;
    private PledgeFxRates pledgeFxRates;
    private String localCounterpartyLabel;
    private String marginAgreementShortName;
    private AgreementType marginAgreementType;

    private int cancelReasonCode;
    private int disputeReasonCode;

    private double agreedAmount;

    private final Set<Pledge> pledges = new TreeSet<>();

    public void addPledge(Pledge pledge) {
        this.pledges.add(pledge);
    }

    @Data
    public static class PledgeFxRates {

    }
}
