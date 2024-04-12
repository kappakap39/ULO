package com.eaf.service.rest.resource.sms;

//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;

//import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
@Deprecated
//@Provider
public class JsonSmsExceptionMapper implements ExceptionMapper<JsonProcessingException>{
//	private static transient Logger logger = Logger.getLogger(JsonSmsExceptionMapper.class);
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
