package com.acuo.common.util;

import java.util.Arrays;
import java.util.Objects;

public class ArithmeticUtils {

    private ArithmeticUtils() {
    }

    private static Double addition(Double... values) {
        return Arrays.stream(values)
                     .filter(Objects::nonNull)
                     .mapToDouble(Double::doubleValue)
                     .sum();
    }
}
