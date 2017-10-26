package com.acuo.common.marshal;

import com.acuo.common.util.ArgChecker;
import com.google.inject.TypeLiteral;
import lombok.extern.slf4j.Slf4j;

/**
 * Support for {@link Marshaller} implementations.
 *
 * @since 1.0
 */
@Slf4j
public abstract class MarshallerSupport implements Marshaller {


	@Override
	public String marshal(final Object body) throws Exception {
		ArgChecker.notNull(body, "body");

		log.trace("Marshalling: {}", body);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(body.getClass().getClassLoader());
		try {
			String value = doMarshal(body);
			log.trace("Value: {}", value);

			return value;
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
		}
	}

	protected abstract String doMarshal(Object body) throws Exception;

	@Override
	public <T> T unmarshal(final String marshaled) throws Exception {
		ArgChecker.notNull(marshaled, "marshaled");

		log.trace("Unmarshalling {}", marshaled);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		try {
			T value = doUnmarshal(marshaled);
			log.trace("Value: {}", value);

			return value;
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
		}
	}

	@Override
	public <T> T unmarshal(final String marshaled, final Class<T> type) throws Exception {
		ArgChecker.notNull(marshaled, "marshaled");
		ArgChecker.notNull(type, "type");

		log.trace("Unmarshalling {}: {}", type, marshaled);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(type.getClassLoader());
		try {
			T value = doUnmarshal(marshaled, type);
			log.trace("Value: {}", value);

			return value;
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
		}
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> T unmarshal(final String marshaled, final TypeLiteral<T> type) throws Exception {
		ArgChecker.notNull(type, "type");
		return (T) unmarshal(marshaled, type.getRawType());
	}

	protected abstract <T> T doUnmarshal(String marshaled) throws Exception;
	protected abstract <T> T doUnmarshal(String marshaled, Class<T> type) throws Exception;
}