package com.acuo.common.util;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import java.util.stream.Stream;

public interface WithResteasyFixtures {

	default Dispatcher createDispatcher(Class<?>... providers) {
		synchronized (LockHolder.LOCK) {
			ArgChecker.notNull(providers, "providers");
			ArgChecker.notEmpty(providers, "providers");
			Stream.of(providers).map(ArgChecker::notNullItem).count();
			ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
			Stream.of(providers).forEach(p -> factory.registerProvider(p));
			System.out.println("####################################### " + factory);
			return create(factory);
		}
	}

	static Dispatcher create(ResteasyProviderFactory factory) {
		Dispatcher dispatcher = new SynchronousDispatcher(factory);
		ResteasyProviderFactory.setInstance(dispatcher.getProviderFactory());
		RegisterBuiltin.register(dispatcher.getProviderFactory());
		return dispatcher;
	}
}

class LockHolder { // Package private class
	public static Object LOCK = new Object();
}