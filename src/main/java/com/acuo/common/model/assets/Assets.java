package com.acuo.common.model.assets;

import lombok.Data;

import java.time.LocalDate;
import com.opengamma.strata.basics.currency.Currency;

/**
 * Created by lucie on 14/3/17.
 */
@Data
public class Assets {

    private String assetId;
    private String idType;
    private String name;
    private Currency currency;
    private String type;
    private String ICADCode;
    private String ticker;
    private LocalDate issueDate;
    private LocalDate maturityDate;
    private String fitchRating;
    private double parValue;
    private double minUnit;
    private double internalCost;
    private double opptCost;
    private double availableQuantities;
    private double notional;

    private Integer CQS;
    private String assetType;
    private String eeaClass;
    private String issuerId;
    private String issuerGroupId;
    private String issuerCountryCode;
    private Currency issuerDomCurrency;
    private String issuerSector;
    private boolean issuerIsMemberState;
    private String moodyRating;
    private String snpRating;
    private String ratingMethod;
    private String creditPrincipalCode;
    private String convertibleTypeCode;
    private String index;
    private String exchange;
    private String rateType;
    private double maturityYears = -1;
    private double usClass;
    private boolean convertibleFlag;
}
