package com.acuo.common.cache.interceptors;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Class MyLittleTestObject.
 */
public class MyLittleTestObject implements Serializable {

    private static final long serialVersionUID = 6754090342991937310L;
    private AtomicInteger count = new AtomicInteger(0);
    private String meh;

    /**
     * The business method.
     * 
     * @param input
     *            the input
     * @return the string
     */
    @Cacheable
    public String theBusinessMethod(final String input) {

        // some processing
        int i = 0;
        while (i < 1000000) {
            String wasteTime = i + i + "count + count";
            i++;
        }

        return "Returning from business method with value: " + input + " " + i;

    }

    
    /**
     * The business method.
     * 
     * @param input
     *            the input
     * @return the string
     */
    @Cacheable
    public String theOtherBusinessMethod(final String input) {

        count.incrementAndGet();

        return "Returning from other business method with value: " + input;

    }

    public int counts() {
        return count.get();
    }
    
    /**
     * Generated Getter.
     * 
     * @return the meh
     */
    public String getMeh() {
        return meh;
    }

    /**
     * Generated Setter.
     * 
     * @param meh
     *            the meh to set
     */
    public void setMeh(final String meh) {
        this.meh = meh;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((meh == null) ? 0 : meh.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyLittleTestObject other = (MyLittleTestObject) obj;
        if (meh == null) {
            if (other.meh != null)
                return false;
        } else if (!meh.equals(other.meh))
            return false;
        return true;
    }

}