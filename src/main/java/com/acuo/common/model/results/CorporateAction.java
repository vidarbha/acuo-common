package com.acuo.common.model.results;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by lucie on 29/5/17.
 */
@Data
public class CorporateAction {

    private String idCode;
    private String actionReference;
    private String senderReference;
    private String messageFunction;
    private String actionEventProcessing;
    private String actionEventIndicator;
    private String mandatoryVoluntary;
    private LocalDateTime preparationDateTime;
    private String processingStatus;
    private String securityId;
    private LocalDate couponDate;
    private LocalDate maturityDate;
    private String optionCode;
    private Currency currencyOption;
    private LocalDate cashPaymentDate;
    private String additionalInformation;
}
