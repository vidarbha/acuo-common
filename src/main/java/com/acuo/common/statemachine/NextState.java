package com.acuo.common.statemachine;

import com.acuo.common.typeref.MethodFinder;

import java.util.Objects;
import java.util.function.Supplier;

public interface NextState<T> extends Supplier<T>, MethodFinder {
    default Class<T> type() {
        try {
            return Objects.equals(method().getName(), "<init>")
                    ? (Class<T>) getContainingClass()
                    : (Class<T>) method().getReturnType();
        } catch (UnableToGuessMethodException e) {
            return (Class<T>) getContainingClass();
        }
    }
}