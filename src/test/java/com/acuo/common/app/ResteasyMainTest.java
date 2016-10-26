package com.acuo.common.app;

import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Module;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

public class ResteasyMainTest {

    ServiceManager serviceManager;

    ResteasyMain server;

    static class AppConfig implements ResteasyConfig {

        @Override
        public HttpServerWrapperConfig getConfig() {
            HttpServerWrapperConfig config = new HttpServerWrapperConfig();
            return config;
        }
    }

    class AppTest extends ResteasyMain {

        @Override
        public Class<? extends ResteasyConfig> config() {
            return AppConfig.class;
        }

        @Override
        public Collection<Class<?>> providers() {
            return Collections.emptyList();
        }

        @Override
        public Collection<Module> modules() {
            return Collections.emptyList();
        }
    }

    @Before
    public void setUp() throws Exception {
        server = new AppTest();
        serviceManager = new ServiceManager(ImmutableList.of(server));
        serviceManager.addListener(new ServiceManager.Listener() {
            @Override
            public void failure(Service service) {
                System.exit(-1);
            }
        }, directExecutor());
    }

    @Test
    public void firstTest() {
        serviceManager.startAsync().awaitHealthy();
    }

    @After
    public void tearDown() throws Exception {
        serviceManager.stopAsync().awaitStopped(1L, TimeUnit.SECONDS);
    }

}