package com.acuo.common.marshal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public interface ContextFactory {

    JAXBContext getContext();

    JAXBContext newInstance(Class<?>... types) throws JAXBException;

    javax.xml.bind.Marshaller marshaller();

    javax.xml.bind.Marshaller marshaller(Class<?>... types);

    Unmarshaller unmarshaller();

    Unmarshaller unmarshaller(Class<?>... types);
}
