package com.acuo.common.model.agreements;


import lombok.Data;

@Data
public class WhitelistInfo {

    private boolean WhitelistAll;
    private boolean WhitelistTradingPartyOnly;
    private boolean WhitelistLegalEntityOnly;
    private TradingParty tradingParty;
    private LegalEntity legalEntity;

}
