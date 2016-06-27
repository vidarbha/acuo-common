package com.acuo.common.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.inject.Inject;

public class MetricsServletContextListener extends MetricsServlet.ContextListener  {

	@Inject
    private MetricRegistry METRIC_REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }

}