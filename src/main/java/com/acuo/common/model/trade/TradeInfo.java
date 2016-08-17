package com.acuo.common.model.trade;


import lombok.Data;

import java.time.LocalDate;

@Data
public class TradeInfo {
    private String tradeId;
    private LocalDate tradeDate;
    private String clearedTradeId;
    private LocalDate clearedTradeDate;
    private String book;
    private TradeStatus tradeStatus;
}
