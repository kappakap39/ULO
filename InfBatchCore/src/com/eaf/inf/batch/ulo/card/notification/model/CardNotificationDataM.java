package com.eaf.inf.batch.ulo.card.notification.model;

import java.io.Serializable;

public class CardNotificationDataM implements Serializable, Cloneable{
	private String cardParamId;
	private String valueFrom;
	private String valueTo;
	private String value1;
	private String result;
	private String cardTypeDesc;
	private String cardLevelDesc;
	private String valueAlert;
	private String paramCode;
	
	public String getCardParamId() {
		return cardParamId;
	}
	public void setCardParamId(String cardParamId) {
		this.cardParamId = cardParamId;
	}
	public String getValueFrom() {
		return valueFrom;
	}
	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}
	public String getValueTo() {
		return valueTo;
	}
	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getValueAlert() {
		return valueAlert;
	}
	public void setValueAlert(String valueAlert) {
		this.valueAlert = valueAlert;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	
	
	
}
