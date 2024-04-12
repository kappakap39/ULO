package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class RejectLetterApplicationDataM implements Serializable,Cloneable {
	private String businessClassId;
	private String applicationRecordId;
	private String cardCode;
	public RejectLetterApplicationDataM() {
		super();
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getProduct(){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
}
