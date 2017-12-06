package com.acuo.common.app.jetty;

import com.acuo.common.app.guice.EventListenerScanner;
import com.acuo.common.app.guice.HandlerScanner;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.servlet.GuiceFilter;

public class JettyModule extends AbstractModule {

    private final JettyServerWrapperConfig config;

    public JettyModule(JettyServerWrapperConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(GuiceFilter.class);
        bind(EventListenerScanner.class);
        bind(HandlerScanner.class);
        bind(JettyServerWrapperConfig.class).toInstance(config);

        install(new FactoryModuleBuilder()
                .implement(JettyServerWrapper.class, JettyServerWrapper.class)
                .build(JettyServerWrapperFactory.class));
    }
}
