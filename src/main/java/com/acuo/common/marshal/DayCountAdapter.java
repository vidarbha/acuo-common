package com.acuo.common.marshal;

import com.acuo.common.model.DayCountProxy;
import com.opengamma.strata.basics.date.DayCount;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DayCountAdapter extends XmlAdapter<String, DayCountProxy> {
    @Override
    public DayCountProxy unmarshal(String value) throws Exception {
        return DayCountProxy.of(value);
    }

    @Override
    public String marshal(DayCountProxy value) throws Exception {
        return value.getName();
    }
}
