package com.eaf.service.rest.resource.sms;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
//import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
//import java.util.List;

//import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
//import javax.ws.rs.ext.Provider;

//import org.apache.log4j.Logger;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig;

//import com.eaf.service.rest.model.SMSRequest;
//import com.eaf.service.rest.model.SMSResponse;
@Deprecated
//@Provider
//@Produces(MediaType.APPLICATION_JSON)
public class JsonSmsProvider implements MessageBodyWriter<Object>{
//	private static transient Logger logger = Logger.getLogger(JsonSmsProvider.class);
	@Override
	public long getSize(Object object, Class<?> type, Type genericType,Annotation[] annotations, MediaType mediaType){
		return -1;
	}
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,MediaType mediaType) {	
        boolean isWritable = false;
//        if (List.class.isAssignableFrom(type) && genericType instanceof ParameterizedType ) {
//            ParameterizedType parameterizedType = (ParameterizedType) genericType;
//            Type[] actualTypeArgs = ( parameterizedType.getActualTypeArguments() );
//            isWritable = (actualTypeArgs.length == 1 && actualTypeArgs[0].equals(SMSRequest.class));
//        } else if (type == SMSRequest.class) {
//            isWritable = true;
//        } else if (type == SMSResponse.class) {
//            isWritable = true;
//        }
	   	return isWritable;
	}
	@Override
	public void writeTo(Object object, Class<?> type, Type genericType,Annotation[] annotations, MediaType mediaType,MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false );
//        mapper.writeValue(entityStream,object);
	}	
}
