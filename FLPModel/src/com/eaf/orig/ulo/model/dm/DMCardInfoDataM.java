package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;

public class DMCardInfoDataM implements Serializable,Cloneable{
	public DMCardInfoDataM(){
		super();
	}
	private String cardType;
	private String personalType;
	private String thFullName;
	private String idNo;
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getThFullName() {
		return thFullName;
	}
	public void setThFullName(String thFullName) {
		this.thFullName = thFullName;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}
