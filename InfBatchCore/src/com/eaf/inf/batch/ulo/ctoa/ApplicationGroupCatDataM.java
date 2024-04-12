package com.eaf.inf.batch.ulo.ctoa;

import java.sql.Date;

public class ApplicationGroupCatDataM {
	String applicationGroupId;
	String applicationGroupNo;
	Date applicationDate;
	String applicationTemplate;
	String applicationStatus;
	int lifecycle;
	Date arcDate;
	String arcStatus;
	Date purgeDateOrigApp;
	Date purgeDateBPMDBS;
	Date purgeDateResDB;
	Date purgeDateOLData;
	Date purgeDateIMApp;
	Date purgeDateImageFile;
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicationTemplate() {
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public int getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(int lifecycle) {
		this.lifecycle = lifecycle;
	}
	public Date getArcDate() {
		return arcDate;
	}
	public void setArcDate(Date arcDate) {
		this.arcDate = arcDate;
	}
	public String getArcStatus() {
		return arcStatus;
	}
	public void setArcStatus(String arcStatus) {
		this.arcStatus = arcStatus;
	}
	public Date getPurgeDateOrigApp() {
		return purgeDateOrigApp;
	}
	public void setPurgeDateOrigApp(Date purgeDateOrigApp) {
		this.purgeDateOrigApp = purgeDateOrigApp;
	}
	public Date getPurgeDateBPMDBS() {
		return purgeDateBPMDBS;
	}
	public void setPurgeDateBPMDBS(Date purgeDateBPMDBS) {
		this.purgeDateBPMDBS = purgeDateBPMDBS;
	}
	public Date getPurgeDateResDB() {
		return purgeDateResDB;
	}
	public void setPurgeDateResDB(Date purgeDateResDB) {
		this.purgeDateResDB = purgeDateResDB;
	}
	public Date getPurgeDateOLData() {
		return purgeDateOLData;
	}
	public void setPurgeDateOLData(Date purgeDateOLData) {
		this.purgeDateOLData = purgeDateOLData;
	}
	public Date getPurgeDateIMApp() {
		return purgeDateIMApp;
	}
	public void setPurgeDateIMApp(Date purgeDateIMApp) {
		this.purgeDateIMApp = purgeDateIMApp;
	}
	public Date getPurgeDateImageFile() {
		return purgeDateImageFile;
	}
	public void setPurgeDateImageFile(Date purgeDateImageFile) {
		this.purgeDateImageFile = purgeDateImageFile;
	}
}
