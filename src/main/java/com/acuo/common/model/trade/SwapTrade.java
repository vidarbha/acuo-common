package com.acuo.common.model.trade;

import com.acuo.common.model.product.Swap;
import lombok.Data;

@Data
public class SwapTrade implements ProductTrade {

    private TradeInfo info;
    private Swap product;
    private ProductType type;

}
