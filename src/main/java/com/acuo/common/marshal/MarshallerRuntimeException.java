package com.acuo.common.marshal;

/**
 * A RuntimeException version of a JAXBException
 */
public class MarshallerRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MarshallerRuntimeException(String msg, Throwable t) {
		super(msg, t);
	}

	public MarshallerRuntimeException(Throwable t) {
		super(t);
	}

	public MarshallerRuntimeException(String msg) {
		super(msg);
	}
}