package com.acuo.common.marshal;

import com.opengamma.strata.basics.date.DayCount;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DayCountAdapter extends XmlAdapter<String, DayCount> {
    @Override
    public DayCount unmarshal(String value) throws Exception {
        return DayCount.of(value);
    }

    @Override
    public String marshal(DayCount value) throws Exception {
        return value.getName();
    }
}
