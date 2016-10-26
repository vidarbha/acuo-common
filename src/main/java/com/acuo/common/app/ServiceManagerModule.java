package com.acuo.common.app;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import java.util.Set;

public class ServiceManagerModule extends AbstractModule {
    @Override
    protected void configure() {
        // Ensure that the binding exists, even if it is empty.
        Multibinder.newSetBinder(binder(), Service.class);

        bind(ServiceManagerHealthCheck.class);
    }

    @Provides
    @Singleton
    ServiceManager provideServiceManager(Set<Service> services) {
        return new ServiceManager(services);
    }
}
