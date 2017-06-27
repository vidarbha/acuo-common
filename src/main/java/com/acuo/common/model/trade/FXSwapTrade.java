package com.acuo.common.model.trade;

import com.acuo.common.model.product.FXSwap;
import lombok.Data;

@Data
public class FXSwapTrade implements ProductTrade {

    private TradeInfo info;
    private FXSwap product;
    private ProductType type;

}
