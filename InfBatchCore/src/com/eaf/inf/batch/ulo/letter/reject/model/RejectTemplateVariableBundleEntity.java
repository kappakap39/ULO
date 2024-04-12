package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RejectTemplateVariableBundleEntity implements Serializable, Cloneable {
	private String templateId;
	private String templateType;
	private String productType;
	private ArrayList<String> businessClassIds;
	private ArrayList<String> personalIds;
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public ArrayList<String> getBusinessClassIds() {
		return businessClassIds;
	}
	public void setBusinessClassIds(ArrayList<String> businessClassIds) {
		this.businessClassIds = businessClassIds;
	}
	public ArrayList<String> getPersonalIds() {
		return personalIds;
	}
	public void setPersonalIds(ArrayList<String> personalIds) {
		this.personalIds = personalIds;
	}
}
