package com.acuo.common.model.margin;

import lombok.Data;

import java.util.Set;

import static com.acuo.common.model.margin.Types.DisputeReasonCode;

@Data
public class Dispute {

    private String msId;
    private Set<DisputeReasonCode> disputeReasonCodes;

    private String comments;
    private Double disputedAmount;
    private Double agreedAmount;
    private Double mtm;
    private Double balance;
    private Double im;

    private String parentAmpId;
    private int parentVersion;
    private Boolean parent;
    private Boolean child;
    private String state;
}
