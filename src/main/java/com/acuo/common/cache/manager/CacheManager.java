package com.acuo.common.cache.manager;

import com.acuo.common.cache.base.Cache;
import com.acuo.common.cache.base.CacheAcquireException;
import com.acuo.common.cache.base.CacheFactory;
import com.acuo.common.cache.base.CacheFactoryRegistry;
import com.acuo.common.cache.simple.TimeLimited;
import com.acuo.common.cache.util.AbstractProperties;
import com.acuo.common.cache.util.CacheProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class CacheManager
{
	private static Log          LOG      = LogFactory.getLog(CacheManager.class);
	private static CacheManager INSTANCE = new CacheManager();

	private final CacheFactoryRegistry<Object, Cacheable> cacheFactoryRegistry;
	private final Cache<Object, Cacheable>                cache;
	private final CacheCleanerUpper                       cleaner;

	private CacheManager()
	{
		AbstractProperties properties = CacheProperties.newInstance();
		cacheFactoryRegistry = new CacheFactoryRegistry<Object, Cacheable>(properties);
		Properties props = new Properties();
		props.put(Cache.PARAM_TYPE, "unlimited");
		props.put(Cache.PARAM_NAME, "Cacheable Unlimited");
		props.put(Cache.PARAM_DEBUG, Cache.DEFAULT_DEBUG);
		props.put(TimeLimited.PARAM_TTL, Integer.toString(1000));

		try
		{
			cache = cacheFactoryRegistry.getCache(props, getClass().getClassLoader());
			cleaner = new CacheCleanerUpper(cache);
		}
		catch(CacheAcquireException e)
		{
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static CacheManager getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Close all caches (to allow for resource clean-up).
	 */
	public void closeCaches()
	{
		for (CacheFactory<Object, Cacheable> cacheFactory : cacheFactoryRegistry.getCacheFactories())
		{
			cacheFactory.shutdown();
		}
		cleaner.shutdown();
	}

	/**
	 * Add a Cacheable object into the main cache.
	 * 
	 * @param object
	 *            Cacheable object to add into the cache
	 * @return the previous value associated with the Cacheable object <tt>identifier</tt>, or
	 *         <tt>null</tt> if there was no object for Cacheable object <tt>identifier</tt>.
	 */
	public Cacheable putCache(Cacheable object)
	{
		return cache.put(object.getIdentifier(), object);
	}

	public Cacheable getCache(Object identifier)
	{
		Cacheable object = (Cacheable) cache.get(identifier);

		if (object == null)
			return null;
		if (object.isExpired())
		{
			cache.remove(identifier);
			return null;
		}
		else
		{
			return object;
		}
	}
}
