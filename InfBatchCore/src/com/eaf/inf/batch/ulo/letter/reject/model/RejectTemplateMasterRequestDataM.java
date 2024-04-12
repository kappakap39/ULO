package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;

import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;

public class RejectTemplateMasterRequestDataM   implements Serializable, Cloneable{
public RejectTemplateMasterRequestDataM() {
		super();
}
private TemplateBuilderRequest templateBuilderRequest;
private TemplateMasterDataM templateMasterDataM;
private String sendTo;
public TemplateBuilderRequest getTemplateBuilderRequest() {
	return templateBuilderRequest;
}
public void setTemplateBuilderRequest(
		TemplateBuilderRequest templateBuilderRequest) {
	this.templateBuilderRequest = templateBuilderRequest;
}
public TemplateMasterDataM getTemplateMasterDataM() {
	return templateMasterDataM;
}
public void setTemplateMasterDataM(TemplateMasterDataM templateMasterDataM) {
	this.templateMasterDataM = templateMasterDataM;
}
public String getSendTo() {
	return sendTo;
}
public void setSendTo(String sendTo) {
	this.sendTo = sendTo;
}
}
