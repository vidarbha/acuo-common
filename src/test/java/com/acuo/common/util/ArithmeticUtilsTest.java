package com.acuo.common.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArithmeticUtilsTest {

    @Test
    public void testAdditionWithNull() {
        Double result = ArithmeticUtils.addition(null);
        assertThat(result).isEqualTo(0.0d);

        result = ArithmeticUtils.addition(null, null);
        assertThat(result).isEqualTo(0.0d);

        result = ArithmeticUtils.addition(null, 1.0d);
        assertThat(result).isEqualTo(1.0d);
    }

}