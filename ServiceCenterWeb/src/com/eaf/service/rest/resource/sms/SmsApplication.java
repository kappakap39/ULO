package com.eaf.service.rest.resource.sms;

import java.util.HashSet;
import java.util.Set;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Deprecated
//@ApplicationPath("/rest/sms/*")
public class SmsApplication extends Application{
	@Override
	public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
//       		classes.add(com.eaf.service.rest.resource.sms.SmsService.class);
//       		classes.add(com.eaf.service.rest.resource.sms.JsonSmsProvider.class);
//       		classes.add(com.eaf.service.rest.resource.sms.JsonSmsExceptionMapper.class);
        return classes;
	}
}
