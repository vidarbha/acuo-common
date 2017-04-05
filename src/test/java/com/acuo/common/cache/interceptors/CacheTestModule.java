package com.acuo.common.cache.interceptors;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * The Class CacheTestModule.
 * @author Brian Du Preez
 */
public class CacheTestModule extends AbstractModule{
    
    /* (non-Javadoc)
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {
        HazelcastMethodCacheInterceptor cache = new HazelcastMethodCacheInterceptor();
        requestInjection(cache);
        
        //Neat way to do factories
        bind(HazelcastInstance.class).toProvider(() -> Hazelcast.newHazelcastInstance());
        bind(CacheKeyStrategy.class).to(DefaultCacheKeyStrategy.class);
        bindConstant().annotatedWith(HazelcastMethodCacheInterceptor.CacheName.class).to("testCache");
        bindInterceptor(Matchers.only(MyLittleTestObject.class), Matchers.annotatedWith(Cacheable.class), cache);
    }

}