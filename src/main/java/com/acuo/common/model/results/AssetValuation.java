package com.acuo.common.model.results;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AssetValuation {

    private String assetId;
    private String idType;
    private double yield;
    private double cleanMarketValue;
    private double notional;
    private LocalDateTime valuationDateTime;
    private String priceQuotationType;
    private String source;
    private Currency nominalCurrency;
    private Currency reportCurrency;
    private double coupon;

}
