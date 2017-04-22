package com.acuo.common.cache.interceptors;

import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class HazelcastMethodCacheInterceptor implements MethodInterceptor {

    private HazelcastInstance hazelcastInstance;
    private String cacheName;

    private List<CacheKeyStrategy> keyStrategies;

    public HazelcastMethodCacheInterceptor() {
        keyStrategies = new LinkedList<CacheKeyStrategy>();
    }

    /**
     * Sets teh Strategies.
     * 
     * @param cacheKeys
     *            strat
     */
    public void setCacheKeyStrategies(final List<CacheKeyStrategy> cacheKeys) {
        this.keyStrategies = cacheKeys;
    }

    /**
     * Add a Strategies.
     * 
     * @param cacheKey
     *            strat
     */
    public void addCacheKeyStrategy(final CacheKeyStrategy cacheKey) {
        this.keyStrategies.add(cacheKey);
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object[] arguments = invocation.getArguments();
        Object result;
        StringBuilder cacheKey = new StringBuilder();
        CacheKeyStrategy defaultKeyStrat = new DefaultCacheKeyStrategy(invocation);

        if (!keyStrategies.isEmpty()) {
            if(log.isTraceEnabled()) log.trace("Have a Key Strategy to use...");
            for (CacheKeyStrategy strat : keyStrategies) {
                if ((arguments != null) && (arguments.length != 0)) {
                    if(log.isTraceEnabled()) log.trace("Have Arguments...");
                    for (Object arg : arguments) {
                        if(log.isTraceEnabled()) {
                            log.trace("Class for Strategy: " + strat.classForStrategy());
                            log.trace("Arguments: " + arg);
                        }
                        if (Class.forName(strat.classForStrategy()).isInstance(arg)) {
                            strat.setObject(arg);
                            if(log.isTraceEnabled()) log.trace("Using Strategy...");
                            cacheKey.append(strat.generateKey());
                        }
                    }
                }
            }
        }

        if (cacheKey.length() == 0) {
            if(log.isTraceEnabled()) log.trace("Using Default...");
            cacheKey.append(defaultKeyStrat.generateKey());
        }
        EntryView<String, Serializable> entry = getCache().getEntryView(cacheKey.toString());
        
        // not in cache
        if (entry == null) {
            result = invocation.proceed();
            if (result != null && !(result instanceof Serializable)) {
                throw new RuntimeException("[" + result.getClass().getName() + "] is not Serializable");
            }
            if(log.isTraceEnabled()) log.trace(">>> caching result - " + cacheKey);
            getCache().put(cacheKey.toString(), (Serializable) result);
        } else {
            if(log.isTraceEnabled()) log.trace(">>> returning result from cache");
            return entry.getValue();
        }

        return result;
    }

    /**
     * Gets the actual cache.
     * 
     * @return the cache
     */
    private IMap<String, Serializable> getCache() {
        return hazelcastInstance.getMap(getCacheName());
    }
    

    /**
     * Sets the hazelcast instance.
     * 
     * @param hazelcastInstance
     *            the new hazelcast instance
     */
    @Inject
    public final void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * Gets the cache name.
     * 
     * @return the cache name
     */
    private final String getCacheName() {
        return cacheName;
    }

    /**
     * Sets the cache name.
     * 
     * @param cacheName
     *            the new cache name
     */
    @Inject
    public final void setCacheName(@CacheName String cacheName) {
        this.cacheName = cacheName;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @BindingAnnotation
    public @interface CacheName {}
}