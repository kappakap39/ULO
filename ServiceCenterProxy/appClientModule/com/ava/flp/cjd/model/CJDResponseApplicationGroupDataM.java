package com.ava.flp.cjd.model;

import java.io.Serializable;
import java.util.ArrayList;


public class CJDResponseApplicationGroupDataM implements Serializable, Cloneable{
	public CJDResponseApplicationGroupDataM(){
		super();
	}
	private String applicationGroupNo;
	ArrayList<CJDReponsePersonalInfoDataM> personalInfos;
	
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public ArrayList<CJDReponsePersonalInfoDataM> getPersonalInfos() {
		return personalInfos;
	}
	public void setPersonalInfos(ArrayList<CJDReponsePersonalInfoDataM> personalInfos) {
		this.personalInfos = personalInfos;
	}
	
	
}
