package com.acuo.common.model.margin;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import static com.acuo.common.model.margin.Types.*;

@Data
public class Recall {

    private String callAmpId;
    private String recallAmpId;
    private String securityId;
    private Types.SecurityIdType securityIdType;
    private String deliveryType;
    private Double quantity;
    private Currency FXCurrency;
    private Double currentMarketValue;
    private Double adjustedCollateralValue;
    private LocalDate settlementDate;
    private Double FXRate;
    private Double price;
    private Double haircut;
    private String externalUsername;
    private Integer rejectReasonCode;
    private String rejectComment;
    private int version;
    private Boolean recallIsAccepted;
    private BusinessState businessState;
}
