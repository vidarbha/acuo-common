package com.acuo.common.async;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.container.Jetty7CometSupport;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.guice.AtmosphereGuiceServlet;
import org.atmosphere.guice.GuiceObjectFactory;
import org.atmosphere.handler.AbstractReflectorAtmosphereHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GuiceConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(Resource.class);
                bind(new TypeLiteral<Map<String, String>>() {
                }).annotatedWith(Names.named(AtmosphereGuiceServlet.PROPERTIES)).toInstance(
                        Collections.<String, String>emptyMap());

                serve("/*").with(AtmosphereGuiceServlet.class, new HashMap<String, String>() {
                    {
                        put(ApplicationConfig.PROPERTY_COMET_SUPPORT, Jetty7CometSupport.class.getName());
                        put(ApplicationConfig.OBJECT_FACTORY, GuiceObjectFactory.class.getName());
                        put("org.atmosphere.useNative", "true");
                        put(ApplicationConfig.ANNOTATION_PACKAGE, Resource.class.getPackage().getName());
                    }
                });
            }
        });
    }

    @AtmosphereHandlerService(path="/")
    public final static class Resource extends AbstractReflectorAtmosphereHandler {

        @Override
        public void onRequest(final AtmosphereResource r) throws IOException {
            r.addEventListener(new AtmosphereResourceEventListenerAdapter() {
              @Override
                public void onSuspend(AtmosphereResourceEvent e) {
                  try {
                      r.write("resume").close();
                  } catch (IOException e1) {
                      e1.printStackTrace();
                  }
              }

            }).suspend();
        }
    }
}