package com.acuo.common.app.api;

import com.acuo.common.app.jetty.JettyServerWrapperConfig;
import com.acuo.common.websocket.GuiceResteasyWebSocketContextListener;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.ServletContextListener;
import java.util.Map;

public class ApiModule extends ServletModule {

    private final JettyServerWrapperConfig config;

    @Inject
    public ApiModule(JettyServerWrapperConfig config) {
        this.config = config;
    }

    @Override
    protected void configureServlets() {

        bind(GuiceResteasyWebSocketContextListener.class);

        Multibinder<ServletContextListener> multibinder = Multibinder.newSetBinder(binder(), ServletContextListener.class);
        multibinder.addBinding().to(GuiceResteasyWebSocketContextListener.class);

        bind(HttpServletDispatcher.class).asEagerSingleton();

        String baseUrl = config.getApiPath() != null ? config.getApiPath() : "/*";
        final Map<String, String> initParams = ImmutableMap.of("resteasy.servlet.mapping.prefix", baseUrl);
        String servingPath = baseUrl + (baseUrl.endsWith("/*") ? "" : "/*");
        serve(servingPath).with(HttpServletDispatcher.class, initParams);
    }

}
