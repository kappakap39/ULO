package com.eaf.ulo.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.fico.Application;

@XmlRootElement(name="ServiceApplicationGroupM")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceApplicationGroupM implements Serializable,Cloneable{
	private ApplicationGroupDataM applicationGroup;
	private Application application;
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	public Application getServiceApplication() {
		return application;
	}
	public void setServiceApplication(Application application) {
		this.application = application;
	}


}
