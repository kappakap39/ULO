package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class NotifyEODManagerInfoDataM implements Cloneable, Serializable {
	private String saleId;
	private ArrayList<VCEmpInfoDataM> manageInfos;
	public String getSaleId(){
		return saleId;
	}
	public void setSaleId(String saleId){
		this.saleId = saleId;
	}
	public ArrayList<VCEmpInfoDataM> getManageInfos(){
		return manageInfos;
	}
	public void setManageInfos(ArrayList<VCEmpInfoDataM> manageInfos){
		this.manageInfos = manageInfos;
	}	
}
