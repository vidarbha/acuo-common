package com.acuo.common.marshal.jaxb;

import com.acuo.common.marshal.MarshallerRuntimeException;
import com.acuo.common.marshal.MarshallerTypes;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Default {@link JaxbContextFactory} implementation.
 */
public class JdkJaxbContextFactory implements JaxbContextFactory {

	private final JAXBContext context;

	@Inject
	JdkJaxbContextFactory(@MarshallerTypes List<Class<?>> types) {
		try {
			this.context = JAXBContext.newInstance(types.toArray(new Class<?>[types.size()]));
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error creating JAXB Context: " + e.getMessage(), e);
		}
	}

	@Override
	public JAXBContext getContext() {
		return context;
	}

	@Override
	public JAXBContext newInstance(Class<?>... types) throws JAXBException {
		return context.newInstance(types);
	}

}