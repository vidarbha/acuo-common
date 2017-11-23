package com.acuo.common.util;

import com.google.inject.AbstractModule;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;

/**
 * Guice module with built-in Mycila Guice Extensions for JSR-250 &amp;
 * Closeable support for {@literal @}PreDestroy &amp; {@literal @}PostConstruct.
 *
 * @author Michael Vorburger.ch
 */
class AnnotationsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new CloseableModule());
        install(new Jsr250Module());
    }

}