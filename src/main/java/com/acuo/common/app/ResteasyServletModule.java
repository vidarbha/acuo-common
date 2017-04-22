package com.acuo.common.app;

import com.google.inject.servlet.ServletModule;
import com.thetransactioncompany.cors.CORSFilter;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Singleton;

public class ResteasyServletModule extends ServletModule {
    @Override
    protected void configureServlets() {

        bind(HttpServletDispatcher.class).asEagerSingleton();
        bind(CORSFilter.class).in(Singleton.class);

        serve("/*").with(HttpServletDispatcher.class);
        filter("/*").through(CORSFilter.class);
    }
}
