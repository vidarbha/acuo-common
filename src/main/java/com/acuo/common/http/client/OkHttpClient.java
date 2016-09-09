package com.acuo.common.http.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.function.Predicate;

@Slf4j
public class OkHttpClient<T extends EndPointConfig> implements ClientEndPoint<T> {

    private final T config;
    private final okhttp3.OkHttpClient httpClient;

    @Inject
    public OkHttpClient(okhttp3.OkHttpClient httpClient, T config) {
        this.config = config;
        log.debug("OkHttpClient default connection timeout in ms:" + httpClient.connectTimeoutMillis());
        this.httpClient = httpClient.newBuilder()
                .connectTimeout(config.connectionTimeOut(), config.connectionTimeOutUnit())
                .readTimeout(config.connectionTimeOut(), config.connectionTimeOutUnit())
                .writeTimeout(config.connectionTimeOut(), config.connectionTimeOutUnit())
                .build();
        log.info("Create Markit Http Client with {}", config.toString());
    }

    @Override
    public T config() {
        return config;
    }

    @Override
    public Call call(Request request, Predicate<String> predicate) {
        return new OkHttpCall(this, request, predicate);
    }

    @Override
    public String send(Call call) {
        try {
            String result = null;
            while (result == null) {
                String response = execute(call.getRequest());
                if (call.getPredicate().test(response)) {
                    result = "an error occurred";
                } else {
                    result = response;
                }
            }
            return result;
        } catch (Exception e) {
            log.error("Failed to send the request, the error message {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected String execute(Request request) {
        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                String msg = String.format("Response unsuccesful, unexpected code returned [%d], %s", response.code(), response.body().string());
                throw new IOException(msg);
            }
            return response.body().string();
        } catch (IOException ioe) {
            log.error("Failed to create {}, the error message {}", request, ioe.getMessage(), ioe);
            throw new RuntimeException(ioe.getMessage(), ioe);
        }
    }

}
