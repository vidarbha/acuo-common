package com.acuo.common.app.swagger;

import com.acuo.common.app.jetty.JettyResourceHandlerConfig;
import com.acuo.common.app.jetty.JettyServerWrapperConfig;
import com.google.common.collect.Lists;
import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.eclipse.jetty.util.resource.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

public class SwaggerModule extends ServletModule {

    private final JettyServerWrapperConfig config;

    @Inject
    public SwaggerModule(JettyServerWrapperConfig config) {
        this.config = config;
    }

    @Override
    protected void configureServlets() {

        bind(SwaggerServletContextListener.class);
        bind(ApiOriginFilter.class).in(Singleton.class);
        bind(ApiListingResource.class);
        bind(SwaggerSerializers.class);

        JettyResourceHandlerConfig rhConfig = new JettyResourceHandlerConfig()
                .withBaseResource(Resource.newClassPathResource("/swagger-ui"))
                .withWelcomeFiles(Lists.newArrayList("index.html"))
                .withEtags(true)
                .withContextPath("/swagger-ui");

        config.addResourceHandlerConfig(rhConfig);

        String baseUrl = config.getApiPath() != null ? config.getApiPath() : "/*";
        String servingPath = baseUrl + (baseUrl.endsWith("/*") ? "" : "/*");
        filter(servingPath).through(ApiOriginFilter.class);
    }
}