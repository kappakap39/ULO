package com.eaf.service.module.model;

import java.util.ArrayList;

public class CIS1034A01ResponseDataM {
	private String customerId;
	private ArrayList<CISDocInfoDataM> cisDocInfos;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public ArrayList<CISDocInfoDataM> getCisDocInfo() {
		return cisDocInfos;
	}
	public void setCisDocInfo(ArrayList<CISDocInfoDataM> cisDocInfos) {
		this.cisDocInfos = cisDocInfos;
	}
	
}
