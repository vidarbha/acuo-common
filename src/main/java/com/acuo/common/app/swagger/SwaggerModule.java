package com.acuo.common.app.swagger;

import com.acuo.common.app.main.ResteasyConfig;
import com.acuo.common.http.server.HttpResourceHandlerConfig;
import com.google.common.collect.Lists;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.eclipse.jetty.util.resource.Resource;

import javax.inject.Singleton;
import javax.servlet.ServletContextListener;

public class SwaggerModule extends ServletModule {

    private final ResteasyConfig config;

    public SwaggerModule(ResteasyConfig config) {
        this.config = config;
    }

    @Override
    protected void configureServlets() {

        bind(SwaggerServletContextListener.class);

        Multibinder<ServletContextListener> multibinder = Multibinder.newSetBinder(binder(), ServletContextListener.class);
        multibinder.addBinding().to(SwaggerServletContextListener.class);

        bind(ApiOriginFilter.class).in(Singleton.class);
        bind(ApiListingResource.class);
        bind(SwaggerSerializers.class);

        HttpResourceHandlerConfig rhConfig = new HttpResourceHandlerConfig()
                .withBaseResource(Resource.newClassPathResource("/swagger-ui"))
                .withWelcomeFiles(Lists.newArrayList("index.html"))
                .withEtags(true)
                .withContextPath("/swagger-ui");

        config.getConfig().addResourceHandlerConfig(rhConfig);

        String baseUrl = config.getConfig().getApiPath() != null ? config.getConfig().getApiPath() : "/*";
        String servingPath = baseUrl + (baseUrl.endsWith("/*") ? "" : "/*");
        filter(servingPath).through(ApiOriginFilter.class);
    }
}