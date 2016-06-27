package com.acuo.common.marshal.jaxb;

import com.acuo.common.marshal.ContextFactory;
import com.acuo.common.marshal.MarshallerRuntimeException;
import com.acuo.common.marshal.MarshallingEventHandler;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

/**
 * Provides access to JAXB {@link Marshaller} and {@link Unmarshaller}
 * instances.
 */
public interface JaxbContextFactory extends ContextFactory {

	default Marshaller marshaller() {
		try {
			Marshaller marshaller = getContext().createMarshaller();
			marshaller.setEventHandler(new MarshallingEventHandler());
			marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
			return marshaller;
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error creating JAXB Marshaller: " + e.getMessage(), e);
		}
	}

	default Marshaller marshaller(Class<?>... types) {
		try {
			Marshaller marshaller = newInstance(types).createMarshaller();
			marshaller.setEventHandler(new MarshallingEventHandler());
			marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
			return marshaller;
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error creating JAXB Marshaller: " + e.getMessage(), e);
		}
	}

	default Unmarshaller unmarshaller() {
		try {
			Unmarshaller unmarshaller = getContext().createUnmarshaller();
			unmarshaller.setEventHandler(new MarshallingEventHandler());
			return unmarshaller;
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error creating JAXB Unmarshaller: " + e.getMessage(), e);
		}
	}

	default Unmarshaller unmarshaller(Class<?>... types) {
		try {
			Unmarshaller unmarshaller = newInstance(types).createUnmarshaller();
			unmarshaller.setEventHandler(new MarshallingEventHandler());
			return unmarshaller;
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error creating JAXB Unmarshaller: " + e.getMessage(), e);
		}
	}
}