package com.acuo.common.http.client;

import java.util.concurrent.TimeUnit;

public interface EndPointConfig {

    int connectionTimeOut();
    TimeUnit connectionTimeOutUnit();

}
