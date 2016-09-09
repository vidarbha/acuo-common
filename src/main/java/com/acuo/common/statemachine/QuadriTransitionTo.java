package com.acuo.common.statemachine;

import java.util.function.Supplier;

@Transition
public interface QuadriTransitionTo<T extends StateGuards, U extends StateGuards, V extends StateGuards, X extends StateGuards> extends TriTransitionTo<T, U, V>  {
    interface FourTransition<T> extends Supplier<T> { }
    default X transition(QuadriTransitionTo.FourTransition<X> constructor) {
        return withGuards(constructor.get());
    }
}
