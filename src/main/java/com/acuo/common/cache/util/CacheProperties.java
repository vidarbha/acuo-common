/*
 * Copyright 2007 Ralf Joachim
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
 *
 * $Id: CacheProperties.java 8994 2011-08-01 23:40:59Z rjoachim $
 */
package com.acuo.common.cache.util;

import com.acuo.common.cache.base.Cache;

/**
 * Properties of CPA modul.
 * 
 * @version $Id: CacheProperties.java 8994 2011-08-01 23:40:59Z rjoachim $
 * @author <a href="mailto:ralf DOT joachim AT syscon DOT eu">Ralf Joachim</a>
 * @since 1.1.3
 */
public class CacheProperties extends AbstractProperties {
    //--------------------------------------------------------------------------

    /** Path to Cache properties of core modul. */
    private static final String FILEPATH = "/com/acuo/common/cache/";

    /** Name of Cache properties of core modul. */
    private static final String FILENAME = "cache.properties";
    
    /** A static properties instance to be used during migration to a none static one. */
    // TODO Remove property after support for static configuration has been terminated.
    private static AbstractProperties _instance = null;

    //--------------------------------------------------------------------------
    
    /**
     * Get the one and only static CPA properties.
     * 
     * @return One and only properties instance for Castor CPA modul.
     * @deprecated Don't limit your applications flexibility by using static properties. Use
     *             your own properties instance created with one of the newInstance() methods
     *             instead.
     */
    // TODO Remove method after support for static properties has been terminated.
    public static synchronized AbstractProperties getInstance() {
        if (_instance == null) { _instance = newInstance(); }
        return _instance;
    }
    
    /**
     * Factory method for a default CPA properties instance. Application and domain class
     * loaders will be initialized to the one used to load this class. The properties instance
     * returned will be a CastorProperties with a CacheProperties, a XMLProperties and a
     * CoreProperties instance as parents. The CastorProperties holding user specific properties
     * is the only one that can be modified by put() and remove() methods. CacheProperties,
     * XMLProperties and CoreProperties are responsble to deliver Castor's default values if they
     * have not been overwritten by the user.
     * 
     * @return Properties instance for Castor CPA modul.
     */
    public static AbstractProperties newInstance() {        
        AbstractProperties prop = new CacheProperties();        
        return prop;
    }
    
    /**
     * Factory method for a CPA properties instance that uses the specified class loaders. The
     * properties instance returned will be a CastorProperties with a CacheProperties, a
     * XMLProperties and a CoreProperties instance as parents. The CastorProperties
     * holding user specific properties is the only one that can be modified by put() and remove()
     * methods. CacheProperties, XMLProperties and CoreProperties are responsble to deliver
     * Castor's default values if they have not been overwritten by the user.
     * 
     * @param app Classloader to be used for all classes of Castor and its required libraries.
     * @param domain Classloader to be used for all domain objects.
     * @return Properties instance for Castor CPA modul.
     */
    public static AbstractProperties newInstance(final ClassLoader app, final ClassLoader domain) {        
        AbstractProperties prop = new CacheProperties(app, domain);        
        return prop;
    }
    
    //--------------------------------------------------------------------------

    /**
     * Construct properties with given parent. Application and domain class loaders will be
     * initialized to the ones of the parent. 
     * <br/>
     * Note: This constructor is not intended for public use. Use one of the newInstance() methods
     * instead.
     */
    public CacheProperties() {
        super();
        loadDefaultProperties(FILEPATH, FILENAME);
    }
    
    /**
     * Construct properties that uses the specified class loaders. No parent properties will be set.
     * <br/>
     * Note: This constructor is not intended for public use. Use one of the newInstance() methods
     * of the modul specific properties instead.
     * 
     * @param application Classloader to be used for all classes of Castor and its required libraries.
     * @param domain Classloader to be used for all domain objects.
     */
    public CacheProperties(final ClassLoader application, final ClassLoader domain) {
        super(application, domain);
        loadDefaultProperties(FILEPATH, FILENAME);
    }

    //--------------------------------------------------------------------------

    // Specify public keys of CPA configuration properties here.

    /** Property listing all available {@link Cache} implementations
     *  (<tt>com.acuo.common.cache.Factories</tt>). */
    public static final String CACHE_FACTORIES = "com.acuo.common.cache.Factories";

    //--------------------------------------------------------------------------
}
