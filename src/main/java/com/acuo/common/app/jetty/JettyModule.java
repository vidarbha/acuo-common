package com.acuo.common.app.jetty;

import com.acuo.common.app.guice.EventListenerScanner;
import com.acuo.common.app.guice.HandlerScanner;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.GuiceFilter;

import javax.inject.Inject;

public class JettyModule extends AbstractModule {

    private final JettyServerWrapperConfig config;

    @Inject
    public JettyModule(JettyServerWrapperConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(GuiceFilter.class);
        bind(EventListenerScanner.class);
        bind(HandlerScanner.class);
        bind(JettyServerWrapperConfig.class).toInstance(config);
        bind(JettyServerWrapper.class);
    }
}
