package com.acuo.common.model.agreements;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LegalEntity {
    private String ampId;
    private LocalDateTime createDate;
    private String createUserAmpId;
    private String emailList;
    private String label;
    private boolean local;
    private LocalDateTime modifyDate;
    private String modifyUserAmpId;
    private String tradingPartyAmpId;
    private String tradingPartyName;
}
