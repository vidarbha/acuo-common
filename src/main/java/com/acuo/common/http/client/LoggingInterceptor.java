package com.acuo.common.http.client;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        LOG.info("Sending {} request on {} with headers {}", request.method(), request.url(), request.headers());

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        LOG.info("Received response for {} in {}ms {}", response.request().url(), (t2 - t1) / 1e6d, response.headers());

        return response;
    }
}