package com.acuo.common.app.main;

import com.acuo.common.app.service.ServiceManagerHealthCheck;
import com.acuo.common.http.server.BinderProviderCapture;
import com.acuo.common.http.server.HttpServerWrapper;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.acuo.common.http.server.HttpServerWrapperFactory;
import com.acuo.common.websocket.GuiceResteasyWebSocketContextListener;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContextListener;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogManager;

@Slf4j
public abstract class Main extends AbstractService {

    private final ServiceManager serviceManager;
    private final HttpServerWrapper httpServerWrapper;

    protected Main() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        BinderProviderCapture<? extends ServletContextListener> listenerProvider = new BinderProviderCapture<>(GuiceResteasyWebSocketContextListener.class);

        Injector injector = Guice.createInjector(new MainModule(config(), modules(), providers(), listenerProvider));

        ResteasyConfig instance = injector.getInstance(ResteasyConfig.class);
        HttpServerWrapperConfig config = instance.getConfig();
//        config.addServletContextListener(injector.getInstance(HealthCheckServletContextListener.class));
//        config.addServletContextListener(injector.getInstance(MetricsServletContextListener.class));
//        config.addServletContextListener(injector.getInstance(SwaggerServletContextListener.class));
        config.addServletContextListenerProvider(listenerProvider);

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

        httpServerWrapper = injector.getInstance(HttpServerWrapperFactory.class).getHttpServerWrapper(config);
    }

    public abstract ResteasyConfig config();

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
