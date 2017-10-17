package com.acuo.common.model.agreements;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LegalEntity {
    private String ampId;
    private boolean active;
    private boolean allowIntracompanyAgreements;
    private LocalDateTime createDate;
    private String createUserAmpId;
    private boolean emailEnabled;
    private String emailList;
    private String label;
    private String legalEntityIdentifier;
    private boolean local;
    private LocalDateTime modifyDate;
    private String modifyUserAmpId;
    private boolean swiftMessageEmailNotifications;
    private String tradingPartyAmpId;
    private String tradingPartyName;
}
