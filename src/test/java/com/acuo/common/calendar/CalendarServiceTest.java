package com.acuo.common.calendar;

import com.acuo.common.modules.ConfigurationTestModule;
import com.acuo.common.security.EncryptionModule;
import com.acuo.common.util.GuiceJUnitRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({
        ConfigurationTestModule.class,
        EncryptionModule.class,
        CalendarModule.class
})
@Ignore
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