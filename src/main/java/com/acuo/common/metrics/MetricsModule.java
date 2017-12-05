package com.acuo.common.metrics;

import com.acuo.common.websocket.GuiceResteasyWebSocketContextListener;
import com.codahale.metrics.*;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.palominolabs.metrics.guice.MetricsInstrumentationModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MetricsModule extends AbstractModule {

	private static final Logger log = LoggerFactory.getLogger(MetricsModule.class);

	private void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
		for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
	        if (entry.getValue() instanceof MetricSet) {
	            registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
	        } else {
	            registry.register(prefix + "." + entry.getKey(), entry.getValue());
	        }
	    }
	}	
	protected void configure() {		
		MetricRegistry mr = new MetricRegistry();
		
		registerAll("gc", new GarbageCollectorMetricSet(), mr);
		registerAll("buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()), mr);
		registerAll("memory", new MemoryUsageGaugeSet(), mr);
		registerAll("threads", new ThreadStatesGaugeSet(), mr);
		
		bind(MetricRegistry.class).toInstance(mr);
		log.info("MetricsModule mr:" + mr.toString());
		
		HealthCheckRegistry hr = new HealthCheckRegistry();
		bind(HealthCheckRegistry.class).toInstance(hr);
		log.info("MetricsModule hr:" + hr.toString());
		
		bind(MetricsServletContextListener.class);
		bind(HealthCheckServletContextListener.class);

		Multibinder<ServletContextListener> multibinder = Multibinder.newSetBinder(binder(), ServletContextListener.class);
		multibinder.addBinding().to(MetricsServletContextListener.class);
		multibinder.addBinding().to(HealthCheckServletContextListener.class);

		install(new MetricsInstrumentationModule(mr));
		install(new HelthChecksInstrumentationServletModule(hr));

		final Slf4jReporter reporter = Slf4jReporter.forRegistry(mr)
				.outputTo(LoggerFactory.getLogger("com.acuo.metrics"))
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build();
		reporter.start(30, TimeUnit.SECONDS);

		JmxReporter.forRegistry(mr).build().start();
		//ConsoleReporter.forRegistry(mr).build().start(10, TimeUnit.SECONDS);
	}
}