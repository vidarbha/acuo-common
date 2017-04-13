package com.acuo.common.calendar;

import com.acuo.common.http.client.EndPointConfig;
import lombok.Data;
import lombok.Getter;

import java.util.concurrent.TimeUnit;


@Data
public class CalendarEndPointConfig implements EndPointConfig {

    private final String scheme = "https";
    private final String host = "api.thomsonreuters.com";
    private final int port = 443;
    private final String path = "tracs-calendars/IsWorkingDay,<local>;<date>/text";
    private final String apikey = "***REMOVED***";
    private final int connectionTimeOut = 60000;
    private final TimeUnit connectionTimeOutUnit = TimeUnit.MILLISECONDS;
    private final boolean useProxy = false;

    @Override
    public int connectionTimeOut() {
        return connectionTimeOut;
    }

    @Override
    public TimeUnit connectionTimeOutUnit() {
        return connectionTimeOutUnit;
    }

    @Override
    public boolean useLocalSocksProxy() {
        return useProxy;
    }
}
