package com.eaf.inf.batch.ulo.cardlink.result.model;

import java.io.Serializable;

public class CardLinkResultConditionDataM  implements Serializable, Cloneable {
 private String applicationGroupId;
 private String applicationRecordId;
 private String cardLinkCustId;

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
	public String getCardLinkCustId() {
		return cardLinkCustId;
	}
	public void setCardLinkCustId(String cardLinkCustId) {
		this.cardLinkCustId = cardLinkCustId;
	}
}
