package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PersonalInfoDataM implements Serializable,Cloneable{
	private String personalId;
	private String docNo;
	private String docType;
	private String thFirstname;
	private String thMidname;
	private String thLastname;
	private String cisNo;
	private String dob;
	private String applicantType;
	private BigDecimal finalVerifiedIncome;
	private String mailAddress;
	private String typeOfFin;
	private String mobileNo;
	private String vatCode;
	private ArrayList<AddressDataM> addresses;
	private VerResultDataM verResult;
	private CardlinkCustDataM cardlinkCust;
	private ArrayList<IncomeInfoDataM> incomeInfos;
	private ArrayList<IncomeSourceDataM> incomeSources;
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getThFirstname() {
		return thFirstname;
	}
	public void setThFirstname(String thFirstname) {
		this.thFirstname = thFirstname;
	}
	public String getThMidname() {
		return thMidname;
	}
	public void setThMidname(String thMidname) {
		this.thMidname = thMidname;
	}
	public String getThLastname() {
		return thLastname;
	}
	public void setThLastname(String thLastname) {
		this.thLastname = thLastname;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public BigDecimal getFinalVerifiedIncome() {
		return finalVerifiedIncome;
	}
	public void setFinalVerifiedIncome(BigDecimal finalVerifiedIncome) {
		this.finalVerifiedIncome = finalVerifiedIncome;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getTypeOfFin() {
		return typeOfFin;
	}
	public void setTypeOfFin(String typeOfFin) {
		this.typeOfFin = typeOfFin;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	public ArrayList<AddressDataM> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<AddressDataM> addresses) {
		this.addresses = addresses;
	}
	public VerResultDataM getVerResult() {
		return verResult;
	}
	public void setVerResult(VerResultDataM verResult) {
		this.verResult = verResult;
	}
	public CardlinkCustDataM getCardlinkCust() {
		return cardlinkCust;
	}
	public void setCardlinkCust(CardlinkCustDataM cardlinkCust) {
		this.cardlinkCust = cardlinkCust;
	}
	public ArrayList<IncomeInfoDataM> getIncomeInfos() {
		return incomeInfos;
	}
	public void setIncomeInfos(ArrayList<IncomeInfoDataM> incomeInfos) {
		this.incomeInfos = incomeInfos;
	}
	public ArrayList<IncomeSourceDataM> getIncomeSources() {
		return incomeSources;
	}
	public void setIncomeSources(ArrayList<IncomeSourceDataM> incomeSources) {
		this.incomeSources = incomeSources;
	}
}
