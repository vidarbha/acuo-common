package com.acuo.common.rest;

import lombok.Data;

@Data
public class ErrorMessage {

    /** contains the same HTTP Status code returned by the server */
	int status;
	
	/** application specific error code */
	int code;
	
	/** message describing the error*/
	String message;
		
	/** extra information that might useful for developers */
	String developerMessage;
}