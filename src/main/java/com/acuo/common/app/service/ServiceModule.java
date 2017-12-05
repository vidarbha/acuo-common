package com.acuo.common.app.service;

import com.acuo.common.metrics.MetricsModule;
import com.google.inject.AbstractModule;

/**
 * A module to configure the application's services.
 */
public class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new MetricsModule());
    install(new ServiceManagerModule());
  }
}