package com.acuo.common.cache.hashbelt.multithreaded;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.acuo.common.cache.base.Cache;
import com.acuo.common.cache.base.CacheAcquireException;
import com.acuo.common.cache.hashbelt.AbstractHashbelt;
import com.acuo.common.cache.hashbelt.FIFOHashbelt;
import com.acuo.common.cache.hashbelt.container.WeakReferenceContainer;

/**
 * Test multithreaded FIFOHashbelt with WeakReferenceContainer.
 */
public final class TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer
        extends AbstractTestMultiThreadedHashbelt {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "MultiThreadedFIFOHashbeltWithWeakReferenceContainer Tests");

        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testPutThenGet"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testGetThenPut"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testContainsKey"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testContainsValue"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testClear"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testSize"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testIsEmpty"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testRemove"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testPutAll"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testKeySet"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testValues"));
        suite.addTest(new TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(
                "testEntrySet"));

        return suite;
    }

    public TestMultiThreadedFIFOHashbeltWithWeakReferenceContainer(final String name) {
        super(name);
    }

    @Override
    protected Cache<String, String> initialize() throws CacheAcquireException {
        Cache<String, String> cache = new FIFOHashbelt<String, String>();
        Properties params = new Properties();
        params.put(Cache.PARAM_NAME, "dummy1");
        params.put(AbstractHashbelt.PARAM_CONTAINER_CLASS, WeakReferenceContainer.class.getName());
        cache.initialize(params);

        return cache;
    }
}
