package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.service.common.util.ServiceUtil;

@SuppressWarnings("serial")
public class RejectLetterBuildTemplateEntity implements Serializable, Cloneable {
	private RejectTemplateMasterRequestDataM rejectTemplateMasterRequest;//initial template only
	
	private ArrayList<RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests;
	private String applicationGroupId;
	private int lifeCycle;
	private ArrayList<RejectTemplateVariableBundleEntity> apps;
	private String language;
	
	public RejectLetterBuildTemplateEntity(){}
	
	public RejectLetterBuildTemplateEntity(ArrayList<RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests){
		this.rejectTemplateMasterRequests = rejectTemplateMasterRequests;
		this.rejectTemplateMasterRequest = rejectTemplateMasterRequests.get(0);
		this.applicationGroupId = ((RejectLetterProcessDataM)rejectTemplateMasterRequest.getTemplateBuilderRequest().getRequestObject()).getApplicationGroupId();
		this.lifeCycle = ((RejectLetterProcessDataM)rejectTemplateMasterRequest.getTemplateBuilderRequest().getRequestObject()).getLifeCycle();
		this.language = ((RejectLetterProcessDataM)rejectTemplateMasterRequest.getTemplateBuilderRequest().getRequestObject()).getLanguage();
		if(!ServiceUtil.empty(this.language) && !this.language.equals(RejectLetterUtil.TH)){//default language to EN and if not TH set to EN
			this.language = RejectLetterUtil.EN;
		}
		this.apps = new ArrayList<>();
		for(RejectTemplateMasterRequestDataM item : rejectTemplateMasterRequests){
			TemplateBuilderRequest templateBuilderRequest = item.getTemplateBuilderRequest();
			RejectLetterProcessDataM rejectLetterProcessDataM = (RejectLetterProcessDataM)templateBuilderRequest.getRequestObject();
			RejectTemplateVariableBundleEntity app = new RejectTemplateVariableBundleEntity();
			
			app.setTemplateId(templateBuilderRequest.getTemplateId());
			app.setTemplateType(templateBuilderRequest.getTemplateType());
			app.setBusinessClassIds(rejectLetterProcessDataM.getBusinessClassIds());
			app.setPersonalIds(rejectLetterProcessDataM.getPerosnalIds());
			app.setProductType(rejectLetterProcessDataM.getProduct());
			apps.add(app);
		}
	}
	public RejectTemplateMasterRequestDataM getRejectTemplateMasterRequest() {
		return rejectTemplateMasterRequest;
	}
	public void setRejectTemplateMasterRequest(
			RejectTemplateMasterRequestDataM rejectTemplateMasterRequest) {
		this.rejectTemplateMasterRequest = rejectTemplateMasterRequest;
	}
	public ArrayList<RejectTemplateMasterRequestDataM> getRejectTemplateMasterRequests() {
		return rejectTemplateMasterRequests;
	}
	public void setRejectTemplateMasterRequests(
			ArrayList<RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests) {
		this.rejectTemplateMasterRequests = rejectTemplateMasterRequests;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public ArrayList<RejectTemplateVariableBundleEntity> getApps() {
		return apps;
	}
	public void setApps(ArrayList<RejectTemplateVariableBundleEntity> apps) {
		this.apps = apps;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
