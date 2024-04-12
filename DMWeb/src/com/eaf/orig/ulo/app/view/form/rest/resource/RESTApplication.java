package com.eaf.orig.ulo.app.view.form.rest.resource;

import java.util.HashSet;
import java.util.Set;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//@ApplicationPath("/rest/dm/*")
public class RESTApplication extends Application{
	@Override
	public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
//        classes.add(com.eaf.orig.ulo.app.view.form.rest.resource.DmCacheService.class);
//        classes.add(com.eaf.orig.ulo.app.view.form.rest.resource.DmService.class);
        return classes;
	}
}
