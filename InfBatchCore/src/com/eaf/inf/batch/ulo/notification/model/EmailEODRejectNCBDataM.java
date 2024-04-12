package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EmailEODRejectNCBDataM implements Serializable, Cloneable{
	private String applicationGroupId;
	private String applicationNo;
	private String customerName;
	private String productName;
	private String productNameEn;
	private String productNameTh;
	private String productNameThEn;
	private String titleName;
	private String personalNameTh;
	private String personalNameEn;
	private String emailPrimary;
	private String referenceNo;
	private String finalAppDecision;
	private String finalDecisionDate;
	private String policyProgramId;
	private String businessClassId;
	private String personalType;
	private String nationality;
	private String personalId;
	private String idNo;
	private String cardType;
	private String cardLevel;
	private String applicationTemplate;
	
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationNo(){
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo){
		this.applicationNo = applicationNo;
	}
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	public String getProductNameEn(){
		return productNameEn;
	}
	public void setProductNameEn(String productNameEn){
		this.productNameEn = productNameEn;
	}
	public String getProductNameTh(){
		return productNameTh;
	}
	public void setProductNameTh(String productNameTh){
		this.productNameTh = productNameTh;
	}
	public String getProductNameThEn() {
		return productNameThEn;
	}
	public void setProductNameThEn(String productNameThEn) {
		this.productNameThEn = productNameThEn;
	}
	public String getTitleName(){
		return titleName;
	}
	public void setTitleName(String titleName){
		this.titleName = titleName;
	}
	public String getPersonalNameTh(){
		return personalNameTh;
	}
	public void setPersonalNameTh(String personalNameTh){
		this.personalNameTh = personalNameTh;
	}
	public String getPersonalNameEn(){
		return personalNameEn;
	}
	public void setPersonalNameEn(String personalNameEn){
		this.personalNameEn = personalNameEn;
	}
	public String getEmailPrimary(){
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary){
		this.emailPrimary = emailPrimary;
	}
	public String getReferenceNo(){
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo){
		this.referenceNo = referenceNo;
	}
	public String getFinalAppDecision(){
		return finalAppDecision;
	}
	public void setFinalAppDecision(String finalAppDecision){
		this.finalAppDecision = finalAppDecision;
	}
	public String getFinalDecisionDate(){
		return finalDecisionDate;
	}
	public void setFinalDecisionDate(String finalDecisionDate){
		this.finalDecisionDate = finalDecisionDate;
	}
	public String getPolicyProgramId(){
		return policyProgramId;
	}
	public void setPolicyProgramId(String policyProgramId){
		this.policyProgramId = policyProgramId;
	}
	public String getBusinessClassId(){
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId){
		this.businessClassId = businessClassId;
	}
	public String getPersonalType(){
		return personalType;
	}
	public void setPersonalType(String personalType){
		this.personalType = personalType;
	}
	public String getNationality(){
		return nationality;
	}
	public void setNationality(String nationality){
		this.nationality = nationality;
	}
	public String getPersonalId(){
		return personalId;
	}
	public void setPersonalId(String perrsonalId){
		this.personalId = perrsonalId;
	}
	public String getIdNo(){
		return idNo;
	}
	public void setIdNo(String idNo){
		this.idNo = idNo;
	}
	public String getCardType(){
		return cardType;
	}
	public void setCardType(String cardType){
		this.cardType = cardType;
	}
	public String getCardLevel(){
		return cardLevel;
	}
	public void setCardLevel(String cardLevel){
		this.cardLevel = cardLevel;
	}
	public String getApplicationTemplate(){
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate){
		this.applicationTemplate = applicationTemplate;
	}
}
