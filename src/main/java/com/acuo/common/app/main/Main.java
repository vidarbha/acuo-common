package com.acuo.common.app.main;

import com.acuo.common.app.jetty.JettyServerWrapper;
import com.acuo.common.app.jetty.JettyServerWrapperConfig;
import com.acuo.common.app.service.ServiceManagerHealthCheck;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.inject.Provider;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogManager;

@Slf4j
public abstract class Main extends AbstractService {

    private final ServiceManager serviceManager;
    private final JettyServerWrapper httpServerWrapper;

    protected Main() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        Injector injector = Guice.createInjector(new MainModule(config(), modules(), providers()));

        serviceManager = injector.getInstance(ServiceManager.class);
        final ServiceManager.Listener listener = new ServiceManager.Listener() {
            @Override
            public void healthy() {
                notifyStarted();
            }

            @Override
            public void stopped() {
                notifyStopped();
            }

            @Override
            public void failure(@NotNull final Service service) {
                notifyFailed(service.failureCause());
            }
        };
        serviceManager.addListener(listener);
        ServiceManagerHealthCheck serviceManagerHealthCheck = injector.getInstance(ServiceManagerHealthCheck.class);
        serviceManager.addListener(serviceManagerHealthCheck.serviceManagerListener());

        httpServerWrapper = injector.getInstance(JettyServerWrapper.class);
    }

    public abstract Class<? extends Provider<JettyServerWrapperConfig>> config();

    protected Collection<Module> modules() {
        return Collections.emptyList();
    }

    protected Collection<Class<?>> providers() {
        return Collections.emptyList();
    }

    @Override
    protected final void doStart() {

        Runtime.getRuntime().addShutdownHook(new Thread(this::doStop, "GracefulShutdownThread"));

        serviceManager.startAsync();

        try {
            httpServerWrapper.start();
        } catch (Exception e) {
            log.error("Error initializing Resteasy application", e);
        }
    }

    @Override
    protected final void doStop() {
        try {
            // We have some shutdown logic to ensure that files are cleaned up so give it a chance to
            // run
            serviceManager.stopAsync().awaitStopped(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.warn("Timeout waiting for shutdown to complete", e);
        }
    }
}
