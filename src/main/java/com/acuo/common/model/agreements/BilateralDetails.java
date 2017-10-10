package com.acuo.common.model.agreements;

import com.acuo.common.model.margin.Types;
import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
public class BilateralDetails {
    private String ampId;
    private Types.AgreementType agreementType;
    private LocalDateTime messagingStartDate;
    private LocalDateTime messagingEndDate;
    private Types.ExposureTreatment exposureTreatment;
    private Currency accountBaseCurrency;
    private Types.Role role;
    private Set<Types.CallType> callType;
    private Double marginTermDeliverMinimumTransferAmount;
    private Double marginTermDeliverRoundingAmount;
    private Double marginTermReturnMinimumTransferAmount;
    private Double marginTermReturnRoundingAmount;
    private String marginTermDeliverRoundingMethod;
    private String marginTermReturnRoundingMethod;
    private Double marginTermThresholdAmount;
    private String marginTermThresholdTreatment;
    private String marginTermNotificationTime;
    private String marginTermNotificationTimeZone;
    private String marginTermStartDate;
    private String marginTermEndDate;
}
