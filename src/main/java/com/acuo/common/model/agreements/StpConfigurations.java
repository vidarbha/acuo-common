package com.acuo.common.model.agreements;

import lombok.Data;

import java.util.Set;

@Data
public class StpConfigurations {
    private String ampId;
    private boolean callIssuanceManual;
    private String organizationAmpId;
    private String stpAction;
    private String stpOptionCallType;
    private String stpOptionType;
    private String stpOptionCurrency;
}
