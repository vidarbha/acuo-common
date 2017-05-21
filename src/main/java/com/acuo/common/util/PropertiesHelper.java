package com.acuo.common.util;

import com.acuo.common.app.Configuration;

public class PropertiesHelper extends BasePropertiesHelper {

    public static final String ACUO_CONFIG_APPID = "acuo.config.appid";
    public static final String ACUO_CONFIG_ENV = "acuo.config.env";

    public static final String ACUO_COMMON_CALENDAR_SCHEME = "acuo.common.calendar.scheme";
    public static final String ACUO_COMMON_CALENDAR_HOST = "acuo.common.calendar.host";
    public static final String ACUO_COMMON_CALENDAR_PORT = "acuo.common.calendar.port";
    public static final String ACUO_COMMON_CALENDAR_PATH = "acuo.common.calendar.path";
    public static final String ACUO_COMMON_CALENDAR_APIKEY = "acuo.common.calendar.apikey";
    public static final String ACUO_COMMON_CALENDAR_CONNECTION_TIMEOUT = "acuo.common.calendar.connection.timeout";
    public static final String ACUO_COMMON_CALENDAR_USE_PROXY = "acuo.common.calendar.use.proxy";

    private PropertiesHelper(Configuration configuration) {
        super(configuration);
    }

    public static PropertiesHelper of(Configuration configuration) {
        ArgChecker.notNull(configuration, "configuration");
        return new PropertiesHelper(configuration);
    }

}
