package com.acuo.common.app.main;

import com.acuo.common.app.api.ApiModule;
import com.acuo.common.app.filter.FilterModule;
import com.acuo.common.app.jetty.BinderProviderCapture;
import com.acuo.common.app.jetty.JettyModule;
import com.acuo.common.app.jetty.JettyServerWrapperConfig;
import com.acuo.common.app.service.ServiceModule;
import com.acuo.common.app.swagger.SwaggerModule;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

import java.util.Collection;

public class MainModule extends AbstractModule {

  private final JettyServerWrapperConfig config;
  private final Collection<Module> modules;
  private final Collection<Class<?>> providers;
  private final BinderProviderCapture<?> listenerProvider;

  public MainModule(JettyServerWrapperConfig config,
                    Collection<Module> modules,
                    Collection<Class<?>> providers,
                    BinderProviderCapture<?> listenerProvider) {
    this.config = config;
    this.modules = modules;
    this.providers = providers;
    this.listenerProvider = listenerProvider;
  }

  @Override
  protected void configure() {
    binder().requireExplicitBindings();

    providers.forEach(this::bind);
    modules.forEach(this::install);

    install(new FilterModule());
    install(new ServiceModule());
    install(new SwaggerModule(config));
    install(new ApiModule(config));
    install(new JettyModule(config));

    //install(new GsonModule());

    listenerProvider.saveProvider(binder());
  }
}