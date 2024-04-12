package com.eaf.inf.batch.ulo.debt.model;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class GenerateDebtDataM implements Serializable,Cloneable{
	public GenerateDebtDataM(){
		super();
	}
	private String applicationGroupNo;
	private String cisNo;
	private String idNo;
	private String consentRefNo;
	private BigDecimal otherDebtAmount;
	private String sourceOfApp;
	private String applicationGroupId;
	private String applicantType;
	private String product;
	private BigDecimal verifiedIncome;
	private BigDecimal approveAmount;
	private int lifeCycle;
	
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSourceOfApp() {
		return sourceOfApp;
	}
	public void setSourceOfApp(String sourceOfApp) {
		this.sourceOfApp = sourceOfApp;
	}
	public BigDecimal getOtherDebtAmount() {
		return otherDebtAmount;
	}
	public void setOtherDebtAmount(BigDecimal otherDebtAmount) {
		this.otherDebtAmount = otherDebtAmount;
	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}
	public BigDecimal getApproveAmount() {
		return approveAmount;
	}
	public void setApproveAmount(BigDecimal approveAmount) {
		this.approveAmount = approveAmount;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	

}
