package com.acuo.common.model.results;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetSettlementDate {

    private String assetId;
    private LocalDate settlementDate;

}
