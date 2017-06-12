package com.acuo.common.model.results;


import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TradeValuation {
    private String tradeId;
    private Double marketValue;
    private LocalDate valuationDate;
}
