package com.acuo.common.cache.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * A Generic Cache Object wrapper. Implements the Cacheable interface uses a TimeToLive strategy for
 * CacheObject expiration.
 * 
 * @author Jonathan Lurie
 * @version 1.0
 */
public class CachedObject implements Cacheable
{
	private static Log LOG = LogFactory.getLog(CachedObject.class);

	/* This variable will be used to determine if the object is expired. */
	private java.util.Date dateofExpiration = null;
	private Object         identifier       = null;

	/* This contains the real "value". This is the object which needs to be shared. */
	private Object object = null;

	/**
	 * Create a wrapper around the object to cache.
	 *
	 * @param obj
	 *            the Object to cache.
	 * @param id
	 *            the identifier used to retrieve the object from the cache.
	 * @param minutesToLive
	 *            time to expiry in minutes, 0 means the object wont expire.
	 */
	public CachedObject(Object obj, Object id, int minutesToLive)
	{
		this(obj, id, minutesToLive, MINUTES);
	}

	/**
	 * Create a wrapper around the object to cache.
	 *
	 * @param obj
	 *            the Object to cache.
	 * @param id
	 *            the identifier used to retrieve the object from the cache.
	 * @param expireTime
	 *            time to expiry, 0 means the object wont expire.
	 * @param timeUnit
	 * 			  unit of the time to expiry.
	 */
	public CachedObject(Object obj, Object id, int expireTime, TimeUnit timeUnit)
	{
		this.object = obj;
		this.identifier = id;
		if (expireTime != 0)
		{
			dateofExpiration = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateofExpiration);
			switch (timeUnit) {
				case MILLISECONDS:
					cal.add(Calendar.MILLISECOND, expireTime);
					break;
				case SECONDS:
					cal.add(Calendar.SECOND, expireTime);
					break;
				case MINUTES:
					cal.add(Calendar.MINUTE, expireTime);
					break;
				case HOURS:
					cal.add(Calendar.HOUR, expireTime);
					break;
			}
			dateofExpiration = cal.getTime();
		}
	}

	public boolean isExpired()
	{
		// Remember if the minutes to live is zero then it lives forever!
		if (dateofExpiration != null)
		{
			// date of expiration is compared.
			if (dateofExpiration.before(new java.util.Date()))
			{
				LOG.debug("CachedResultSet.isExpired:  Expired from Cache! EXPIRE TIME: " + dateofExpiration.toString() + " CURRENT TIME: " + (new java.util.Date()).toString());
				return true;
			}
			else
			{
				LOG.debug("CachedResultSet.isExpired:  Expired not from Cache!");
				return false;
			}
		}
		else
			// This means it lives forever!
			return false;
	}

	public Object getObject()
	{
		return object;
	}

	public Object getIdentifier()
	{
		return identifier;
	}
}