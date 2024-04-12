package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
@SuppressWarnings("serial")
public class BranchSummaryResponseDataM implements Serializable, Cloneable{
	HashMap<String, ArrayList<NotificationEODDataM>> eodBranchSummaryData;
	HashMap<String, ArrayList<VCEmpInfoDataM>> managerData;
	public HashMap<String, ArrayList<NotificationEODDataM>> getEodBranchSummaryData() {
		return eodBranchSummaryData;
	}
	public void setEodBranchSummaryData(
			HashMap<String, ArrayList<NotificationEODDataM>> eodBranchSummaryData) {
		this.eodBranchSummaryData = eodBranchSummaryData;
	}
	public HashMap<String, ArrayList<VCEmpInfoDataM>> getManagerData() {
		return managerData;
	}
	public void setManagerData(
			HashMap<String, ArrayList<VCEmpInfoDataM>> managerData) {
		this.managerData = managerData;
	}
}
