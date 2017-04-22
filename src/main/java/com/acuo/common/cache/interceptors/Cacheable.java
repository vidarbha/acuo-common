package com.acuo.common.cache.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The Interface Cachable.
 * @author Brian Du Preez
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
@interface Cacheable {}