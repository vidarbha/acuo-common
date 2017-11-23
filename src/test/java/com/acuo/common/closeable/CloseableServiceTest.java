package com.acuo.common.closeable;

import com.acuo.common.util.GuiceJUnitRunner;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.mycila.guice.ext.closeable.CloseableInjector;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({CloseableServiceTest.DummyModule.class})
public class CloseableServiceTest {

    @Inject
    private Injector injector = null;

    @Test
    public void test() {
        final CloseableInjector closeableInjector = injector.getInstance(CloseableInjector.class);

        CloseableService closeableService = closeableInjector.getInstance(CloseableService.class);
        assertThat(closeableService.initialized).isEqualTo(1);
        assertThat(closeableService.destroyed).isEqualTo(0);

        closeableInjector.close();

        assertThat(closeableService.initialized).isEqualTo(1);
        assertThat(closeableService.destroyed).isEqualTo(1);
    }

    public static class DummyModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(CloseableService.class);
        }
    }

    @Singleton
    static class CloseableService {

        int initialized;
        int destroyed;

        @PostConstruct
        public void start(){
            initialized++;
        }

        @PreDestroy
        public void close(){
            destroyed++;
        }

    }

}
