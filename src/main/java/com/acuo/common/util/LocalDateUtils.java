package com.acuo.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.SATURDAY;

public class LocalDateUtils {

    private LocalDateUtils() {}

    public static LocalDate valuationDate() {
        return minus(LocalDate.now(), 1);
    }

    public static LocalDate add(LocalDate date, int workdays) {
        if (workdays < 1) {
            return date;
        }

        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < workdays) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == SATURDAY ||
                    result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }

        return result;
    }

    public static LocalDate minus(LocalDate date, int workdays) {
        if (workdays < 1) {
            return date;
        }

        LocalDate result = date;
        int removedDays = 0;
        while (removedDays < workdays) {
            result = result.minusDays(1);
            if (!(result.getDayOfWeek() == SATURDAY ||
                    result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++removedDays;
            }
        }
        return result;
    }

    public static LocalDate adjustForWeekend(LocalDate date) {
        ArgChecker.notNull(date, "date");
        switch(date.getDayOfWeek()) {
            case SATURDAY:
                return date.plusDays(2);
            case SUNDAY:
                return date.plusDays(1);
            default:
                return date;
        }
    }
}
