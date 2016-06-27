package com.acuo.common.app;

import com.acuo.common.http.server.BinderProviderCapture;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.acuo.common.http.server.HttpServerWrapperFactory;
import com.acuo.common.http.server.HttpServerWrapperModule;
import com.acuo.common.metrics.HealthCheckServletContextListener;
import com.acuo.common.metrics.MetricsModule;
import com.acuo.common.metrics.MetricsServletContextListener;
import com.google.inject.*;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContextListener;
import java.util.Collection;
import java.util.logging.LogManager;

public abstract class ResteasyMain {

	private static final Logger LOG = LoggerFactory.getLogger(ResteasyMain.class);

	public ResteasyMain() {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();

		BinderProviderCapture<? extends ServletContextListener> listenerProvider = new BinderProviderCapture<>(
				GuiceResteasyBootstrapServletContextListener.class);

		Injector injector = Guice.createInjector(new ServiceModule(listenerProvider, providers(), modules(), config()));

		ResteasyConfig instance = injector.getInstance(ResteasyConfig.class);
		HttpServerWrapperConfig config = instance.getConfig();
		config.addServletContextListener(injector.getInstance(HealthCheckServletContextListener.class));
		config.addServletContextListener(injector.getInstance(MetricsServletContextListener.class));
		config.addServletContextListenerProvider(listenerProvider);

		try {
			injector.getInstance(HttpServerWrapperFactory.class).getHttpServerWrapper(config).start();
		} catch (Exception e) {
			LOG.error("Error initializing Resteasy application", e);
		}
	}

	public abstract Class<? extends ResteasyConfig> config();

	public abstract Collection<Class<?>> providers();

	public abstract Collection<Module> modules();

	private static class ServiceModule extends AbstractModule {

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

			install(new HttpServerWrapperModule());

			modules.stream().forEach(this::install);

			bind(GuiceResteasyBootstrapServletContextListener.class);

			bind(ResteasyConfig.class).to(config).in(Singleton.class);

			install(new MetricsModule());
			install(new ResteasyServletModule());

			listenerProvider.saveProvider(binder());
		}
	}
}
