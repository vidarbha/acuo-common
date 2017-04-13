package com.acuo.common.calendar;

import com.acuo.common.http.client.OkHttpClient;

import javax.inject.Inject;

public class CalendarClient extends OkHttpClient<CalendarEndPointConfig> {

    @Inject
    public CalendarClient(okhttp3.OkHttpClient httpClient, CalendarEndPointConfig config) {
        super(httpClient, config);
    }
}
