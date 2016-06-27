package com.acuo.common.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

import javax.inject.Inject;

public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener  {

	@Inject
    private HealthCheckRegistry HC_REGISTRY;

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return HC_REGISTRY;
    }

}