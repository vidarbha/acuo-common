package com.acuo.common.typeref;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface MethodAwareFunction<T,R> extends Function<T,R>, MethodFinder { }