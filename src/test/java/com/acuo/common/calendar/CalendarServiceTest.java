package com.acuo.common.calendar;

import com.acuo.common.util.GuiceJUnitRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({
        CalendarModule.class
})
@Slf4j
public class CalendarServiceTest {

    @Inject
    CalendarService calendarService;

    @Test
    public void testIsWorkingDay(){
        final LocalDate workingDay = LocalDate.of(2017, 07, 12);
        final LocalDate holiday = LocalDate.of(2017, 07, 14);
        boolean isWorkingDay = calendarService.isWorkingDay("FRA", workingDay);
        assertTrue(isWorkingDay);
        isWorkingDay = calendarService.isWorkingDay("FRA", holiday);
        assertFalse(isWorkingDay);
    }
}