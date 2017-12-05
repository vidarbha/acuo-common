package com.acuo.common.app.jetty;

import com.acuo.common.app.guice.EventListenerScanner;
import com.acuo.common.app.guice.HandlerScanner;
import com.acuo.common.app.main.ResteasyConfig;
import com.acuo.common.http.server.HttpServerWrapper;
import com.acuo.common.http.server.HttpServerWrapperFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.servlet.GuiceFilter;

public class JettyModule extends AbstractModule {

    private final ResteasyConfig config;

    public JettyModule(ResteasyConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(GuiceFilter.class);
        bind(EventListenerScanner.class);
        bind(HandlerScanner.class);
        bind(ResteasyConfig.class).toInstance(config);

        install(new FactoryModuleBuilder()
                .implement(HttpServerWrapper.class, HttpServerWrapper.class)
                .build(HttpServerWrapperFactory.class));
    }
}
