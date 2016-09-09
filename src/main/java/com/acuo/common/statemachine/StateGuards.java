package com.acuo.common.statemachine;

public interface StateGuards<T> {
    void afterTransition(T from);
    void beforeTransition(T to);
}