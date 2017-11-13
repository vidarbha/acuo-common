package com.acuo.common.http.client;

import java.io.IOException;

public class DefaultResponse implements Response {

    private final boolean succesful;
    private final String body;

    DefaultResponse(Exception exception) {
        this.succesful = false;
        this.body = exception.getMessage();
    }

    DefaultResponse(okhttp3.Response response) throws IOException {
        this.succesful = response.isSuccessful();
        this.body = response.body().string();
    }

    @Override
    public Boolean succesful() {
        return succesful;
    }

    @Override
    public String body() {
        return body;
    }
}
