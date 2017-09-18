package com.acuo.common.model.product.fx;

import com.acuo.common.model.product.Product;
import lombok.Data;

@Data
public final class FxSwap implements Product {

    private FxSingle nearLeg;
    private FxSingle farLeg;
}