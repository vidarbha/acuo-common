package com.acuo.common.cache.interceptors;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.acuo.common.util.GuiceJUnitRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * The Class TestAspectCache.
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ CacheTestModule.class })
@Slf4j
public class TestHazelcastCacheAspect {

    @Inject
    private MyLittleTestObject hazelTestObject;

    @Before
    public void setUp() {
        hazelTestObject.theOtherBusinessMethod("test");
    }

    /**
     * Check aspect.
     */
    @Test
    public void checkHazelcastAspect() {

        long starttime = new Date().getTime();
        String returnstr = hazelTestObject.theBusinessMethod("test");
        log.info("Ran the Hazelcast method: {}", returnstr);
        long donetime = new Date().getTime();
        long totalTime1 = donetime - starttime;
        log.info("theOtherBusinessMethod()> 1 done - time: {}", totalTime1);

        assertEquals(returnstr, "Returning from business method with value: test 1000000");

    }

    /**
     * Test value processed only once
     */
    @Test
    public void cachedHazelcastTimeTest() {
        int i = 0;
        while (i < 1000000) {
            hazelTestObject.theOtherBusinessMethod("test");
            i++;
        }
        assertEquals(1, hazelTestObject.counts());
    }

}