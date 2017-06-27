package com.acuo.common.model.results;

import com.acuo.common.model.margin.Types;
import lombok.Data;

@Data
public class MarginValuation {

    private String name;
    private Double account;
    private Double change;
    private Double total;
    private Types.CallType marginType;
    private String portfolioId;
}