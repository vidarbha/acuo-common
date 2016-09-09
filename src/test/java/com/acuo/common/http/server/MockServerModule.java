package com.acuo.common.http.server;

import com.acuo.common.http.client.EndPointConfig;
import com.google.inject.AbstractModule;
import okhttp3.mockwebserver.MockWebServer;

public class MockServerModule extends AbstractModule {

    MockWebServer server = new MockWebServer();

    @Override
    protected void configure() {
        bind(MockWebServer.class).toInstance(server);
        //bind(EndPointConfig.class).toInstance(markitEndPointConfig);
    }
}
