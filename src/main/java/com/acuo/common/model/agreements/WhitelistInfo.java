package com.acuo.common.model.agreements;


import lombok.Data;

@Data
public class WhitelistInfo {

    private boolean whitelistForAll;
    private boolean whitelistForTradingPartyOnly;
    private boolean whitelistForLegalEntityOnly;
    private TradingParty cptyTradingParty;
    private LegalEntity cptyLegalEntity;
    private LegalEntity clientLegalEntity;

}
