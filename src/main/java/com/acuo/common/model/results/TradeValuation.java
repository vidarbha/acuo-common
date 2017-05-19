package com.acuo.common.model.results;


import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

/**
 * Created by lucie on 19/5/17.
 */
@Data
public class TradeValuation {
    private String tradeId;
    private Double marketValue;
    private String status;
    private String errorMessage;
}
