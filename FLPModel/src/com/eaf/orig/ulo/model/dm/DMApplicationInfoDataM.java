package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.util.ArrayList;

public class DMApplicationInfoDataM implements Serializable,Cloneable{
	public DMApplicationInfoDataM(){
		super();
	}
	private String businessClassId;
	private String applicationRecordId;
	private String applicationType;
	private String finalAppDecision;
	private int lifeCycle;
	private ArrayList<DMCardInfoDataM> cardInfos;
		
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
	public String getFinalAppDecision() {
		return finalAppDecision;
	}
	public void setFinalAppDecision(String finalAppDecision) {
		this.finalAppDecision = finalAppDecision;
	}
	public ArrayList<DMCardInfoDataM> getCardInfos() {
		return cardInfos;
	}
	public void setCardInfos(ArrayList<DMCardInfoDataM> cardInfos) {
		this.cardInfos = cardInfos;
	}
	public String getProduct(){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	
}
