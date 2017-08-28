package com.acuo.common.model.substitution;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;

import static com.acuo.common.model.margin.Types.BusinessState;
import static com.acuo.common.model.margin.Types.SecurityIdType;

@Data
public class SubstitutionItem {

    private String ampId;
    private String substitutionAmpId;
    private LocalDate createDate;
    private LocalDate expireDate;
    private LocalDate modifyDate;
    private double adjustedCollateralValue;
    private Currency fxCurrency;
    private double FXRate;
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
}
