package com.acuo.common.model.margin;

import lombok.Data;

/**
 * Created by lucie on 22/2/17.
 */
@Data
public class Dispute {

    private int disputeReasonCodes;
    private double counterpartyMtM;
    private double counterpartyCollateralBalance;
    private double counterpartyIM;
    private String disputeComment;

}
