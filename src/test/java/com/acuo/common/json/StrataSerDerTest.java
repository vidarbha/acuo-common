package com.acuo.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.opengamma.strata.collect.result.Result;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class StrataSerDerTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.registerModule(StrataSerDer.simpleModule());
    }

    @Test
    public void testResultSerialiser() throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, Result.success(0.0d));
        String s = writer.getBuffer().toString();
        assertThat(s).isNotBlank();
    }

}