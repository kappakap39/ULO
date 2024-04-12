package com.eaf.orig.ulo.app.view.form.rest.ia.resource;

//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;

//import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;

//@Provider
public class JsonImageExceptionMapper implements ExceptionMapper<JsonProcessingException>{
//	private static transient Logger logger = Logger.getLogger(JsonImageExceptionMapper.class);
	@Override
	public Response toResponse(JsonProcessingException e) {
//        String message = e.getMessage();
//        logger.info("message >> "+message);
//        return Response.status(Status.BAD_REQUEST)
//        	.type(MediaType.TEXT_PLAIN)
//            .entity("The supplied JSON was not well formed: "+message)
//            .build();
		return null;
	}

}
