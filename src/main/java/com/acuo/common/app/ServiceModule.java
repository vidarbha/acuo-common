package com.acuo.common.app;

import com.acuo.common.app.main.ResteasyConfig;
import com.acuo.common.app.service.ServiceManagerModule;
import com.acuo.common.http.server.BinderProviderCapture;
import com.acuo.common.http.server.HttpServerWrapperModule;
import com.acuo.common.metrics.MetricsModule;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;

import java.util.Collection;

class ServiceModule extends AbstractModule {

    private final BinderProviderCapture<?> listenerProvider;
    private final Collection<Class<?>> providers;
    private final Collection<Module> modules;
    private final Class<? extends ResteasyConfig> config;

    public ServiceModule(BinderProviderCapture<?> listenerProvider, Collection<Class<?>> providers,
                         Collection<Module> modules, Class<? extends ResteasyConfig> config) {
        this.listenerProvider = listenerProvider;
        this.providers = providers;
        this.modules = modules;
        this.config = config;
    }

    @Override
    protected void configure() {
        binder().requireExplicitBindings();

        providers.stream().forEach(this::bind);
        bind(ResteasyConfig.class).to(config).in(Singleton.class);

        modules.forEach(this::install);
        install(new HttpServerWrapperModule());
        install(new MetricsModule());
        install(new ResteasyServletModule());
        install(new ServiceManagerModule());

        listenerProvider.saveProvider(binder());
    }
}