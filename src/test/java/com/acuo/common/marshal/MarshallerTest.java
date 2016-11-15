package com.acuo.common.marshal;

import com.acuo.common.marshal.jaxb.JaxbContextFactory;
import com.acuo.common.marshal.jaxb.MoxyJaxbContextFactory;
import com.acuo.common.marshal.json.JsonContextFactory;
import com.acuo.common.marshal.json.MoxyJsonContextFactory;
import com.acuo.common.util.ResourceFile;
import com.google.inject.*;
import com.google.inject.name.Names;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class MarshallerTest {

	@Rule
	public ResourceFile xml = new ResourceFile("/some.xml");

	@Rule
	public ResourceFile json = new ResourceFile("/some.json");

	@Inject
	@Named("xml")
	Marshaller xmlMarshaller;

	@Inject
	@Named("json")
	Marshaller jsonMarshaller;

	@Before
	public void init() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(JaxbContextFactory.class).to(MoxyJaxbContextFactory.class);
				bind(JsonContextFactory.class).to(MoxyJsonContextFactory.class);
				bind(Marshaller.class).annotatedWith(Names.named("xml")).to(new TypeLiteral<MarshallerExecutor<JaxbContextFactory>>(){});
				bind(Marshaller.class).annotatedWith(Names.named("json")).to(new TypeLiteral<MarshallerExecutor<JsonContextFactory>>(){});
			}

			@Provides
			@MarshallerTypes
			List<Class<?>> types() {
				return Arrays.asList(new Class<?>[] { SomeXml.class });
			}
		});
		injector.injectMembers(this);
	}

	@Test
	public void testDoMarshal() throws Exception {
		String xml = xmlMarshaller.marshal(someXml());
		assertNotNull(xml);
		assertNotEquals(0, xml.length());
	}

	@Test
	public void testDoUnmarshal() throws Exception {
		SomeXml someXml = xmlMarshaller.unmarshal(xml.getContent(), SomeXml.class);
		assertNotNull(someXml);
	}

	@Test
	public void testDoJsonUnmarshal() throws Exception {
		SomeXml someXml = jsonMarshaller.unmarshal(json.getContent(), SomeXml.class);
		assertNotNull(someXml);
	}

	@Test
	public void testXmlFilesAreEqual() throws Exception {
		String result = xmlMarshaller.marshal(someXml());
		assertEquals(xml.getContent(), result);
	}

	@Test
	public void testObjectsAreEqual() throws Exception {
		SomeXml result = xmlMarshaller.unmarshal(xml.getContent(), SomeXml.class);
		assertEquals(someXml(), result);
	}

	private SomeXml someXml() throws Exception {
		SomeXml someXml = new SomeXml();
		someXml.header.name = "SomeXml";
		someXml.header.date = new DateAdapter().unmarshal("2016-06-14");

		asList(1l, 2l, 3l, 4l, 5l).stream().map(MarshallerTest::addValue).forEach(v -> someXml.values.add(v));

		return someXml;
	}

	private static SomeXml.Value addValue(Long l) {
		SomeXml.Value value = new SomeXml.Value();
		value.id = l;
		return value;
	}

}
