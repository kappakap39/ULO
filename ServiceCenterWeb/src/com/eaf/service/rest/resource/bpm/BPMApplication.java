package com.eaf.service.rest.resource.bpm;

import java.util.HashSet;
import java.util.Set;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//@ApplicationPath("/rest/bpm/*")
public class BPMApplication extends Application{
	@Override
	public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
//       		classes.add(com.eaf.service.rest.resource.bpm.BPMService.class);
        return classes;
	}
}
