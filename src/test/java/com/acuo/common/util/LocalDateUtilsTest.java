package com.acuo.common.util;

import org.junit.Test;

import java.time.LocalDate;

import static com.acuo.common.util.LocalDateUtils.*;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateUtilsTest {

    @Test
    public void testMinusOneDayFromMonday() {
        LocalDate monday = of(2017, 3, 13);
        LocalDate friday = of(2017, 3, 10);

        LocalDate subject = minus(monday, 1);
        assertThat(subject).isEqualTo(friday);
    }

    @Test
    public void testMinusOneDayFromTuesday() {
        LocalDate tuesday = of(2017, 3, 14);
        LocalDate monday = of(2017, 3, 13);

        LocalDate subject = minus(tuesday, 1);
        assertThat(subject).isEqualTo(monday);
    }

    @Test
    public void testAddOneDayFromFriday() {
        LocalDate friday = of(2017, 3, 10);
        LocalDate monday = of(2017, 3, 13);

        LocalDate subject = add(friday, 1);
        assertThat(subject).isEqualTo(monday);
    }

    @Test
    public void testAddOneDayFromMonday() {
        LocalDate monday = of(2017, 3, 13);
        LocalDate tuesday = of(2017, 3, 14);

        LocalDate subject = add(monday, 1);
        assertThat(subject).isEqualTo(tuesday);
    }

    @Test
    public void testAdjustForWeekends() {
        LocalDate saturday = of(2017, 3, 11);
        LocalDate sunday = of(2017, 3, 12);
        LocalDate monday = of(2017, 3, 13);

        assertThat(adjustForWeekend(saturday)).isEqualTo(monday);
        assertThat(adjustForWeekend(sunday)).isEqualTo(monday);
    }
}