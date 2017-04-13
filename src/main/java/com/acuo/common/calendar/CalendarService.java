package com.acuo.common.calendar;

import java.time.LocalDate;

public interface CalendarService {

    boolean isWorkingDay(String local, LocalDate date);
}
