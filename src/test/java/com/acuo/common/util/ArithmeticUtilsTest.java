package com.acuo.common.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArithmeticUtilsTest {

    @Test
    public void addition() throws Exception {
    }

    @Test
    public void divide() throws Exception {
        Double result = ArithmeticUtils.divide(null, null);
        assertThat(result).isEqualTo(Double.NaN);

        result = ArithmeticUtils.divide(1d, 0.0d);
        assertThat(result).isEqualTo(Double.NaN);

        result = ArithmeticUtils.divide(1d, 1d);
        assertThat(result).isEqualTo(1d);
    }

    @Test
    public void testAdditionWithNull() {
        Double result = ArithmeticUtils.addition((Double[]) null);
        assertThat(result).isEqualTo(0.0d);

        result = ArithmeticUtils.addition(null, null);
        assertThat(result).isEqualTo(0.0d);

        result = ArithmeticUtils.addition(null, 1.0d);
        assertThat(result).isEqualTo(1.0d);
    }


}