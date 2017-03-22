package com.acuo.common.cache.hashbelt.multithreaded;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.acuo.common.cache.base.Cache;
import com.acuo.common.cache.base.CacheAcquireException;
import com.acuo.common.cache.hashbelt.AbstractHashbelt;
import com.acuo.common.cache.hashbelt.FIFOHashbelt;
import com.acuo.common.cache.hashbelt.container.FastIteratingContainer;

/**
 * Test multithreaded FIFOHashbelt with FastIteratingContainer.
 */
public final class TestMultiThreadedFIFOHashbeltWithFastIteratingContainer
        extends AbstractTestMultiThreadedHashbelt {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "MultiThreadedFIFOHashbeltWithFastIteratingContainer Tests");

        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testPutThenGet"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testGetThenPut"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testContainsKey"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testContainsValue"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testClear"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testSize"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testIsEmpty"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testRemove"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testPutAll"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testKeySet"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testValues"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(
                "testEntrySet"));

        return suite;
    }

    public TestMultiThreadedFIFOHashbeltWithFastIteratingContainer(final String name) {
        super(name);
    }

    @Override
    protected Cache<String, String> initialize() throws CacheAcquireException {
        Cache<String, String> cache = new FIFOHashbelt<String, String>();
        Properties params = new Properties();
        params.put(Cache.PARAM_NAME, "dummy1");
        params.put(AbstractHashbelt.PARAM_CONTAINER_CLASS, FastIteratingContainer.class.getName());
        
        cache.initialize(params);

        return cache;
    }
}
