package com.acuo.common.marshal;

import com.opengamma.strata.basics.currency.Currency;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CurrencyAdapter extends XmlAdapter<String, Currency> {

    @Override
    public Currency unmarshal(String value) throws Exception {
        return Currency.parse(value);
    }

    @Override
    public String marshal(Currency value) throws Exception {
        return value.getCode();
    }
}