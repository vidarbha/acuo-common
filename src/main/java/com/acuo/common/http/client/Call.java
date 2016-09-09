package com.acuo.common.http.client;

import okhttp3.Request;

import java.util.function.Predicate;

public interface Call {

    Request getRequest();

    Predicate<String> getPredicate();

    String send();

}
