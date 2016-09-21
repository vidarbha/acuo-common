package com.acuo.common.model.product;

import com.acuo.common.model.AdjustableDate;
import com.acuo.common.model.AdjustableSchedule;
import com.acuo.common.model.BusinessDayAdjustment;
import com.acuo.common.model.trade.ProductType;
import com.acuo.common.model.trade.SwapTrade;
import com.acuo.common.model.trade.TradeInfo;
import com.acuo.common.model.trade.TradeStatus;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.date.BusinessDayConventions;
import com.opengamma.strata.basics.date.DayCounts;
import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.basics.index.FloatingRateName;
import com.opengamma.strata.basics.schedule.Frequency;
import com.opengamma.strata.product.common.PayReceive;
import com.opengamma.strata.product.swap.FixingRelativeTo;
import com.opengamma.strata.product.swap.SwapLegType;

import java.time.LocalDate;

public class SwapHelper {

    private static LocalDate now = LocalDate.now();

    public static SwapTrade createTrade() {
        SwapTrade trade = new SwapTrade();
        trade.setInfo(createTradeInfo());
        trade.setProduct(createSwap());
        trade.setType(ProductType.SWAP);
        return trade;
    }

    private static Swap createSwap() {
        Swap swap = new Swap();
        swap.addLeg(createLeg(1, SwapLegType.FIXED, PayReceive.PAY));
        swap.addLeg(createLeg(2, SwapLegType.IBOR, PayReceive.RECEIVE));
        return swap;
    }

    private static Swap.SwapLeg createLeg(int id, SwapLegType type, PayReceive payRec) {
        Swap.SwapLeg swapLeg = new Swap.SwapLeg();
        swapLeg.setId(id);
        swapLeg.setCurrency(Currency.USD);
        swapLeg.setNotional(1_000_000_000.0);
        swapLeg.setStartDate(createAdjustableDate(LocalDate.now()));
        swapLeg.setMaturityDate(createAdjustableDate(LocalDate.now().plusYears(10)));
        swapLeg.setPaymentSchedule(createPaymentSchedule());
        swapLeg.setPayReceive(payRec);
        switch (type) {
            case FIXED:
                swapLeg.setType("FIXED");
                swapLeg.setDaycount(DayCounts.THIRTY_360_ISDA);
                break;
            case IBOR:
                swapLeg.setType("FLOAT");
                swapLeg.setDaycount(DayCounts.ACT_360);
                Swap.SwapLegFixing fixing = createSwapLegFixing();
                swapLeg.setFixing(fixing);
                break;
            case OVERNIGHT:
                break;
            case INFLATION:
                break;
            case OTHER:
                break;
        }
        return swapLeg;
    }

    private static AdjustableSchedule createPaymentSchedule() {
        AdjustableSchedule adjustableSchedule = new AdjustableSchedule();
        BusinessDayAdjustment adjustment = createBusinessDayAdjustment();
        adjustableSchedule.setAdjustment(adjustment);
        adjustableSchedule.setFrequency(Frequency.P3M);
        return adjustableSchedule;
    }

    private static AdjustableDate createAdjustableDate(LocalDate date) {
        AdjustableDate maturityDate = new AdjustableDate();
        BusinessDayAdjustment adjustment = createBusinessDayAdjustment();
        maturityDate.setAdjustment(adjustment);
        maturityDate.setDate(date);
        return maturityDate;
    }

    private static BusinessDayAdjustment createBusinessDayAdjustment() {
        BusinessDayAdjustment adjustment = new BusinessDayAdjustment();
        adjustment.setBusinessDayConvention(BusinessDayConventions.MODIFIED_FOLLOWING);
        //adjustment.setHolidays(ImmutableSet.of(HolidayCalendarIds.USNY, HolidayCalendarIds.GBLO));
        return adjustment;
    }

    private static Swap.SwapLegFixing createSwapLegFixing() {
        Swap.SwapLegFixing fixing = new Swap.SwapLegFixing();
        fixing.setFloatingRateName(FloatingRateName.of("USD-LIBOR"));
        fixing.setTenor(Tenor.TENOR_3M);
        fixing.setFixingRelativeTo(FixingRelativeTo.PERIOD_START);
        return fixing;
    }

    private static TradeInfo createTradeInfo() {

        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setBook("Book");
        tradeInfo.setClearedTradeDate(now);
        tradeInfo.setClearedTradeId("123");
        tradeInfo.setTradeDate(now);
        tradeInfo.setTradeId("tradeId");
        tradeInfo.setTradeStatus(TradeStatus.CLEARED);
        return tradeInfo;
    }
}
