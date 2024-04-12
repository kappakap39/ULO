package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

@SuppressWarnings("serial")
public class NotifyFLPFinalApproveServiceProxyRequest implements Cloneable,
		Serializable {

	private ApplicationGroupDataM applicationGroup;
	private ArrayList<NotifyTemplateDataM> notifyTemplates;
	
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}
	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	public ArrayList<NotifyTemplateDataM> getNotifyTemplates() {
		return notifyTemplates;
	}
	public void setNotifyTemplates(ArrayList<NotifyTemplateDataM> notifyTemplates) {
		this.notifyTemplates = notifyTemplates;
	}
}
