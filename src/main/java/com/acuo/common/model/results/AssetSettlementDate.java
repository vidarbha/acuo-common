package com.acuo.common.model.results;

import com.acuo.common.model.ids.AssetId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetSettlementDate {

    private AssetId assetId;
    private LocalDate settlementDate;

}
