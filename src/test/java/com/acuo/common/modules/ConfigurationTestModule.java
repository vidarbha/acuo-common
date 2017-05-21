package com.acuo.common.modules;

import com.acuo.common.app.AppId;
import com.acuo.common.app.Configuration;
import com.acuo.common.app.Environment;
import com.acuo.common.configuration.PropertiesModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ConfigurationTestModule extends AbstractModule {

	private final Configuration configuration;

	public ConfigurationTestModule() {
		configuration = Configuration.builder(AppId.of("common"))
				.with(Environment.TEST)
				.build();
	}

	@Override
    protected void configure() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Configuration.class).toInstance(configuration);
			}
		});
		install(injector.getInstance(PropertiesModule.class));
		bind(Configuration.class).toInstance(configuration);
	}

}
