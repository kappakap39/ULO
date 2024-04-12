package com.eaf.service.module.model;

import java.io.Serializable;

public class CardlinkCustDataM implements Serializable, Cloneable{
	private String cardlinkCustNo;
	private String orgId;
	
	public String getCardlinkCustNo() {
		return cardlinkCustNo;
	}
	public void setCardlinkCustNo(String cardlinkCustNo) {
		this.cardlinkCustNo = cardlinkCustNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
