/**
 * Copyright (C) 2011 Fabio Strozzi (fabio.strozzi@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acuo.common.util;

import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.mycila.guice.ext.closeable.CloseableInjector;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * A JUnit class runner for Guice based applications.
 *
 */
public class GuiceJUnitRunner extends BlockJUnit4ClassRunner {

	private Injector injector;
	private InstanceTestClassListener setupListener;

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	public @interface GuiceModules {
		Class<?>[] value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.runners.BlockJUnit4ClassRunner#createTest()
	 */
	@Override
	public Object createTest() throws Exception {
		Object obj = super.createTest();
		injector.injectMembers(obj);
		if (obj instanceof InstanceTestClassListener && setupListener == null) {
			setupListener = (InstanceTestClassListener) obj;
			setupListener.beforeClassSetup();
		}
		return obj;
	}

	@Override
	public void run(RunNotifier notifier) {
		super.run(notifier);
		if (setupListener != null)
			setupListener.afterClassSetup();
		tearDownInjector();
	}

	/**
	 * Instances a new JUnit runner.
	 * 
	 * @param klass
	 *            The test class
	 * @throws InitializationError
	 */
	public GuiceJUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		final List<Module> modules = getModulesFor(klass);
		injector = Guice.createInjector(modules);
	}

	protected void tearDownInjector() {
		if (injector != null) {
			// http://code.mycila.com/guice/#3-jsr-250
			try {
				injector.getInstance(CloseableInjector.class).close();
			} catch (ConfigurationException e) {
				throw new IllegalStateException("You forgot to either add GuiceRule(..., AnnotationsModule.class), "
						+ "or in your Module use an install(new AnnotationsModule()) with "
						+ AnnotationsModule.class.getName(), e);
			}
		}
	}

	/**
	 * Gets the Guice modules for the given test class.
	 * 
	 * @param klass
	 *            The test class
	 * @return The array of Guice {@link Module} modules used to initialize the
	 *         injector for the given test.
	 * @throws InitializationError
	 */
	private List<Module> getModulesFor(Class<?> klass) throws InitializationError {
		GuiceModules annotation = klass.getAnnotation(GuiceModules.class);
		if (annotation == null)
			throw new InitializationError("Missing @GuiceModules annotation for unit test '" + klass.getName() + "'");
		ArrayList<Class<?>> classes = new ArrayList<>(Arrays.asList(annotation.value()));
		classes.add(AnnotationsModule.class);
		return classes.stream().map(aClass -> {
			try {
				return (Module) (aClass).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				return null;
			}
		})
		.filter(Objects::nonNull)
		.collect(toList());
	}

}