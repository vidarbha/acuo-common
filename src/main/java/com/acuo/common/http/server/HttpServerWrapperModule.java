/*
 * Copyright (c) 2012 Palomino Labs, Inc.
 */

package com.acuo.common.http.server;

import com.acuo.common.app.guice.EventListenerScanner;
import com.acuo.common.app.guice.HandlerScanner;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.servlet.GuiceFilter;

/**
 * Binds an HTTP server with Guice servlet, Jackson and authentication support.
 */
public class HttpServerWrapperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GuiceFilter.class);
        bind(EventListenerScanner.class);
        bind(HandlerScanner.class);
        install(new FactoryModuleBuilder()
            .implement(HttpServerWrapper.class, HttpServerWrapper.class)
            .build(HttpServerWrapperFactory.class));
    }
}
