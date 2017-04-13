package com.acuo.common.calendar;

import com.acuo.common.http.client.Call;
import com.acuo.common.http.client.CallBuilder;
import com.acuo.common.http.client.ClientEndPoint;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class CalendarCall extends CallBuilder<CalendarCall> {

    private final ClientEndPoint<CalendarEndPointConfig> client;

    private Request.Builder builder;


    private CalendarCall(ClientEndPoint<CalendarEndPointConfig> client )
    {
        this.client = client;
    }

    public static CalendarCall of(ClientEndPoint<CalendarEndPointConfig> client)
    {
        return new CalendarCall(client);
    }

    public CalendarCall with(String key, String value) {
        CalendarEndPointConfig config = client.config();
        HttpUrl url = new HttpUrl.Builder()
                .scheme(config.getScheme())
                .host(config.getHost())
                .port(config.getPort())
                .addPathSegments(config.getPath().replace("<date>", value).replace("<local>", key))
                .addQueryParameter("apikey", config.getApikey())
                .build();
        builder = new Request.Builder().url(url);
        return this;
    }

    public Call create() {
        Request request = builder.build();
        return client.call(request, predicate);
    }

}
