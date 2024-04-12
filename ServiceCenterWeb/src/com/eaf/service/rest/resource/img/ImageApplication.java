package com.eaf.service.rest.resource.img;

import java.util.HashSet;
import java.util.Set;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//import org.apache.log4j.Logger;

//@ApplicationPath("/rest/image/*")
public class ImageApplication extends Application{
//	private static transient Logger logger = Logger.getLogger(ImageApplication.class);
	@Override
	public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
//       		classes.add(com.eaf.service.rest.resource.img.ImageService.class);
        return classes;
	}
}
