package com.acuo.common.marshal;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public LocalDate unmarshal(String value) throws Exception {
		if (value == null)
			return null;
		return LocalDate.parse(value);
	}

	@Override
	public String marshal(LocalDate value) throws Exception {
		if (value == null)
			return null;
		return value.format(formatter);
	}

}