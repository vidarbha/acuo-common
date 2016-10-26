package com.acuo.common.app;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;

import static com.codahale.metrics.health.HealthCheck.Result.unhealthy;

public class ServiceManagerHealthCheck extends HealthCheck {

    private final ServiceManagerListener serviceManagerListener;
    private Result result = unhealthy("Services are not started");

    ServiceManagerHealthCheck() {
        serviceManagerListener = new ServiceManagerListener();
    }

    public class ServiceManagerListener extends ServiceManager.Listener {

        @Override
        public void healthy() {
            result = Result.healthy("All services are started");
        }

        @Override
        public void stopped() {
            result = unhealthy("All services are stopped");
        }

        @Override
        public void failure(Service service) {
            result = unhealthy("Service %s failed : %s", service, service.failureCause());
        }
    }

    @Override
    protected Result check() throws Exception {
        return result;
    }

    public ServiceManagerListener serviceManagerListener() {
        return serviceManagerListener;
    }
}