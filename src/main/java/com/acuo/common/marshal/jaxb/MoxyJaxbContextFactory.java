package com.acuo.common.marshal.jaxb;

import com.acuo.common.marshal.MarshallerRuntimeException;
import com.acuo.common.marshal.MarshallerTypes;
import com.acuo.common.util.ArgChecker;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoxyJaxbContextFactory implements JaxbContextFactory {

	private final JAXBContext context;

	@Inject
	MoxyJaxbContextFactory(@MarshallerTypes List<Class<?>> types) {
		ArgChecker.notEmpty(types, "types");
		try {
			List<Class<?>> classes = addMoxyClasses(types);
			this.context = JAXBContextFactory.createContext(toArray(classes), Collections.EMPTY_MAP);
		} catch (JAXBException e) {
			throw new MarshallerRuntimeException("Error creating Moxy/JAXB Context: " + e.getMessage(), e);
		}
	}

	@Override
	public JAXBContext getContext() {
		return context;
	}

	@Override
	public JAXBContext newInstance(Class<?>... types) throws JAXBException {
		List<Class<?>> classes = addMoxyClasses(Arrays.asList(types));
		return JAXBContextFactory.createContext(toArray(classes), Collections.EMPTY_MAP);
	}

	private List<Class<?>> addMoxyClasses(List<Class<?>> types) {
		List<Class<?>> classes = new ArrayList<>(types.size() + 1);
		classes.addAll(types);
		classes.add(ObjectFactory.class);
		return classes;
	}

	private Class<?>[] toArray(List<Class<?>> classes) {
		return classes.toArray(new Class<?>[classes.size()]);
	}
}
