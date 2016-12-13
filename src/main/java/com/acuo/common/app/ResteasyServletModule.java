package com.acuo.common.app;

import com.acuo.common.http.server.CorsFilter;
import com.google.inject.servlet.ServletModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class ResteasyServletModule extends ServletModule {
    @Override
    protected void configureServlets() {

        bind(HttpServletDispatcher.class).asEagerSingleton();
        serve("/*").with(HttpServletDispatcher.class);

        filter("/*").through(CorsFilter.class);
    }
}
