package com.acuo.common.cache.manager;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import com.acuo.common.cache.base.Cache;

public class CacheCleanerUpper implements Runnable
{
	private static Log LOG = LogFactory.getLog(CacheCleanerUpper.class);

	private static final ThreadFactory   THREAD_FACTORY   = new ThreadFactoryBuilder().setDaemon(false).setNameFormat("Cache Cleaner Upper-%s QThread").build();
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor(THREAD_FACTORY);

	private final Cache<Object, Cacheable> cache;
	private volatile boolean stopped = false;

	/* The default time the cleaning task should sleep between scans. */
	private int milliSecondSleepTime = 10000;

	CacheCleanerUpper(Cache<Object, Cacheable> cache)
	{
		this.cache = cache;
		EXECUTOR_SERVICE.submit(this);
	}

	public void run()
	{
		try
		{
			/* Sets up an infinite loop. The thread will continue looping forever. */
			while (true)
			{
				LOG.trace("Scanning For Expired Objects...");

				/* Get the set of all keys that are in cache. These are the unique identifiers */
				Set<Object> keySet = cache.keySet();

				/* An iterator is used to move through the Keyset */
				Iterator<Object> keys = keySet.iterator();

				/* Sets up a loop that will iterate through each key in the KeySet */
				while (keys.hasNext())
				{
					/*
					 * Get the individual key. We need to hold on to this key in case it needs to be
					 * removed
					 */
					Object key = keys.next();

					/* Get the cacheable object associated with the key inside the cache */
					Cacheable value = (Cacheable) cache.get(key);

					/* Is the cacheable object expired? */
					if (value.isExpired())
					{
						/* Yes it's expired! Remove it from the cache */
						cache.remove(key);
						LOG.trace("ThreadCleanerUpper Running. Found an Expired Object in the Cache.");
					}
				}

				if (stopped)
				{
					return;
				}

				/* Puts the thread to sleep. Don't need to check it continuously */
				Thread.sleep(this.milliSecondSleepTime);
			}
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage(), e);
		}
	}

	public void shutdown()
	{
		stopped = true;
		EXECUTOR_SERVICE.shutdown();
	}
}
