package com.acuo.common.marshal.json;

import com.acuo.common.marshal.MarshallerRuntimeException;
import com.acuo.common.marshal.MarshallerTypes;
import com.acuo.common.marshal.MarshallingEventHandler;
import com.acuo.common.util.ArgChecker;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

public class MoxyJsonContextFactory implements JsonContextFactory {

    private final JAXBContext context;

    @Inject
    public MoxyJsonContextFactory(@MarshallerTypes List<Class<?>> types) {
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

    public Marshaller marshaller() {
        try {
            Marshaller marshaller = getContext().createMarshaller();
            marshaller.setEventHandler(new MarshallingEventHandler());
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(MarshallerProperties.JSON_VALUE_WRAPPER, "$");
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
            return marshaller;
        } catch (JAXBException e) {
            throw new MarshallerRuntimeException("Error creating JAXB Marshaller: " + e.getMessage(), e);
        }
    }

    public Marshaller marshaller(Class<?>... types) {
        try {
            Marshaller marshaller = newInstance(types).createMarshaller();
            marshaller.setEventHandler(new MarshallingEventHandler());
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(MarshallerProperties.JSON_VALUE_WRAPPER, "$");
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
            return marshaller;
        } catch (JAXBException e) {
            throw new MarshallerRuntimeException("Error creating JAXB Marshaller: " + e.getMessage(), e);
        }
    }

    public Unmarshaller unmarshaller() {
        try {
            Unmarshaller unmarshaller = getContext().createUnmarshaller();
            unmarshaller.setEventHandler(new MarshallingEventHandler());
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            unmarshaller.setProperty(UnmarshallerProperties.JSON_VALUE_WRAPPER, "$");
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
            return unmarshaller;
        } catch (JAXBException e) {
            throw new MarshallerRuntimeException("Error creating JAXB Unmarshaller: " + e.getMessage(), e);
        }
    }

    public Unmarshaller unmarshaller(Class<?>... types) {
        try {
            Unmarshaller unmarshaller = newInstance(types).createUnmarshaller();
            unmarshaller.setEventHandler(new MarshallingEventHandler());
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
            unmarshaller.setProperty(UnmarshallerProperties.JSON_VALUE_WRAPPER, "$");
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
            return unmarshaller;
        } catch (JAXBException e) {
            throw new MarshallerRuntimeException("Error creating JAXB Unmarshaller: " + e.getMessage(), e);
        }
    }
}