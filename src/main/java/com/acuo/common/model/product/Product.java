package com.acuo.common.model.product;

import com.acuo.common.model.trade.Trade;

/**
 * The product details of a financial instrument.
 * <p>
 * A product is a high level abstraction applicable to many different types.
 * For example, an Interest Rate Swap is a product, as is a Forward Rate Agreement (FRA).
 * <p>
 * A product exists independently from a {@link Trade}. It represents the economics of the
 * financial instrument regardless of the trade date or counterparties.
 * <p>
 * Implementations must be immutable and thread-safe beans.
 */
public interface Product {

}
