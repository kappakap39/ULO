package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationDataM implements Serializable,Cloneable{
	private String appItemNo;
	private String appStatus;
	private Date appStatusDate;
	private String projectCode;
	private String kbankProductCode;
	private String vetoFlag;
	private String rejectCode;
	private String applyType;
	private String bundleProduct;
	private String bundleProductCreditLimit;
	private String policyProgramId;
	private String mainAppItemNo;
	private VerResultDataM verResult;
	private ArrayList<LoanDataM> loans;
	private ArrayList<PersonalRefDataM> personalRefs;
	
	public String getAppItemNo() {
		return appItemNo;
	}
	public void setAppItemNo(String appItemNo) {
		this.appItemNo = appItemNo;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public Date getAppStatusDate() {
		return appStatusDate;
	}
	public void setAppStatusDate(Date appStatusDate) {
		this.appStatusDate = appStatusDate;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getKbankProductCode() {
		return kbankProductCode;
	}
	public void setKbankProductCode(String kbankProductCode) {
		this.kbankProductCode = kbankProductCode;
	}
	public String getVetoFlag() {
		return vetoFlag;
	}
	public void setVetoFlag(String vetoFlag) {
		this.vetoFlag = vetoFlag;
	}
	public String getRejectCode() {
		return rejectCode;
	}
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getBundleProduct() {
		return bundleProduct;
	}
	public void setBundleProduct(String bundleProduct) {
		this.bundleProduct = bundleProduct;
	}
	public String getBundleProductCreditLimit() {
		return bundleProductCreditLimit;
	}
	public void setBundleProductCreditLimit(String bundleProductCreditLimit) {
		this.bundleProductCreditLimit = bundleProductCreditLimit;
	}
	public String getPolicyProgramId() {
		return policyProgramId;
	}
	public void setPolicyProgramId(String policyProgramId) {
		this.policyProgramId = policyProgramId;
	}
	public String getMainAppItemNo() {
		return mainAppItemNo;
	}
	public void setMainAppItemNo(String mainAppItemNo) {
		this.mainAppItemNo = mainAppItemNo;
	}
	public VerResultDataM getVerResult() {
		return verResult;
	}
	public void setVerResult(VerResultDataM verResult) {
		this.verResult = verResult;
	}
	public ArrayList<LoanDataM> getLoans() {
		return loans;
	}
	public void setLoans(ArrayList<LoanDataM> loans) {
		this.loans = loans;
	}
	public ArrayList<PersonalRefDataM> getPersonalRefs() {
		return personalRefs;
	}
	public void setPersonalRefs(ArrayList<PersonalRefDataM> personalRefs) {
		this.personalRefs = personalRefs;
	}
}
