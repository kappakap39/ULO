package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;

import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
@SuppressWarnings("serial")
public class TemplateConditionRequestDataM implements Serializable, Cloneable{
	private TemplateMasterDataM templateMasterDataM;
	private String emailPrimary;
	public TemplateMasterDataM getTemplateMasterDataM() {
		return templateMasterDataM;
	}
	public void setTemplateMasterDataM(TemplateMasterDataM templateMasterDataM) {
		this.templateMasterDataM = templateMasterDataM;
	}
	public String getEmailPrimary() {
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
	}
	
}
