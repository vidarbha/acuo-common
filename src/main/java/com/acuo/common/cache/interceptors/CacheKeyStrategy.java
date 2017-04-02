package com.acuo.common.cache.interceptors;

/**
 * Interface to allow you to inject a specific cache key for a particular object.
 * @author Brian Du Preez
 */
public interface CacheKeyStrategy {

    /**
     * generate an identifier for a particular to be used as the cache key..
     *
     * @return cache key
     * @throws IllegalStateException the illegal state exception
     */
    String generateKey() throws IllegalStateException;

    /**
     * Class to look for.
     * @return the class
     */
    String classForStrategy();

    /**
     * Set the object used to generate key.
     *
     * @param object the new object
     */
    void setObject(final Object object);
}