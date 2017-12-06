package com.acuo.common.app.main;

import com.acuo.common.app.jetty.JettyResourceHandlerConfig;

public interface ServerConfig {

    void addResourceHandlerConfig(JettyResourceHandlerConfig config);

    String getApiPath();
}
