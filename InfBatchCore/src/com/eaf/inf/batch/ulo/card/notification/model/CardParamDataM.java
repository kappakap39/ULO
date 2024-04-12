package com.eaf.inf.batch.ulo.card.notification.model;

import java.io.Serializable;

public class CardParamDataM implements Serializable, Cloneable{
	private String cardParamId;
	private String cardParamDesc;
	private String cardTypeId;
	private String cardTypeDesc;
	private String paramCode;
	private String value1;
	private String value2;
	private String cardGroupId;
	private String cardLevelId;
	private String cardLevelDesc;
	
	public String getCardParamId() {
		return cardParamId;
	}
	public void setCardParamId(String cardParamId) {
		this.cardParamId = cardParamId;
	}
	public String getCardParamDesc() {
		return cardParamDesc;
	}
	public void setCardParamDesc(String cardParamDesc) {
		this.cardParamDesc = cardParamDesc;
	}
	public String getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(String cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getCardGroupId() {
		return cardGroupId;
	}
	public void setCardGroupId(String cardGroupId) {
		this.cardGroupId = cardGroupId;
	}
	public String getCardLevelId() {
		return cardLevelId;
	}
	public void setCardLevelId(String cardLevelId) {
		this.cardLevelId = cardLevelId;
	}
	public String getCardTypeDesc() {
		return cardTypeDesc;
	}
	public void setCardTypeDesc(String cardTypeDesc) {
		this.cardTypeDesc = cardTypeDesc;
	}
	public String getCardLevelDesc() {
		return cardLevelDesc;
	}
	public void setCardLevelDesc(String cardLevelDesc) {
		this.cardLevelDesc = cardLevelDesc;
	}
	
	
	
	
}
