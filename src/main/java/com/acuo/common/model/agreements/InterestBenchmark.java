package com.acuo.common.model.agreements;

import com.opengamma.strata.basics.currency.Currency;
import lombok.Data;

@Data
public class InterestBenchmark {
    private String benchmarkName;
    private Currency currency;
}

