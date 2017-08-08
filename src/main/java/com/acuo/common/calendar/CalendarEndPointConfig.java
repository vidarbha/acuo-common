package com.acuo.common.calendar;

import com.acuo.common.http.client.EndPointConfig;
import lombok.Data;
import lombok.ToString;
import org.jasypt.encryption.pbe.PBEStringEncryptor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

import static com.acuo.common.util.PropertiesHelper.*;


@Data
@ToString(exclude="apikey")
public class CalendarEndPointConfig implements EndPointConfig {

    private static final TimeUnit connectionTimeOutUnit = TimeUnit.MILLISECONDS;

    private final String scheme;
    private final String host;
    private final int port;
    private final String path;
    private final String apikey;
    private final int connectionTimeOut;
    private final boolean useProxy;

    @Inject
    public CalendarEndPointConfig(@Named(ACUO_COMMON_CALENDAR_SCHEME) String scheme,
                                  @Named(ACUO_COMMON_CALENDAR_HOST) String host,
                                  @Named(ACUO_COMMON_CALENDAR_PORT) int port,
                                  @Named(ACUO_COMMON_CALENDAR_PATH) String path,
                                  @Named(ACUO_COMMON_CALENDAR_APIKEY) String apikey,
                                  @Named(ACUO_COMMON_CALENDAR_CONNECTION_TIMEOUT) int connectionTimeOut,
                                  @Named(ACUO_COMMON_CALENDAR_USE_PROXY) boolean useProxy,
                                  PBEStringEncryptor encryptor) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.apikey = (encryptor == null) ? apikey : encryptor.decrypt(apikey);
        this.connectionTimeOut = connectionTimeOut;
        this.useProxy = useProxy;
    }

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
