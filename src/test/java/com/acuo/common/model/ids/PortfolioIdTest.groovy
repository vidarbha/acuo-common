package com.acuo.common.model.ids

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class PortfolioIdTest extends Specification {
    def "FromString"() {
        when:
        ObjectMapper om = new ObjectMapper()

        String testAString = om.writeValueAsString(PortfolioId.fromString("p2"))

        PortfolioId newTestA = om.readValue(testAString, PortfolioId.class)

        then:
        assert newTestA != null
    }
}
