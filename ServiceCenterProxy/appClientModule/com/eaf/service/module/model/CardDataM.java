package com.eaf.service.module.model;

import java.io.Serializable;

public class CardDataM implements Serializable, Cloneable{
	private String cardCode;
	private String cardLevel;
	private String plasticType;
	private String docNo;
	private String cardApplyType;
	private String cardNo;
	private String cardNoEncrypted;
	private String priorityPassNo;
	private String cardType;
	private String cardlinkCustNo;
	private String cardlinkOrgNo;
	private String mainCardNo;
	private String maincardNoEncrypted;
	
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getPlasticType() {
		return plasticType;
	}
	public void setPlasticType(String plasticType) {
		this.plasticType = plasticType;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getCardApplyType() {
		return cardApplyType;
	}
	public void setCardApplyType(String cardApplyType) {
		this.cardApplyType = cardApplyType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardNoEncrypted() {
		return cardNoEncrypted;
	}
	public void setCardNoEncrypted(String cardNoEncrypted) {
		this.cardNoEncrypted = cardNoEncrypted;
	}
	public String getPriorityPassNo() {
		return priorityPassNo;
	}
	public void setPriorityPassNo(String priorityPassNo) {
		this.priorityPassNo = priorityPassNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardlinkCustNo() {
		return cardlinkCustNo;
	}
	public void setCardlinkCustNo(String cardlinkCustNo) {
		this.cardlinkCustNo = cardlinkCustNo;
	}
	public String getCardlinkOrgNo() {
		return cardlinkOrgNo;
	}
	public void setCardlinkOrgNo(String cardlinkOrgNo) {
		this.cardlinkOrgNo = cardlinkOrgNo;
	}
	public String getMainCardNo() {
		return mainCardNo;
	}
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}
	public String getMaincardNoEncrypted() {
		return maincardNoEncrypted;
	}
	public void setMaincardNoEncrypted(String maincardNoEncrypted) {
		this.maincardNoEncrypted = maincardNoEncrypted;
	}
}
