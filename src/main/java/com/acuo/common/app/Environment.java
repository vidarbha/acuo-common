package com.acuo.common.app;

import com.acuo.common.util.ArgChecker;
import com.opengamma.strata.collect.TypedString;
import org.joda.convert.FromString;

import java.util.HashMap;
import java.util.Map;

public class Environment extends TypedString<Environment> {

	private static final long serialVersionUID = 1L;

	private final static Map<String, Environment> lookup = new HashMap<>();

	public static final Environment TEST = Environment.of("test");
	public static final Environment INTEGRATION = Environment.of("int");
	public static final Environment LOCAL = Environment.of("local");
	public static final Environment DEVELOPMENT = Environment.of("dev");
	public static final Environment KUBE = Environment.of("kube");
	public static final Environment PRODUCTION = Environment.of("prod");

	private Environment(String name) {
		super(name);
	}

	private static Environment of(String name) {
		ArgChecker.notNull(name, "name");
		return getOrCreate(name);
	}

	private static Environment getOrCreate(String name) {
		Environment environment = lookup.get(name);
		synchronized (lookup) {
			if (environment == null) {
				environment = new Environment(name);
				lookup.put(name, environment);
			}
		}
		return environment;
	}

	@FromString
	public static Environment lookup(String name) {
		ArgChecker.notNull(name, "name");
		return getOrCreate(name);
	}
}
