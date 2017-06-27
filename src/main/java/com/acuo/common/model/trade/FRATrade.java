package com.acuo.common.model.trade;


import com.acuo.common.model.product.FRA;
import lombok.Data;

@Data
public class FRATrade implements ProductTrade {

    private TradeInfo info;
    private FRA product;
    private ProductType type;

}
