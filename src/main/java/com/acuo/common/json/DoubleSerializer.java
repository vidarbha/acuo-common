package com.acuo.common.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DoubleSerializer extends JsonSerializer<Double> {

    /**
     * @deprecated use or update the static child classes
     **/
    @Override
    @Deprecated
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(ValueFormatter.format(value));
    }

    public static class Two extends JsonSerializer<Double> {
        @Override
        public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(ValueFormatter.format(value,"%.2f"));
        }
    }

    public static class Four extends JsonSerializer<Double> {
        @Override
        public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(ValueFormatter.format(value,"%.4f"));
        }
    }

    public static class NoFormat extends JsonSerializer<Double> {
        @Override
        public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            value = (value == null) ? 0.0d : value;
            jgen.writeString(Double.toString(value));
        }
    }
}
