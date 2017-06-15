package com.acuo.common.model.trade;


import com.opengamma.strata.product.fra.Fra;
import lombok.Data;

@Data
public class FRATrade implements ProductTrade {

    private TradeInfo info;
    private Fra product;
    private ProductType type;

}
