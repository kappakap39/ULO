package com.eaf.orig.ulo.model.dih;

import java.io.Serializable;
import java.sql.Timestamp;

public class CardLinkDataM implements Serializable,Cloneable{
	public CardLinkDataM(){
		super();
	}
	private String cisNo; 
	private String cardNo;
	private String cardNumber;
	private String cardNoMark;
	private String cardNoHash;
	private String phoneNo;
	private String email;
	private String mainCardHolderName;
	private String applicationType;
	private String applicationNo;
	private String cardType;
	private String cardTypeDesc;
	private String cardOrgNo;
	private String mainCis;
	private String mainCardNo;
	private String cardLinkCustNo;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String mainZipCode;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getMainZipCode() {
		return mainZipCode;
	}
	public void setMainZipCode(String mainZipCode) {
		this.mainZipCode = mainZipCode;
	}

	public String getCardLinkCustNo() {
		return cardLinkCustNo;
	}
	public void setCardLinkCustNo(String cardLinkCustNo) {
		this.cardLinkCustNo = cardLinkCustNo;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardNoMark() {
		return cardNoMark;
	}
	public void setCardNoMark(String cardNoMark) {
		this.cardNoMark = cardNoMark;
	}
	public String getCardNoHash() {
		return cardNoHash;
	}
	public void setCardNoHash(String cardNoHash) {
		this.cardNoHash = cardNoHash;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMainCardHolderName() {
		return mainCardHolderName;
	}
	public void setMainCardHolderName(String mainCardHolderName) {
		this.mainCardHolderName = mainCardHolderName;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardTypeDesc() {
		return cardTypeDesc;
	}
	public void setCardTypeDesc(String cardTypeDesc) {
		this.cardTypeDesc = cardTypeDesc;
	}
	public String getCardOrgNo() {
		return cardOrgNo;
	}
	public void setCardOrgNo(String cardOrgNo) {
		this.cardOrgNo = cardOrgNo;
	}
	public String getMainCis() {
		return mainCis;
	}
	public void setMainCis(String mainCis) {
		this.mainCis = mainCis;
	}
	public String getMainCardNo() {
		return mainCardNo;
	}
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}	
}
