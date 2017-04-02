package com.acuo.common.model.margin;

import lombok.Data;

import java.util.Set;

/**
 * Created by lucie on 22/2/17.
 */
@Data
public class Dispute {

    private Set<Integer> disputeReasonCodes;
    private double counterpartyMtM;
    private double counterpartyCollateralBalance;
    private double counterpartyIM;
    private String disputeComment;
    private String state;
    private Boolean parent;
    private Boolean child;
    private String parentAmpId;
    private int parentVersion;
}
