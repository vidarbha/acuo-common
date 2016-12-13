package com.acuo.common.app;

import com.acuo.common.http.server.CorsFilter;
import com.google.inject.servlet.ServletModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Singleton;

public class ResteasyServletModule extends ServletModule {
    @Override
    protected void configureServlets() {

        bind(HttpServletDispatcher.class).asEagerSingleton();
        bind(CorsFilter.class).in(Singleton.class);

        serve("/*").with(HttpServletDispatcher.class);
        filter("/*").through(CorsFilter.class);
    }
}
