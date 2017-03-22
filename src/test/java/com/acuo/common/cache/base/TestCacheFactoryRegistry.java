/*
 * Copyright 2005 Werner Guttmann, Ralf Joachim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acuo.common.cache.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;

import com.acuo.common.cache.distributed.CoherenceCache;
import com.acuo.common.cache.distributed.CoherenceCacheFactory;
import com.acuo.common.cache.distributed.EHCache;
import com.acuo.common.cache.distributed.EHCacheFactory;
import com.acuo.common.cache.distributed.FKCache;
import com.acuo.common.cache.distributed.FKCacheFactory;
import com.acuo.common.cache.distributed.GigaspacesCache;
import com.acuo.common.cache.distributed.GigaspacesCacheFactory;
import com.acuo.common.cache.distributed.JCache;
import com.acuo.common.cache.distributed.JCacheFactory;
import com.acuo.common.cache.distributed.JcsCache;
import com.acuo.common.cache.distributed.JcsCacheFactory;
import com.acuo.common.cache.distributed.OsCache;
import com.acuo.common.cache.distributed.OsCacheFactory;
import com.acuo.common.cache.exceptions.PropertiesException;
import com.acuo.common.cache.hashbelt.FIFOHashbelt;
import com.acuo.common.cache.hashbelt.FIFOHashbeltFactory;
import com.acuo.common.cache.hashbelt.LRUHashbelt;
import com.acuo.common.cache.hashbelt.LRUHashbeltFactory;
import com.acuo.common.cache.simple.CountLimited;
import com.acuo.common.cache.simple.CountLimitedFactory;
import com.acuo.common.cache.simple.NoCache;
import com.acuo.common.cache.simple.NoCacheFactory;
import com.acuo.common.cache.simple.TimeLimited;
import com.acuo.common.cache.simple.TimeLimitedFactory;
import com.acuo.common.cache.simple.Unlimited;
import com.acuo.common.cache.simple.UnlimitedFactory;
import com.acuo.common.cache.util.AbstractProperties;
import com.acuo.common.cache.util.CacheProperties;

/**
 * @author <a href="mailto:werner DOT guttmann AT gmx DOT net">Werner Guttmann</a>
 * @author <a href="mailto:ralf DOT joachim AT syscon DOT eu">Ralf Joachim</a>
 * @version $Revision: 9041 $ $Date: 2011-08-16 11:51:17 +0200 (Di, 16 Aug 2011) $
 * @since 1.0
 */
public final class TestCacheFactoryRegistry extends TestCase {
    private static final boolean DISABLE_LOGGING = true;
    
    private CacheFactoryRegistry<String, String> _registry;
    
    public static Test suite() {
        TestSuite suite = new TestSuite("CacheFactoryRegistry Tests");

        suite.addTest(new TestCacheFactoryRegistry("testConstructor"));
        suite.addTest(new TestCacheFactoryRegistry("testGetCacheNames"));
        suite.addTest(new TestCacheFactoryRegistry("testGetCacheFactories"));
        suite.addTest(new TestCacheFactoryRegistry("testGetCache"));

        return suite;
    }

    public TestCacheFactoryRegistry(final String name) { super(name); }

    public void testConstructor() {
        Logger logger = Logger.getLogger(CacheFactoryRegistry.class);
        Level level = logger.getLevel();

        assertEquals("com.acuo.common.cache.Factories", CacheFactoryRegistry.CACHE_FACTORIES);
        
        AbstractProperties properties = CacheProperties.newInstance();
        String memF = properties.getString(CacheFactoryRegistry.CACHE_FACTORIES);
        
        properties.remove(CacheFactoryRegistry.CACHE_FACTORIES);
        new CacheFactoryRegistry<String, String>(properties);
        
        properties.put(CacheFactoryRegistry.CACHE_FACTORIES, "");
        new CacheFactoryRegistry<String, String>(properties);
        
        properties.put(CacheFactoryRegistry.CACHE_FACTORIES, UnlimitedFactory.class.getName());
        new CacheFactoryRegistry<String, String>(properties);
        
        if (DISABLE_LOGGING) { logger.setLevel(Level.FATAL); }

        properties.put(CacheFactoryRegistry.CACHE_FACTORIES, "com.acuo.common.cache.simple.UnknownFactory");
        try {
            new CacheFactoryRegistry<String, String>(properties);
            fail("Should have failed to create unknown class.");
        } catch (PropertiesException ex) {
            assertTrue(true);
        }
        
        logger.setLevel(level);

        properties.put(CacheFactoryRegistry.CACHE_FACTORIES, memF);
    }

