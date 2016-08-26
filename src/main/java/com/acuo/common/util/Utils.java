package com.acuo.common.util;

import java.util.Comparator;

public class Utils {

    public static Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareTo);
}
