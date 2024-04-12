package com.eaf.inf.batch.ulo.capport;


public class NotifyCapportRequest {
	
	private String capportName;
	private String capAmount;
	private String grantedAmount;
	private String capApplication;
	private String grantedApplication;
	private String warningPoint;
	public String getCapAmount() {
		return capAmount;
	}
	public void setCapAmount(String capAmount) {
		this.capAmount = capAmount;
	}
	public String getCapportName() {
		return capportName;
	}
	public void setCapportName(String capportName) {
		this.capportName = capportName;
	}
	public String getGrantedApplication() {
		return grantedApplication;
	}
	public void setGrantedApplication(String grantedApplication) {
		this.grantedApplication = grantedApplication;
	}
	public String getGrantedAmount() {
		return grantedAmount;
	}
	public void setGrantedAmount(String grantedAmount) {
		this.grantedAmount = grantedAmount;
	}
	public String getCapApplication() {
		return capApplication;
	}
	public void setCapApplication(String capApplication) {
		this.capApplication = capApplication;
	}
	public String getWarningPoint() {
		return warningPoint;
	}
	public void setWarningPoint(String warningPoint) {
		this.warningPoint = warningPoint;
	}
}
