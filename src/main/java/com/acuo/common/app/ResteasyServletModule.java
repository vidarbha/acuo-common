package com.acuo.common.app;

import com.acuo.common.websocket.GuiceResteasyWebSocketContextListener;
import com.google.inject.servlet.ServletModule;
import com.thetransactioncompany.cors.CORSFilter;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Singleton;

public class ResteasyServletModule extends ServletModule {

    private final String path;

    public ResteasyServletModule() {
        this.path = null;
    }

    @Override
    protected void configureServlets() {

        //bind(GuiceResteasyBootstrapServletContextListener.class);
        bind(GuiceResteasyWebSocketContextListener.class);

        bind(HttpServletDispatcher.class).asEagerSingleton();
        bind(CORSFilter.class).in(Singleton.class);

        serve("/*").with(HttpServletDispatcher.class);
        filter("/*").through(CORSFilter.class);
    }
}
