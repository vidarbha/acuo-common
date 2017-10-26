/*
 * Copyright 2005, 2006 Gregory Block
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
package com.acuo.common.cache.hashbelt.reaper;

import com.acuo.common.cache.hashbelt.container.Container;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

/**
 * Calls a refresh method on each object in the container; it reinserts any
 * returned object to the front of the expiration system. Useful for any object
 * that needs to be periodically refreshed from source; you are free to return
 * the same object that was called or to replace it with a refreshed version of
 * that object.
 * <p>
 * Note that you must supply the implementation of the refresh method.
 * 
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * 
 * @author <a href="mailto:gblock AT ctoforaday DOT com">Gregory Block</a>
 * @version $Revision: 9040 $ $Date: 2011-08-16 08:26:59 +0200 (Di, 16 Aug 2011) $
 * @since 1.0
 */
@Slf4j
public abstract class RefreshingReaper<K, V> extends AbstractReaper<K, V> {
    
    /**
     * {@inheritDoc}
     */
    public final void handleExpiredContainer(final Container<K, V> expiredContainer) {
        Iterator<K> iter = expiredContainer.keyIterator();
        while (iter.hasNext()) {
            K key = iter.next();
            V value = expiredContainer.get(key);
            V refreshed = null;
            
            try {
                refreshed = refresh(value);
            } catch (Throwable t) {
                log.error("Caught exception while processing refresh on a cache value; "
                        + "dropping cache value", t);
                if (t instanceof VirtualMachineError) {
                    throw (VirtualMachineError) t;
                }
            }
            
            if (refreshed != null) { getCache().put(key, refreshed); }
        }
    }

    /**
     * Function called to attempt to refresh the object. If refresh was successful,
     * return the refreshed object; if not, return null.
     * 
     * @param objectToBeRefreshed The object to be refreshed.
     * @return The refreshed object, or null if the object could not be refreshed.
     */
    protected abstract V refresh(final V objectToBeRefreshed);
}
