package com.acuo.common.model.margin.result;

import lombok.Data;

@Data
public class MarginValuation {

    private String name;
    private Double account;
    private Double change;
    private Double margin;
}