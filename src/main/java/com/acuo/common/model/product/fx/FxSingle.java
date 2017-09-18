package com.acuo.common.model.product.fx;

import com.acuo.common.model.BusinessDayAdjustment;
import com.acuo.common.model.product.Product;
import com.opengamma.strata.basics.currency.CurrencyAmount;
import lombok.Data;

import java.time.LocalDate;

@Data
public final class FxSingle implements Product {

  /**
   * The amount in the base currency, positive if receiving, negative if paying.
   * <p>
   * The amount is signed.
   * A positive amount indicates the payment is to be received.
   * A negative amount indicates the payment is to be paid.
   */
  private CurrencyAmount baseCurrencyAmount;

  /**
   * The amount in the counter currency, positive if receiving, negative if paying.
   * <p>
   * The amount is signed.
   * A positive amount indicates the payment is to be received.
   * A negative amount indicates the payment is to be paid.
   */
  private CurrencyAmount counterCurrencyAmount;

  /**
   * The date that the FX settles.
   * <p>
   * On this date, the pay and receive amounts will be exchanged.
   * This date is typically a valid business day, however the {@code businessDayAdjustment}
   * property may be used to adjust it.
   */
  private LocalDate paymentDate;

  /**
   * The payment date adjustment, optional.
   * <p>
   * If present, the adjustment will be applied to the payment date.
   */
  private BusinessDayAdjustment paymentDateAdjustment;

}