package com.acuo.common.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
 
	public Response toResponse(Throwable ex) {

        ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		errorMessage.setCode(5000);
		errorMessage.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		errorMessage.setDeveloperMessage(errorStackTrace.toString());

		return Response.status(errorMessage.getStatus())
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();	
	}
}