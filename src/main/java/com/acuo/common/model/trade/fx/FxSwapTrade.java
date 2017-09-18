package com.acuo.common.model.trade.fx;

import com.acuo.common.model.product.fx.FxSwap;
import com.acuo.common.model.trade.ProductTrade;
import com.acuo.common.model.trade.ProductType;
import com.acuo.common.model.trade.TradeInfo;
import lombok.Data;

@Data
public final class FxSwapTrade implements ProductTrade {

    private final TradeInfo info;
    private final FxSwap product;

    @Override
    public ProductType getType() {
        return ProductType.FXSWAP;
    }
}