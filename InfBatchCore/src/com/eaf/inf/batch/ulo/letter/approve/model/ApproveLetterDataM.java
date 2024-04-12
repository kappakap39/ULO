package com.eaf.inf.batch.ulo.letter.approve.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ApproveLetterDataM implements Serializable, Cloneable{
	private String letterNo;
	private String finalAppDecisionDate;
	private String name;
	private String address1;
	private String address2;
	private String applicationGroupNo;
	private String orgId;
	private String templateType;
	private BigDecimal loanAmt;
	private String loanAmtText;
	private String idNo;
	private int term;
	private BigDecimal monthlyInstallment;
	private String monthlyInstallmentText;
	private BigDecimal rateAmount;
	private String accountNo;
	private String firstInstallmentMonthYear;
	private String firstInstallmentDay;
	private String contactCenter;
	private String applicationGroupId;
	private String applicationRecordId;
	private String moduleId;
	
	private String cisNo;
	private String letterChannel;
	private BigDecimal installmentAmount;
	private String thFirstName;
	private String thMidName;
	private String thLastName;
	private String emailPrimary;
	private String payrollFlag;
	
	private String productNameThEn;
	
	public String getPayrollFlag() {
		return payrollFlag;
	}
	public void setPayrollFlag(String payrollFlag) {
		this.payrollFlag = payrollFlag;
	}
	public String getEmailPrimary() {
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
	}
	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getLetterChannel() {
		return letterChannel;
	}
	public void setLetterChannel(String letterChannel) {
		this.letterChannel = letterChannel;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getFinalAppDecisionDate() {
		return finalAppDecisionDate;
	}
	public void setFinalAppDecisionDate(String finalAppDecisionDate) {
		this.finalAppDecisionDate = finalAppDecisionDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoanAmtText() {
		return loanAmtText;
	}
	public void setLoanAmtText(String loanAmtText) {
		this.loanAmtText = loanAmtText;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public BigDecimal getMonthlyInstallment() {
		return monthlyInstallment;
	}
	public void setMonthlyInstallment(BigDecimal monthlyInstallment) {
		this.monthlyInstallment = monthlyInstallment;
	}
	public String getMonthlyInstallmentText() {
		return monthlyInstallmentText;
	}
	public void setMonthlyInstallmentText(String monthlyInstallmentText) {
		this.monthlyInstallmentText = monthlyInstallmentText;
	}
	public BigDecimal getRateAmount() {
		return rateAmount;
	}
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getFirstInstallmentMonthYear() {
		return firstInstallmentMonthYear;
	}
	public void setFirstInstallmentMonthYear(String firstInstallmentMonthYear) {
		this.firstInstallmentMonthYear = firstInstallmentMonthYear;
	}
	public String getFirstInstallmentDay() {
		return firstInstallmentDay;
	}
	public void setFirstInstallmentDay(String firstInstallmentDay) {
		this.firstInstallmentDay = firstInstallmentDay;
	}
	public String getContactCenter() {
		return contactCenter;
	}
	public void setContactCenter(String contactCenter) {
		this.contactCenter = contactCenter;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getProductNameThEn() {
		return productNameThEn;
	}
	public void setProductNameThEn(String productNameThEn) {
		this.productNameThEn = productNameThEn;
	}
	
}
