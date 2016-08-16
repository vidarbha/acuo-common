package com.acuo.common.marshal;

import com.opengamma.strata.basics.schedule.Frequency;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FrequencyAdapter extends XmlAdapter<String, Frequency> {
    @Override
    public Frequency unmarshal(String value) throws Exception {
        return Frequency.parse(value);
    }

    @Override
    public String marshal(Frequency value) throws Exception {
        return value.toString().substring(1);
    }
}
