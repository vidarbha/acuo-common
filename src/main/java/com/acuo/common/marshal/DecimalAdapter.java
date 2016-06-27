package com.acuo.common.marshal;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class DecimalAdapter extends XmlAdapter<String, Double> {

	@Override
	public Double unmarshal(String value) throws Exception {
		if (value == null)
			return null;
		return new BigDecimal(value).stripTrailingZeros().doubleValue();
	}

	@Override
	public String marshal(Double value) throws Exception {
		if (value == null)
			return null;
		return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
	}

}
