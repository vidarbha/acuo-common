package com.acuo.common.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValueFormatter {

    private ValueFormatter() {

    }

    public static String format(Double value, String format) {
        if (value == null)
            value = 0.0d;
        return String.format(format, value);
    }

    public static String format(Double value) {
        if (value == null)
            value = 0.0d;
        return String.format("%.2f", value);
    }

    public static String format(LocalDate date) {
        if (date == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        return formatter.format(date);
    }

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        return formatter.format(dateTime);
    }
}
