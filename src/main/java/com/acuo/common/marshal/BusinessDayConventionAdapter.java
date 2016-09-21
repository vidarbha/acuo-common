package com.acuo.common.marshal;

import com.opengamma.strata.basics.date.BusinessDayConvention;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BusinessDayConventionAdapter extends XmlAdapter<String, BusinessDayConvention> {
    @Override
    public BusinessDayConvention unmarshal(String value) throws Exception {
        return BusinessDayConvention.of(value);
    }

    @Override
    public String marshal(BusinessDayConvention value) throws Exception {
        return value.getName();
    }
}
