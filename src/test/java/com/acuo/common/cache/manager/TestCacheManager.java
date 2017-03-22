package com.acuo.common.cache.manager;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestCacheManager
{
	/*
	 * This is the object that we are going to cache. Admittedly this is a trivial object to cache.
	 * You might replace our alphabet with a list of every character in the world.
	 */
	private String			s	= new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

	private CacheManager	cacheManager;

	@Before
	public void setUp() throws Exception
	{
		BasicConfigurator.configure();
		cacheManager = CacheManager.getInstance();
	}

	@After
	public void tearDown() throws Exception
	{
		cacheManager.closeCaches();
	}

	@Test
	public final void testGetInstance()
	{
		assertNotNull(cacheManager);
	}

	@Test
	public final void testPutAndGetCache()
	{
		/*
		 * Create an instance of CachedObject, set the minutesToLive to 1 minute. Give the object
		 * some unique identifier.
		 */
		CachedObject co = new CachedObject(s, new Long(1234), 1);

		/* Place the object into the cache! */
		cacheManager.putCache(co);

		/* Try to retrieve the object from the cache! */
		CachedObject o = (CachedObject) cacheManager.getCache(new Long(1234));

		/* Let's see if we got it! */
		assertNotNull("Object not found in cache", o);
	}

	@Test
	public final void testPutAndExpired() throws InterruptedException
	{
		/*
		 * Create an instance of CachedObject, set the minutesToLive to 1 minute. Give the object
		 * some unique identifier.
		 */
		CachedObject co = new CachedObject(s, new Long(1234), 20, TimeUnit.SECONDS);

		/* Place the object into the cache! */
		cacheManager.putCache(co);

		/* Sleep for more than 20 seconds */
		Thread.sleep(1100 * 20);

		/* Try to retrieve the object from the cache! */
		CachedObject o = (CachedObject) cacheManager.getCache(new Long(1234));

		/* Let's see if we got it! */
		assertNull("Expired object found in cache", o);
	}

}
