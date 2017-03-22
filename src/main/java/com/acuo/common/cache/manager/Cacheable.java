package com.acuo.common.cache.manager;

/**
 * Title: Caching Description: This interface defines the methods, which must be implemented by all
 * objects wishing to be placed in the cache.
 * 
 * @author Jonathan Lurie
 * @version 1.0
 */
public interface Cacheable
{
	/*
	 * By requiring all objects to determine their own expirations, the algorithm is abstracted from
	 * the caching service, thereby providing maximum flexibility since each object can adopt a
	 * different expiration strategy.
	 */
	public boolean isExpired();

	/*
	 * This method will ensure that the caching service is not responsible for uniquely identifying
	 * objects placed in the cache.
	 */
	public Object getIdentifier();

	public Object getObject();
}