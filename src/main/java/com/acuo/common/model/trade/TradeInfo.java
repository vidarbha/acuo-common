package com.acuo.common.model.trade;


import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
public class TradeInfo {
    private String tradeId;
    private LocalDate tradeDate;
    private String clearedTradeId;
    private LocalDate clearedTradeDate;
    private String book;
    private TradeStatus tradeStatus;
    private Set<Attribute> attributes;

    @Data
    public static class Attribute {
        private TradeAttributeType<?> type;
        private String value;
    }
}
