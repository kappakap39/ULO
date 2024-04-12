package com.ava.flp.cjd.model;

import java.io.Serializable;

import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

@SuppressWarnings("serial")
public class CJDResponseServiceProxyRequest implements Cloneable,
		Serializable {

	private ApplicationGroupDataM applicationGroup;
	private String completeFlag;
	
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	public String getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	
}
