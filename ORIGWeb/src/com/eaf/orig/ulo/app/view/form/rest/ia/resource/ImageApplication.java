package com.eaf.orig.ulo.app.view.form.rest.ia.resource;

import java.util.HashSet;
import java.util.Set;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//@ApplicationPath("/rest/ia/*")
public class ImageApplication extends Application{
	@Override
	public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
//        	classes.add(com.eaf.orig.ulo.app.view.form.rest.ia.resource.ImageDocumentService.class); 
//        	classes.add(com.eaf.orig.ulo.app.view.form.rest.ia.resource.JsonImageProvider.class);       
//        	classes.add(com.eaf.orig.ulo.app.view.form.rest.ia.resource.JsonImageExceptionMapper.class);       
        return classes;
	}
}
