package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class VerResultDataM implements Serializable, Cloneable{
	private String verifyCusResult;
	private ArrayList<WebsiteDataM> websites;
	private ArrayList<OrCodeDataM> orCodes;
	
	public String getVerifyCusResult() {
		return verifyCusResult;
	}
	public void setVerifyCusResult(String verifyCusResult) {
		this.verifyCusResult = verifyCusResult;
	}
	public ArrayList<WebsiteDataM> getWebsites() {
		return websites;
	}
	public void setWebsites(ArrayList<WebsiteDataM> websites) {
		this.websites = websites;
	}
	public ArrayList<OrCodeDataM> getOrCodes() {
		return orCodes;
	}
	public void setOrCodes(ArrayList<OrCodeDataM> orCodes) {
		this.orCodes = orCodes;
	}
}
