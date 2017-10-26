package com.acuo.common.app;

import com.acuo.common.util.ArgChecker;
import com.opengamma.strata.collect.TypedString;
import org.joda.convert.FromString;

public class SecurityKey extends TypedString<SecurityKey> {

    private SecurityKey(String name) {
        super(name);
    }

    @FromString
    public static SecurityKey of(String name) {
        ArgChecker.notNull(name, "name");
        return new SecurityKey(name);
    }
}