    public void testGetCacheNames() {
        AbstractProperties properties = CacheProperties.newInstance();
        Collection<String> col = new CacheFactoryRegistry<String, String>(
                properties).getCacheNames();
        assertEquals(13, col.size());
        assertTrue(col.contains(CountLimited.TYPE));
        assertTrue(col.contains(NoCache.TYPE));
        assertTrue(col.contains(TimeLimited.TYPE));
        assertTrue(col.contains(Unlimited.TYPE));
        assertTrue(col.contains(CoherenceCache.TYPE));
        assertTrue(col.contains(FKCache.TYPE));
        assertTrue(col.contains(JCache.TYPE));
        assertTrue(col.contains(JcsCache.TYPE));
        assertTrue(col.contains(OsCache.TYPE));
        assertTrue(col.contains(FIFOHashbelt.TYPE));
        assertTrue(col.contains(LRUHashbelt.TYPE));
        assertTrue(col.contains(EHCache.TYPE));
        assertTrue(col.contains(GigaspacesCache.TYPE));
    }

    public void testGetCacheFactories() {
        AbstractProperties properties = CacheProperties.newInstance();
        Collection<CacheFactory<String, String>> col = new CacheFactoryRegistry<String, String>(
                properties).getCacheFactories();
        assertEquals(13, col.size());
        assertTrue(containsInstanceOf(col, CountLimitedFactory.class));
        assertTrue(containsInstanceOf(col, NoCacheFactory.class));
        assertTrue(containsInstanceOf(col, TimeLimitedFactory.class));
        assertTrue(containsInstanceOf(col, UnlimitedFactory.class));
        assertTrue(containsInstanceOf(col, CoherenceCacheFactory.class));
        assertTrue(containsInstanceOf(col, FKCacheFactory.class));
        assertTrue(containsInstanceOf(col, JCacheFactory.class));
        assertTrue(containsInstanceOf(col, JcsCacheFactory.class));
        assertTrue(containsInstanceOf(col, OsCacheFactory.class));
        assertTrue(containsInstanceOf(col, FIFOHashbeltFactory.class));
        assertTrue(containsInstanceOf(col, LRUHashbeltFactory.class));
        assertTrue(containsInstanceOf(col, EHCacheFactory.class));
        assertTrue(containsInstanceOf(col, GigaspacesCacheFactory.class));
    }
    
    @SuppressWarnings("rawtypes")
    private boolean containsInstanceOf(final Collection<CacheFactory<String, String>> col,
            final Class<? extends CacheFactory> cls) {
        Iterator<CacheFactory<String, String>> iter = col.iterator();
        while (iter.hasNext()) {
            CacheFactory<String, String> factory = iter.next();
            if (cls.isInstance(factory)) { return true; }
        }
        return false;
    }

    public void testGetCache() throws CacheAcquireException
    {
        Logger logger = Logger.getLogger(CacheFactoryRegistry.class);
        Level level = logger.getLevel();
        
        AbstractProperties properties = CacheProperties.newInstance();
        _registry = new CacheFactoryRegistry<String, String>(properties);
        
        Cache<String, String> cache = null;
        
        if (DISABLE_LOGGING) { logger.setLevel(Level.FATAL); }

        try {
            cache = getCache("not-existing", 0);
            fail("Getting a non existent cache should throw an exception.");
        } catch (CacheAcquireException ex) {
            assertNull(cache);
        }

        logger.setLevel(level);
        
        cache = getCache("count-limited", 3); 
        assertEquals("count-limited", cache.getType());
//        assertTrue(cache instanceof CountLimited);
        assertEquals(3, ((CountLimited<String, String>) cache).getCapacity());
        
        cache = getCache("none", 10); 
        assertEquals("none", cache.getType());
        assertTrue(cache instanceof NoCache);

        cache = getCache("time-limited", 10);
        assertEquals("time-limited", cache.getType());
        assertTrue(cache instanceof TimeLimited);
        assertEquals(10, ((TimeLimited<String, String>) cache).getTTL());
        
        cache = getCache("unlimited", 10); 
        assertEquals("unlimited", cache.getType());
        assertTrue(cache instanceof Unlimited);
        
        // Creation of distributed caches can not be tested without having their
        // implementations available on the classpath but testing the construction
        // isn't necessary as we have done it at tests of their factories.
        //
        // If you still like to test you only need to comment in one or all of the
        // following sections.

//        cache = getCache("coherence", 10); 
//        assertEquals("coherence", cache.getType());
//        assertTrue(cache instanceof CoherenceCache);

//        cache = getCache("fkcache", 10); 
//        assertEquals("fkcache", cache.getType());
//        assertTrue(cache instanceof FKCache);

//        cache = getCache("jcache", 10); 
//        assertEquals("jcache", cache.getType());
//        assertTrue(cache instanceof JCache);

//        cache = getCache("jcs", 10); 
//        assertEquals("jcs", cache.getType());
//        assertTrue(cache instanceof JcsCache);
    }
    
    private Cache<String, String> getCache(final String type, final int capacity)
    throws CacheAcquireException {
        Properties props = new Properties();
        props.put(Cache.PARAM_TYPE, type);
        props.put(Cache.PARAM_NAME, "dummy");
        props.put(Cache.PARAM_DEBUG, Cache.DEFAULT_DEBUG);
        props.put(CountLimited.PARAM_CAPACITY, Integer.toString(capacity));
        props.put(TimeLimited.PARAM_TTL, Integer.toString(capacity));

        return _registry.getCache(props, getClass().getClassLoader());
    }
}
