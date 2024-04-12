package com.eaf.service.module.model;

import java.io.Serializable;

public class WebsiteDataM implements Serializable,Cloneable{
	private String websiteCode;
	private String websiteResult;
	
	public String getWebsiteCode() {
		return websiteCode;
	}
	public void setWebsiteCode(String websiteCode) {
		this.websiteCode = websiteCode;
	}
	public String getWebsiteResult() {
		return websiteResult;
	}
	public void setWebsiteResult(String websiteResult) {
		this.websiteResult = websiteResult;
	}
	
}
