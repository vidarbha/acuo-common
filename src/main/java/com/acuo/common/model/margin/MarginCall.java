package com.acuo.common.model.margin;

import com.acuo.common.model.margin.Types.AgreementType;
import com.acuo.common.model.margin.Types.CallType;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.acuo.common.model.margin.Types.*;

@Data
public class MarginCall {

    private String id;
    private String ampId;
    private BusinessState businessState;
    private String direction;

    private Set<Integer> cancelReasonCodes;

    private double collateralValue;
    private Currency currency;

    private LocalDate callDate;
    private boolean callDateCalculated;

    private double deliverAmount;

    private double exposure;
    private double pendingCollateral;
    private double returnAmount;

    private double roundingAmount;
    private double threshold;

    private double totalCallAmount;
    private CallType callType;
    private MarginType marginType;

    private LocalDate valuationDate;

    private int version;
    private boolean child;

    private double minimumTransferAmount;

    private LocalDate settlementDate;
    private String externalReference;
    private String marginAgreementAmpId;
    private String marginAgreementShortName;
    private String externalUsername;
    private PledgeFxRates pledgeFxRates;

    private String localCounterpartyLabel;
    private AgreementType marginAgreementType;

    private double agreedAmount;

    private String side;
    private String statementId;
    private double totalCouponPayment;
    private double upfrontFee;
    private double premiumPayment;
    private double CDSCreditEvent;
    private double NDFCashSettlement;
    private double clearingFee;
    private double brokerFee;
    private double initialBalanceCash;
    private double initialBalanceNonCash;

    private Dispute dispute;

    private Set<Recall> recalls = new HashSet<>();

    public Set<Recall> getRecalls() { return recalls; }

    public void addRecall(Recall recall) { this.recalls.add(recall); }

    public void removeAllRecalls() {recalls.clear(); }

    private final Set<Pledge> pledges = new TreeSet<>();

    public Set<Pledge> getPledges()  {
        return pledges;
    }

    public void addPledge(Pledge pledge) {
        this.pledges.add(pledge);
    }

    public void removeAllPledges()  {
        pledges.clear();
    }

    @Data
    public static class PledgeFxRates {

    }

    @Data
    public static class Counterparty {

    }
}