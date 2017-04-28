package com.acuo.common.calendar;

import com.acuo.common.http.client.ClientEndPoint;
import com.acuo.common.http.client.LoggingInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import java.util.concurrent.TimeUnit;

public class CalendarModule extends AbstractModule {
    @Override
    protected void configure() {
        okhttp3.OkHttpClient httpClient = new okhttp3.OkHttpClient.Builder().connectTimeout(0, TimeUnit.MILLISECONDS).addInterceptor(new LoggingInterceptor()).build();
        bind(okhttp3.OkHttpClient.class).toInstance(httpClient);
        bind(new TypeLiteral<ClientEndPoint<CalendarEndPointConfig>>(){}).to(CalendarClient.class);
        bind(CalendarService.class).to(CalendarServiceImpl.class);
    }
}
