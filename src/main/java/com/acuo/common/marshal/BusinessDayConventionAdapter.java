package com.acuo.common.marshal;

import com.acuo.common.model.BusinessDayConventionProxy;
import com.opengamma.strata.basics.date.BusinessDayConvention;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BusinessDayConventionAdapter extends XmlAdapter<String, BusinessDayConventionProxy> {
    @Override
    public BusinessDayConventionProxy unmarshal(String value) throws Exception {
        return BusinessDayConventionProxy.of(value);
    }

    @Override
    public String marshal(BusinessDayConventionProxy value) throws Exception {
        return value.getName();
    }
}
