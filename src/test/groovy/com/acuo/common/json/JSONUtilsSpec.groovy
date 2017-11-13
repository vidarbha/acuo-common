package com.acuo.common.json

import spock.lang.Specification


class JSONUtilsSpec extends Specification {

    void "Invalid json return false"() {
        given:
        String invalid = "{test}"

        expect:
        JSONUtils.isValid(invalid) == false
    }

    void "Valid json return true"() {
        given:
        String valid = "{\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Smith\",\n" +
                "    \"age\": 25,\n" +
                "    \"address\": {\n" +
                "        \"streetAddress\": \"21 2nd Street\",\n" +
                "        \"city\": \"New York\",\n" +
                "        \"state\": \"NY\",\n" +
                "        \"postalCode\": 10021\n" +
                "    },\n" +
                "    \"phoneNumbers\": [\n" +
                "        {\n" +
                "            \"type\": \"home\",\n" +
                "            \"number\": \"212 555-1234\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"fax\",\n" +
                "            \"number\": \"646 555-4567\" \n" +
                "        }\n" +
                "    ] \n" +
                "}"

        expect:
        JSONUtils.isValid(valid) == true
    }
}
