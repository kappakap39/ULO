package com.eaf.inf.batch.ulo.notification.config.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

@SuppressWarnings("serial")
public class NotificationEODConfigDataM implements Serializable,Cloneable{
	public NotificationEODConfigDataM(){
		super();
	}
	private HashMap<String,ArrayList<String>> reasonData;
	private VCEmpInfoDataM vcEmpManager;
	public HashMap<String, ArrayList<String>> getReasonData() {
		return reasonData;
	}
	public void setReasonData(HashMap<String, ArrayList<String>> reasonData) {
		this.reasonData = reasonData;
	}
	public VCEmpInfoDataM getVcEmpManager() {
		return vcEmpManager;
	}
	public void setVcEmpManager(VCEmpInfoDataM vcEmpManager) {
		this.vcEmpManager = vcEmpManager;
	}
}
