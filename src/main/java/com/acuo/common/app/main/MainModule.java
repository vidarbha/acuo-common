package com.acuo.common.app.main;

import com.acuo.common.app.api.ApiModule;
import com.acuo.common.app.filter.FilterModule;
import com.acuo.common.app.jetty.JettyModule;
import com.acuo.common.app.jetty.JettyServerWrapperConfig;
import com.acuo.common.app.service.ServiceModule;
import com.acuo.common.app.swagger.SwaggerModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;

import javax.inject.Provider;
import java.util.Collection;

public class MainModule extends AbstractModule {

  private final Class<? extends Provider<JettyServerWrapperConfig>> config;
  private final Collection<Module> modules;
  private final Collection<Class<?>> providers;

  public MainModule(Class<? extends Provider<JettyServerWrapperConfig>> config,
                    Collection<Module> modules,
                    Collection<Class<?>> providers) {
    this.config = config;
    this.modules = modules;
    this.providers = providers;
  }

  @Override
  protected void configure() {
    binder().requireExplicitBindings();

    providers.forEach(this::bind);
    modules.forEach(this::install);

    install(new FilterModule());
    install(new ServiceModule());

    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        modules.forEach(this::install);
        bind(JettyServerWrapperConfig.class).toProvider(config).in(Scopes.SINGLETON);
      }
    });
    install(injector.getInstance(SwaggerModule.class));
    install(injector.getInstance(ApiModule.class));
    install(injector.getInstance(JettyModule.class));

    //install(new GsonModule());
  }
}