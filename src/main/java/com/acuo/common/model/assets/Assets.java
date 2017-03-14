package com.acuo.common.model.assets;

import lombok.Data;

import java.time.LocalDate;
import java.util.Currency;

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
    private double price;
    private double yield;
    private double oriYield;
    private String fitchRating;
    private double parValue;
    private double minUnit;
    private double internalCost;
    private double opptCost;
    private double availableQuantities;
    private double notional;

}
