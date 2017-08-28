package com.acuo.common.model.substitution;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.acuo.common.model.margin.Types.BusinessState;
import static com.acuo.common.model.margin.Types.SecurityIdType;

@Data
public class Substitution {

    private String ampId;
    private LocalDate createDate;
    private LocalDate expireDate;
    private LocalDate modifyDate;
    private String direction;
    private String MarginAgreementAmpId;
    private String marginAgreementShortName;
    private double adjustedCollateralValue;
    private String cancelComments;
    private String cancelReasonCode;
    private Currency FXCurrency;
    private double currentMarketValue;
    private LocalDate settlementDate;
    private double haircut;
    private double price;
    private double quantity;
    private String securityId;
    private SecurityIdType securityIdType;
    private int rejectReasonCode;
    private String rejectComment;
    private Integer version;
    private LocalDate valuationDate;
    private BusinessState businessState;
    private Set<SubstitutionItem> substitutionItems = new HashSet<>();
    public Set<SubstitutionItem> getSubstitutionItems() { return substitutionItems; }
    public void addSubstitutionItem(SubstitutionItem substitutionItem) { this.substitutionItems.add(substitutionItem); }
    public void removeAllSubstitutionItems() {substitutionItems.clear(); }
}
