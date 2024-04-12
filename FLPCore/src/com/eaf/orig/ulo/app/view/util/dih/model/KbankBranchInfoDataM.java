package com.eaf.orig.ulo.app.view.util.dih.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KbankBranchInfoDataM implements Serializable,Cloneable{
	private String branchNo;
	private String branchName;
	private String branchRegion;
	private String branchZone;
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchRegion() {
		return branchRegion;
	}
	public void setBranchRegion(String branchRegion) {
		this.branchRegion = branchRegion;
	}
	public String getBranchZone() {
		return branchZone;
	}
	public void setBranchZone(String branchZone) {
		this.branchZone = branchZone;
	}	
}
