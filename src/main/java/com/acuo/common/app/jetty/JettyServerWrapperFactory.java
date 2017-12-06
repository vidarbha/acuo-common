package com.acuo.common.app.jetty;

import javax.annotation.Nonnull;

public interface JettyServerWrapperFactory {

    @Nonnull
    JettyServerWrapper getHttpServerWrapper(@Nonnull JettyServerWrapperConfig config);
}
