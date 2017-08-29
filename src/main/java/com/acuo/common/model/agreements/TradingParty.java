package com.acuo.common.model.agreements;

import lombok.Data;

/**
 * Not to be confused by our ACUO TradingAccount notion : this is the layer between Organisation
 * and LegalEntity
 */

@Data
public class TradingParty {

    private String ampId;
    private String name;

}
