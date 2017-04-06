package com.acuo.common.util;

import java.util.Arrays;
import java.util.Objects;

public class ArithmeticUtils {

    private ArithmeticUtils() {
    }

    public static Double addition(Double... values) {
        if (values == null) return 0.0d;
        return Arrays.stream(values)
                     .filter(Objects::nonNull)
                     .reduce(0.0, Double::sum);
    }
}
