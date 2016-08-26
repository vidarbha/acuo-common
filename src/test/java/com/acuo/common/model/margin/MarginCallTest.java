package com.acuo.common.model.margin;

import com.opengamma.strata.basics.currency.Currency;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static com.acuo.common.model.margin.Types.SecurityIdType.CASH;
import static org.assertj.core.api.Assertions.assertThat;

public class MarginCallTest {

    private MarginCall marginCall;
    private String agreement = "F 1000";
    private double million = 1_000_000_000;
    private LocalDate now = LocalDate.now();

    @Before
    public void setUp() {
        marginCall = new MarginCall();
    }

    @Test
    public void testCreate() {

        marginCall.setMarginAgreementShortName(agreement);
        marginCall.setValuationDate(now);
        marginCall.setCurrency(Currency.USD);
        marginCall.setTotalCallAmount(million);

        assertThat(marginCall.getMarginAgreementShortName()).isEqualTo(agreement);
        assertThat(marginCall.getValuationDate()).isEqualTo(now);
        assertThat(marginCall.getCurrency()).isEqualTo(Currency.USD);
        assertThat(marginCall.getTotalCallAmount()).isEqualTo(million);
    }


    @Test
    public void testCancel() {
        marginCall.setCallAmpId("abc");
        marginCall.setCancelReasonCode(9001);

        assertThat(marginCall.getCallAmpId()).isEqualTo("abc");
        assertThat(marginCall.getCancelReasonCode()).isEqualTo(9001);
    }

    @Test
    public void testAgree() {
        marginCall.setCallAmpId("abc");

        assertThat(marginCall.getCallAmpId()).isEqualTo("abc");
    }

    @Test
    public void testDisputeFull() {
        marginCall.setCallAmpId("abc");
        marginCall.setDisputeReasonCode(9001);

        assertThat(marginCall.getCallAmpId()).isEqualTo("abc");
        assertThat(marginCall.getDisputeReasonCode()).isEqualTo(9001);
    }

    @Test
    public void testDisputePartial() {
        marginCall.setCallAmpId("abc");
        marginCall.setDisputeReasonCode(9001);
        marginCall.setAgreedAmount(1);

        assertThat(marginCall.getCallAmpId()).isEqualTo("abc");
        assertThat(marginCall.getDisputeReasonCode()).isEqualTo(9001);
        assertThat(marginCall.getAgreedAmount()).isEqualTo(1);

    }

    @Test
    public void testPledge() {
        Pledge pledge = createPledge();
        marginCall.addPledge(pledge);
        marginCall.addPledge(pledge);
        marginCall.setCallAmpId("abc");

        assertThat(marginCall.getCallAmpId()).isEqualTo("abc");
        assertThat(marginCall.getPledges()).hasSize(1).contains(createPledge());
    }

    @Test
    public void testPledgeReject() {
        Pledge pledge = createPledge();
        pledge.setRejectReasonCode(9002);
        marginCall.addPledge(pledge);
        marginCall.setCallAmpId("abc");

        assertThat(marginCall.getCallAmpId()).isEqualTo("abc");
        assertThat(marginCall.getPledges()).hasSize(1).doesNotContain(createPledge());
        Optional<Pledge> first = marginCall.getPledges().stream().findFirst();
        Pledge p = createPledge();
        p.setRejectReasonCode(9002);
        assertThat(first).isPresent().contains(p);
    }

    @Test
    public void testPledgeAmend() {
        Pledge pledge = new Pledge();
        //pledge.set[Anything] ( *);
        marginCall.addPledge(pledge);
        marginCall.setCallAmpId("abc");
    }


    @Test
    public void testPledgeAccept() {
        marginCall.setCallAmpId("abc");
    }

    private Pledge createPledge() {
        Pledge pledge = new Pledge();
        pledge.setSecurityId("CASH");
        pledge.setSecurityIdType(CASH);
        pledge.setSettlementDate(now);
        pledge.setCurrentMarketValue(million);
        return pledge;
    }
}
