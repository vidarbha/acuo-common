package com.acuo.common.calendar;

import com.acuo.common.http.client.ClientEndPoint;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CalendarServiceImpl implements CalendarService {

    private final ClientEndPoint<CalendarEndPointConfig> client;

    @Inject
    public CalendarServiceImpl(ClientEndPoint<CalendarEndPointConfig> client)
    {
        this.client = client;
    }

    public boolean isWorkingDay(String local, LocalDate date)
    {
        String response =  CalendarCall.of(client).with(local, date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))).create().send();
        return Boolean.valueOf(response.toLowerCase());
    }
}
