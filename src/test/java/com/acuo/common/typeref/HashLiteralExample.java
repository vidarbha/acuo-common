package com.acuo.common.typeref;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class HashLiteralExample {
    @Test
    public void java_hash_literal() {
        Map<String, String> hash = hash(
            hello -> "world",
            bob -> bob,
            bill -> "was here"
        );

        assertEquals("world", hash.get("hello"));
        assertEquals("bob", hash.get("bob"));
        assertEquals("was here", hash.get("bill"));
    }

    private static <T> Map<String, T> hash(NamedValue<T>... keyValuePairs) {
        Map<String, T> map = new HashMap<>();
        Arrays.stream(keyValuePairs)
            .forEach(kvp ->
                map.put(
                    kvp.name(),
                    kvp.value())
                );
        return map;
    }
}