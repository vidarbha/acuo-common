package com.acuo.common.statemachine;

public interface Action<T extends State<T>> {
    void perform(T state);
}
