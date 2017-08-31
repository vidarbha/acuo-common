package com.acuo.common.model.margin;

import com.acuo.common.model.margin.Types.AgreementType;
import com.acuo.common.model.margin.Types.CallType;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private Set<DisputeReasonCode> cancelReasonCodes;
    private String cancelComments;

    private Double collateralValue;
    private Currency currency;

    private LocalDate callDate;
    private boolean callDateCalculated;

    private Double deliverAmount;

    private Double exposure;
    private Double pendingCollateral;
    private Double returnAmount;
    private Boolean fullReturn;

    private Double roundingAmount;
    private String roundingMethod;
    private Double deliverRoundingAmount;
    private String deliverRoundingMethod;
    private Double returnRoundingAmount;
    private String returnRoundingMethod;


    private Double threshold;
    private String thresholdTreatment;

    private Double totalCallAmount;
    private CallType callType;
    private MarginType marginType;

    private LocalDate valuationDate;

    private LocalDateTime modifyDate;
    private LocalDateTime expiryDate;

    private Integer version;
    private boolean child;

    private Double minimumTransferAmount;
    private Double returnMinimumTransferAmount;
    private Double deliverMinimumTransferAmount;

    private LocalDate settlementDate;
    private String externalReference;
    private String marginAgreementAmpId;
    private String marginAgreementShortName;
    private String externalUsername;
    private PledgeFxRates pledgeFxRates;

    private String localCounterpartyLabel;
    private AgreementType marginAgreementType;

    private Double agreedAmount;

    private String side;
    private String statementId;
    private Double totalCouponPayment;
    private Double upfrontFee;
    private Double premiumPayment;
    private Double CDSCreditEvent;
    private Double NDFCashSettlement;
    private Double clearingFee;
    private Double brokerFee;
    private Double initialBalanceCash;
    private Double initialBalanceNonCash;

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

    private String role;
    private String notificationTime; // Should be changed to a LocalTime at some point but not sure how to build a soc
    private Double netRequiredAmount;
    private String comment;

    @Data
    public static class PledgeFxRates {

    }

    @Data
    public static class Counterparty {

    }
}