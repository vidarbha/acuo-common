package com.acuo.common.model.agreements;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WhitelistInfo {

    private boolean whitelistForAll;
    private boolean whitelistForTradingPartyOnly;
    private boolean whitelistForLegalEntityOnly;
    private TradingParty cptyTradingParty;
    private LegalEntity cptyLegalEntity;
    private LegalEntity clientLegalEntity;
    private LocalDateTime modifyDate;

}
