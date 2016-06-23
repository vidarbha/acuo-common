package com.acuo.common.marshal;

import com.acuo.common.util.ArgChecker;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class MarshallerExecutor<F extends ContextFactory> extends MarshallerSupport {

	private final F factory;

	@Inject
	public MarshallerExecutor(final F factory) {
		this.factory = ArgChecker.notNull(factory, "factory");
	}

	@Override
	protected String doMarshal(final Object body) {
		StringWriter buff = new StringWriter();
		try {
			factory.marshaller().marshal(body, buff);
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error marshalling [" + body + "] :" + e.getMessage(), e);
		}
		return buff.toString();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected <T> T doUnmarshal(final String marshaled) {
		StringReader buff = new StringReader(marshaled);
		try {
			return (T) factory.unmarshaller().unmarshal(new StreamSource(buff));
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException(
					"Error un-marshaling [" + marshaled + "] :" + e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected <T> T doUnmarshal(final String marshaled, final Class<T> type) {
		StringReader buff = new StringReader(marshaled);
		try {
			return (T) factory.unmarshaller(type).unmarshal(new StreamSource(buff), type).getValue();
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException(
					"Error unmarshalling [" + marshaled + "] against [" + type + "] :" + e.getMessage(), e);
		}
	}
}